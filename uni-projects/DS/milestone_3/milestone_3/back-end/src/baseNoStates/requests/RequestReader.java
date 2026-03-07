package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.Door;
import baseNoStates.Space;
import baseNoStates.User;
import baseNoStates.DirectoryUserGroups;
import java.time.LocalDateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Request to perform an action on a single door.
  Checks user authorization based on schedule, action and space permissions.
 */
public class RequestReader implements Request {
  private static final Logger logger = LoggerFactory.getLogger(RequestReader.class); // Logger añadido

  private final String credential;
  private final String action;
  private final LocalDateTime dateTime;
  private final String doorId;
  private String doorStateName;

  public RequestReader(final String credential, final String action, final LocalDateTime dateTime, final String doorId) {
      this.credential = credential;
      this.action = action;
      this.dateTime = dateTime;
      this.doorId = doorId;
  }

  public String getAction() {
    return this.action;
  }

  public boolean isAuthorized() {
    final User user = DirectoryUserGroups.findUserByCredential(this.credential);
    if (null == user) {
        logger.warn("Authorization failed: User with credential {} not found", this.credential);
      return false;
    }

    final Door door = DirectoryDoors.findDoorById(this.doorId);
    if (null == door) {
        logger.warn("Authorization failed: Door with ID {} not found", this.doorId);
      return false;
    }

    final Space targetSpace = door.getToSpace();
    if (null == targetSpace) {
        logger.warn("Authorization failed: Door {} has no target space", this.doorId);
      return false;
    }

    final boolean canSend = user.canSendRequests(this.dateTime);
    final boolean canDo = user.canDoAction(this.action);
    final boolean canAccess = user.canBeInSpace(targetSpace);

    if (!canSend) logger.info("Failed: Outside schedule at {}", this.dateTime);
    if (!canDo) logger.info("Failed: Action {} not allowed", this.action);
    if (!canAccess) {
      final String id = targetSpace.getId();
        logger.info("Failed: No access to space {}", id);
    }

    return canSend && canDo && canAccess;
  }

  public String getDoorStateName() {
    return this.doorStateName;
  }

  public void setDoorStateName(final String doorStateName) {
    this.doorStateName = doorStateName;
  }

  @Override
  public JSONObject answerToJson() {
    final JSONObject json = new JSONObject();
    final JSONObject reasons = new JSONObject();
    final boolean authorized = this.isAuthorized();
    if (!authorized) {
      final User user = DirectoryUserGroups.findUserByCredential(this.credential);
      final Door door = DirectoryDoors.findDoorById(this.doorId);
      if (null == user) reasons.put("error", "User not found");
      else if (null == door) reasons.put("error", "Door not found");
      else {
        if (!user.canSendRequests(this.dateTime)) reasons.put("reason", "Outside schedule");
        if (!user.canDoAction(this.action)) reasons.put("reason", "Action not allowed");
        final Space toSpace = door.getToSpace();
        if (!user.canBeInSpace(toSpace)) reasons.put("reason", "No access to space");
      }
    }
    json.put("reasons", reasons);
    json.put("authorized", authorized);
    final Door door = DirectoryDoors.findDoorById(this.doorId);
    if (null != door) {
      final boolean closed = door.isClosed();
      json.put("closed", closed);
      final String stateName = door.getStateName();
      json.put("state", null != this.doorStateName ? this.doorStateName : stateName);
    } else {
      json.put("closed", false);
      json.put("state", "unknown");
    }
    json.put("doorId", this.doorId);
    return json;
  }

  @Override
  public void process() {
    final Door door = DirectoryDoors.findDoorById(this.doorId);
    if (null != door) {
      door.processRequest(this);
    } else {
        logger.warn("Cannot process request: Door {} not found", this.doorId);
    }
  }

  @Override
  public String toString() {
    return "RequestReader{credential='" + this.credential + "', action='" + this.action + "', dateTime=" + this.dateTime +
        ", doorId='" + this.doorId + "', doorStateName='" + this.doorStateName + "'}";
  }
}