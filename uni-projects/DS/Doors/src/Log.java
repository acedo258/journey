import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log extends CodeHandler {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log(CodeHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleCode(String code, Door door) {
        // log with timestamp, door ID, code, and locked state
        System.out.println("[LOG] " + LocalDateTime.now().format(formatter) +
                " | Door: " + door.getId() +
                " | Code: " + code +
                " | Locked: " + door.isLocked());

        super.handleCode(code, door);
    }
}