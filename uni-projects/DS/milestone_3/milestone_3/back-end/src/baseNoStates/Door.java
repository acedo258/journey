package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Represents a door in the access control system.
  A door connects two spaces (from and to) and has a state (State pattern).
  Processes reader requests by delegating actions to its current state.
 */
public class Door {
  private static final Logger logger = LoggerFactory.getLogger(Door.class);

  private final String id;
  private boolean closed;
  private DoorState state;
  private final Space fromSpace;
  private final Space toSpace;

  public Door(final String id, final Space from, final Space to) {
      super();
      this.id = id;
      this.closed = true;
      this.state = new UnlockedState(this);
      this.fromSpace = from;
      this.toSpace = to;
      // Register this door with its destination space
      if (null != to) {
          to.addDoor(this);
      }
      final String id1 = from.getId();
      final String id2 = to.getId();
      Door.logger.debug("Created door {} from {} to {}", id,
              null != from ? id1 : "null",
              null != to ? id2 : "null");
  }

  /*
    Processes a reader request by authorizing and executing the action.
   */
  public void processRequest(final RequestReader request) {
    if (request.isAuthorized()) {
      final String action = request.getAction();
        this.doAction(action);
        Door.logger.info("Door {} processed action: {}", this.id, action);
    } else {
        Door.logger.warn("Request not authorized for door {}", this.id);
    }
    final String stateName = this.getStateName();
    request.setDoorStateName(stateName);
  }

  private void doAction(final String action) {
    switch (action) {
      case Actions.OPEN:
          this.state.open();
        break;
      case Actions.CLOSE:
          this.state.close();
        break;
      case Actions.LOCK:
          this.state.lock();
        break;
      case Actions.UNLOCK:
          this.state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
          this.state.unlockShortly();
        break;
      default:
          Door.logger.error("Unknown action: {}", action);
    }
  }

  public boolean isClosed() {
    return this.closed;
  }

  public void setClosed(final boolean closed) {
    this.closed = closed;
  }

  public void setState(final DoorState state) {
    this.state = state;
  }

  public String getId() {
    return this.id;
  }

  public String getStateName() {
    if (this.state instanceof UnlockedState) {
      return "unlocked";
    } else if (this.state instanceof LockedState) {
      return "locked";
    } else if (this.state instanceof UnlockShortlyState) {
      return "unlocked_shortly";
    } else if (this.state instanceof ProppedState) {
      return "propped";
    }
    return "unknown";
  }

  public Space getFromSpace() {
    return this.fromSpace;
  }

  public Space getToSpace() {
    return this.toSpace;
  }

  @Override
  public String toString() {
    return "Door{id='" + this.id + "', closed=" + this.closed + ", state=" + this.getStateName() + "}";
  }

  public JSONObject toJson() {
    final JSONObject json = new JSONObject();
    json.put("id", this.id);
    final String stateName = this.getStateName();
    json.put("state", stateName);
    json.put("closed", this.closed);
    if (null != this.fromSpace) {
      final String id1 = this.fromSpace.getId();
      json.put("originSpace", id1);
    }
    if (null != this.toSpace) {
      final String id1 = this.toSpace.getId();
      json.put("destinationSpace", id1);
    }
    return json;
  }
}
