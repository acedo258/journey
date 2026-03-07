package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Represents the propped state of a door in the State pattern.
  A door enters this state when it was left open after unlock_shortly expired.
  This is an alarm state indicating a security issue.
  Valid transitions: close -> LockedState (door automatically locks when closed).
 */
public class ProppedState extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(ProppedState.class);

  public ProppedState(final Door door) {
    super(door);
    final String id = this.door.getId();

      ProppedState.logger.warn("Door {} entered PROPPED state - security alert!",id);
  }

  @Override
  public void open() {
    if (this.door.isClosed()) {
        this.door.setClosed(false);
      final String id = this.door.getId();

        ProppedState.logger.debug("Door {} opened while propped",id);
    }
  }

  @Override
  public void close() {
    if (!this.door.isClosed()) {
      final String id = this.door.getId();
        this.door.setClosed(true);
        this.door.setState(new LockedState(this.door));
        ProppedState.logger.info("Door {} closed and locked from propped state", id);
    }
  }

  @Override
  public void lock() {
    final String id = this.door.getId();
    if (this.door.isClosed()) {
        this.door.setState(new LockedState(this.door));
        ProppedState.logger.info("Door {} locked from propped state", id);
    } else {
        ProppedState.logger.warn("Cannot lock door {} - it's open", id);
    }
  }

  @Override
  public void unlock() {
    final String id = this.door.getId();
      ProppedState.logger.warn("Door {} is propped, unlock has no effect", id);
  }

  @Override
  public void unlockShortly() {
    final String id = this.door.getId();
      ProppedState.logger.warn("Cannot unlock_shortly door {} while propped", id);
  }
}
