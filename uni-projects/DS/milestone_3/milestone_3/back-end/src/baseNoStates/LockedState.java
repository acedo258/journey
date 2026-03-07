package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
  Represents the locked state of a door in the State pattern.
  When locked, the door cannot be opened until it is unlocked.
  Valid transitions: unlock -> UnlockedState, unlockShortly -> UnlockShortlyState.
 */

public class LockedState extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(LockedState.class);

  public LockedState(final Door door) {
    super(door);
  }

  @Override
  public void open() {
    final String id = this.door.getId();
      LockedState.logger.warn("Cannot open door {} - it's locked", id);
  }

  @Override
  public void close() {
    if (!this.door.isClosed()) {
        this.door.setClosed(true);
    } else {
        LockedState.logger.warn("Door already closed");
    }
  }

  @Override
  public void unlock() {
    if (this.door.isClosed()) {
        this.door.setState(new UnlockedState(this.door));
    }
  }

  @Override
  public void lock() {
      LockedState.logger.warn("Door already locked!!");
  }

  @Override
  public void unlockShortly() {
      this.door.setState(new UnlockShortlyState(this.door));
  }
}