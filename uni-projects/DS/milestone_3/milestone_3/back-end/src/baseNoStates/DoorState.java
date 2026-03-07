package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Abstract base class for door states implementing the State design pattern.
  This pattern allows a door to change its behavior based on its current state
  (locked, unlocked, unlocked_shortly, propped) without using complex conditionals.
  Each concrete state class handles actions differently according to its semantics.
 */

public abstract class DoorState {
  private static final Logger logger = LoggerFactory.getLogger(DoorState.class);

  protected Door door;

  public DoorState(final Door door) {
      super();
      this.door = door;
  }

  public abstract void open();
  public abstract void close();
  public abstract void lock();
  public abstract void unlock();
  public abstract void unlockShortly();
}