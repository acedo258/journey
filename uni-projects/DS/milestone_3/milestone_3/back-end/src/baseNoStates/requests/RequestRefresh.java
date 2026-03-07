package baseNoStates.requests;

import baseNoStates.DirectoryAreas;
import baseNoStates.DirectoryDoors;
import baseNoStates.Door;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;


/*
  Request to get the current state of all doors.
  Used by the simulator to refresh the display on page load.
 */

public class RequestRefresh implements Request {
  private final ArrayList<JSONObject> jsonsDoors = new ArrayList<>();

  @Override
  public JSONObject answerToJson() {
    final JSONObject json = new JSONObject();
    json.put("doors", new JSONArray(this.jsonsDoors));
    // jsonDoors has been set previously by process()
    return json;
  }

  @Override
  public String toString() {
    return "RequestRefresh{"
        + this.jsonsDoors
        + "}";
  }

  // Also this is used to paint the simulator when the page is loaded, and to display
  // doors and readers after passing from locked to propped or propped to locked,
  // pressing the Refresh Request button of the simulator.
  // Also, to quickly test if the partition requests sent by the client app in Flutter
  // works or not, retrieves the state of all the doors so that the simulator can
  // repaint the readers
  public void process() {
    for (final Door door : DirectoryDoors.getAllDoors()) {  // aqui hemos cambiado que se recorran desde DirecotryAreas
      final JSONObject json = door.toJson();
        this.jsonsDoors.add(json);
    }
  }
}
