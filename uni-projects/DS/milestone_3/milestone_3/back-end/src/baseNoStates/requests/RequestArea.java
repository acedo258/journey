package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.Door;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
  Request to perform an action on all doors in an area.
  Only LOCK and UNLOCK actions are valid for area requests.
  Creates individual RequestReader for each door in the area.
 */

public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();


  public RequestArea(final String credential, final String action, final LocalDateTime now, final String areaId) {
      this.credential = credential;
      this.areaId = areaId;
      assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
              : "invalid action " + action + " for an area request";
      this.action = action;
      this.now = now;
  }

  public String getAction() {
    return this.action;
  }

  @Override
  public JSONObject answerToJson() {
    final JSONObject json = new JSONObject();
    json.put("action", this.action);
    json.put("areaId", this.areaId);
    final JSONArray jsonRequests = new JSONArray();
    for (final RequestReader rd : this.requests) {
      JSONObject value = rd.answerToJson();
      jsonRequests.put(value);
    }
    json.put("requestsDoors", jsonRequests);
    return json;
  }

  @Override
  public String toString() {
    final String requestsDoorsStr;
    if (this.requests.isEmpty()) {
      requestsDoorsStr = "";
    } else {
      requestsDoorsStr = this.requests.toString();
    }
    return "Request{"
            + "credential=" + this.credential
            + ", action=" + this.action
            + ", now=" + this.now
            + ", areaId=" + this.areaId
            + ", requestsDoors=" + requestsDoorsStr
            + "}";
  }

  // processing the request of an area is creating the corresponding door requests and forwarding
  // them to all of its doors. For some it may be authorized and action will be done, for others
  // it won't be authorized and nothing will happen to them.
  public void process() {
    // commented out until Area, Space and Partition are implemented


    // make the door requests and put them into the area request to be authorized later and
    // processed later
    final Area area = DirectoryAreas.findAreaById(this.areaId);
    // an Area is a Space or a Partition
    if (null != area) {
      // is null when from the app we click on an action but no place is selected because
      // there (flutter) I don't control like I do in javascript that all the parameters are provided

      // Make all the door requests, one for each door in the area, and process them.
      // Look for the doors in the spaces of this area that give access to them.
      for (final Door door : area.getDoorsGivingAccess()) {
        final String id = door.getId();
        final RequestReader requestReader = new RequestReader(this.credential, this.action, this.now, id);
        requestReader.process();
        // after process() the area request contains the answer as the answer
        // to each individual door request, that is read by the simulator/Flutter app
          this.requests.add(requestReader);
      }
    }

  }
}
