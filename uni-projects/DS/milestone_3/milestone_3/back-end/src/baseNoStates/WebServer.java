package baseNoStates;

import baseNoStates.requests.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.StringTokenizer;

// IMPORTANTE: Librerías para Logging (SLF4J)
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example
public class WebServer {
  // Declaramos el LOGGER para esta clase
  private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

  private static final int PORT = 8080; // port to listen connection
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

  public WebServer() {
      super();
      try {
          final ServerSocket serverConnect = new ServerSocket(PORT);
          // System.out.println -> logger.info
          WebServer.logger.info("Server started. Listening for connections on port : {} ...", WebServer.PORT);

          // we listen until user halts server execution
          while (true) {
              // each client connection will be managed in a dedicated Thread
              new SocketThread(serverConnect.accept());
              // create dedicated thread to manage the client connection
          }
      } catch (IOException e) {
          // System.err.println -> logger.error
          WebServer.logger.error("Server Connection error : {}", e.getMessage());
      }
  }

  private class SocketThread extends Thread {
    // as an inner class, SocketThread sees WebServer attributes
    private final Socket insocked; // client connection via Socket class

    SocketThread(final Socket insocket) {
        super();
        this.insocked = insocket;
        this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      final BufferedReader in;
      final PrintWriter out;
      final String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(this.insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(this.insocked.getOutputStream());
        // get first line of the request from the client
        final String input = in.readLine();

        if (null == input) return; // Evitar errores si input es null

        // Logueamos la petición entrante como INFO
          WebServer.logger.info("socketthread : {}", input);

        StringTokenizer parse = new StringTokenizer(input);
        final String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
        if (!"GET".equals(method)) {
            WebServer.logger.warn("501 Not Implemented : {} method.", method);
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          // Logs de depuración (DEBUG)
            WebServer.logger.debug("input {}", input);
            WebServer.logger.debug("method {}", method);
            WebServer.logger.debug("resource {}", resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          final String[] tokens = new String[20]; // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            // Esto es muy detallado, lo ponemos en TRACE o DEBUG
              WebServer.logger.debug("Token {}: {}", i, tokens[i]);
            i++;
          }

          // Here is where we send the request and get the answer inside it
          final Request request = this.makeRequest(tokens);
          if (null != request) {
            final String typeRequest = tokens[0];
            // Logueamos la creación y el procesado como INFO (requerimiento del enunciado)
              WebServer.logger.info("created request {} {}", typeRequest, request);
            request.process();
              WebServer.logger.info("processed request {} {}", typeRequest, request);

            // Make the answer as a JSON string, to be sent to the Javascript client
            final String answer = this.makeJsonAnswer(request);
              WebServer.logger.debug("answer: {}", answer); // El JSON completo mejor en debug

            // Here we send the response to the client
            out.println(answer);
            out.flush(); // flush character output stream buffer
            Thread.sleep(1000);
          }
        }

        in.close();
        out.close();
          this.insocked.close(); // we close socket connection
      } catch (Exception e) {
          WebServer.logger.error("Exception in SocketThread: ", e);
      }
    }

    private Request makeRequest(final String[] tokens) {
      // Imprimir array de tokens limpio usando el logger
        WebServer.logger.debug("tokens parsing: {}", Arrays.toString(tokens));

      final Request request;
      // assertions below evaluated to false won't stop the webserver, just print an
      // assertion error, maybe because the webserver runs in a socked thread
      switch (tokens[0]) {
        case "refresh":
          request = new RequestRefresh();
          break;
        case "reader":
          request = this.makeRequestReader(tokens);
          break;
        case "area":
          request = this.makeRequestArea(tokens);
          break;
        case "get_children":
              String areaId = tokens[1];
              request = new RequestChildren(areaId);
              WebServer.logger.info("Created get_children request for area {}", areaId);
              break;
        default:
          // just in case we change the user interface or the simulator
            WebServer.logger.error("Unknown request {}", tokens[0]);
          assert false : "unknown request " + tokens[0];
          request = null;
          System.exit(-1);
      }
      return request;
    }

    private RequestReader makeRequestReader(final String[] tokens) {
      final String credential = tokens[2];
      final String action = tokens[4];
      final LocalDateTime dateTime = LocalDateTime.parse(tokens[6], WebServer.formatter);
      final String doorId = tokens[8];
      return new RequestReader(credential, action, dateTime, doorId);
    }

    private RequestArea makeRequestArea(final String[] tokens) {
      final String credential = tokens[2];
      final String action = tokens[4];
      final LocalDateTime dateTime = LocalDateTime.parse(tokens[6], WebServer.formatter);
      final String areaId = tokens[8];
      return new RequestArea(credential, action, dateTime, areaId);
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "Access-Control-Allow-Origin: *\r\n";
      // SUPERIMPORTANT to avoid the CORS problem :
      // "Cross-Origin Request Blocked: The Same Origin Policy disallows reading
      // the remote resource..."
      answer += "\r\n"; // blank line between headers and content, very important !
      return answer;
    }

    private String makeJsonAnswer(final Request request) {
      String answer = this.makeHeaderAnswer();
        JSONObject jsonObject = request.answerToJson();
        answer += jsonObject.toString();
      return answer;
    }
  }
}