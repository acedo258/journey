package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Represents the unlocked state of a door in the State pattern.
  When unlocked, the door can be freely opened and closed.
  Valid transitions: lock -> LockedState (only if door is closed).
 */

public class UnlockedState extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(UnlockedState.class);

  public UnlockedState(final Door door) {
    super(door);
    final String id = door.getId();
      UnlockedState.logger.debug("Door {} is now unlocked", id);
  }

  @Override
  public void open() {
    if (this.door.isClosed()) {
        this.door.setClosed(false);
      final String id = this.door.getId();
        UnlockedState.logger.debug("Door {} opened", id);
    }
  }

  @Override
  public void close() {
    if (!this.door.isClosed()) {
        this.door.setClosed(true);
      final String id = this.door.getId();
        UnlockedState.logger.debug("Door {} closed", id);
    }
  }

  @Override
  public void unlock() {
    final String id = this.door.getId();
      UnlockedState.logger.warn("Door {} already unlocked", id);
  }

  @Override
  public void lock() {
    final String id = this.door.getId();
    if (this.door.isClosed()) {
        this.door.setState(new LockedState(this.door));
        UnlockedState.logger.info("Door {} locked", id);
    } else {
        UnlockedState.logger.warn("Cannot lock door {} - it's open", id);
    }
  }

  @Override
  public void unlockShortly() {
    final String id = this.door.getId();
      UnlockedState.logger.debug("Door {} already unlocked, unlockShortly has no effect", id);
  }
}
