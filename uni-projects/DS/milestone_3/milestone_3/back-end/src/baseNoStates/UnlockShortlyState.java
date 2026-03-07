package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/*
  Represents the unlock_shortly state of a door in the State pattern.
  This state allows temporary access: the door unlocks for 10 seconds,
  then automatically returns to locked if closed, or propped if still open.
  Implements Observer to receive time updates from the Clock singleton.
 */
public class UnlockShortlyState extends DoorState implements Observer {
  private static final Logger logger = LoggerFactory.getLogger(UnlockShortlyState.class);
  private static final int UNLOCK_DURATION_SECONDS = 10;

  private final LocalDateTime startTime;
  private final Clock clock;

  public UnlockShortlyState(final Door door) {
    super(door);
      this.startTime = LocalDateTime.now();
      this.clock = Clock.getInstance();
      this.clock.addObserver(this);
    final String id = door.getId();
      UnlockShortlyState.logger.info("Door {} unlocked shortly, will auto-lock in {} seconds",
        id, UnlockShortlyState.UNLOCK_DURATION_SECONDS);
  }

  @Override
  public void open() {
    if (this.door.isClosed()) {
        this.door.setClosed(false);
      final String id = this.door.getId();
        UnlockShortlyState.logger.debug("Door {} opened in unlocked_shortly state", id);
    }
  }

  @Override
  public void close() {
    if (!this.door.isClosed()) {
        this.door.setClosed(true);
      final String id = this.door.getId();
        UnlockShortlyState.logger.debug("Door {} closed in unlocked_shortly state", id);
    }
  }

  @Override
  public void unlock() {
    final String id = this.door.getId();
      UnlockShortlyState.logger.warn("Door {} already unlocked shortly", id);
  }

  @Override
  public void lock() {
    final String id = this.door.getId();
    if (this.door.isClosed()) {
        this.clock.deleteObserver(this);
        this.door.setState(new LockedState(this.door));
        UnlockShortlyState.logger.info("Door {} manually locked from unlocked_shortly", id);
    } else {
        UnlockShortlyState.logger.warn("Cannot lock door {} - it's open", id);
    }
  }

  @Override
  public void unlockShortly() {
    final String id = this.door.getId();
      UnlockShortlyState.logger.warn("Door {} already in unlocked_shortly state", id);
  }


   //Called by the Clock observable every second.
   // After 10 seconds, transitions to locked (if closed) or propped (if open).

  @Override
  public void update(final Observable o, final Object arg) {
    if (arg instanceof LocalDateTime) {
      final LocalDateTime currentTime = (LocalDateTime) arg;
      final Duration duration = Duration.between(this.startTime, currentTime);

      if (10 <= duration.getSeconds()) {
          this.clock.deleteObserver(this);
        final String id = this.door.getId();
        if (this.door.isClosed()) {
            this.door.setState(new LockedState(this.door));
            UnlockShortlyState.logger.info("Door {} auto-locked after 10 seconds",
              id);
        } else {
            this.door.setState(new ProppedState(this.door));
            UnlockShortlyState.logger.warn("Door {} propped - was left open after 10 seconds",
              id);
        }
      }
    }
  }
}
