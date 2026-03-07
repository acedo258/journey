package baseNoStates.requests;

import org.json.JSONObject;

/*
  Interface for all request types in the access control system.
  Implementations: RequestReader (single door), RequestArea (multiple doors),
  RequestRefresh (get all door states).
 */

public interface Request {
    JSONObject answerToJson();

    String toString();

    void process();
}
