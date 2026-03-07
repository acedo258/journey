package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
  Singleton clock that notifies observers every second.
  Used by UnlockShortlyState to track time and auto-transition after 10 seconds.
  Implements Observable to notify door states about time changes.
 */
public class Clock extends Observable {
  private static final Logger logger = LoggerFactory.getLogger(Clock.class);
  private static Clock instance;

  private Timer timer;
  private LocalDateTime currentTime;

  private Clock() {
      this.timer = new Timer();
      this.currentTime = LocalDateTime.now();
      logger.debug("Clock singleton created");
  }

  public static Clock getInstance() {
    if (null == instance) {
        instance = new Clock();
    }
    return instance;
  }

  /*
   Starts the clock timer, notifying observers every second.
   */
  public void start() {
    if (null != this.timer) {
        this.timer.cancel();
    }
      this.timer = new Timer();

      this.timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
          Clock.this.currentTime = Clock.this.currentTime.plusSeconds(1);
          Clock.this.setChanged();
          notifyObservers(currentTime);
      }
    }, 0, 1000);
      logger.info("Clock started");
  }

  /*
    Stops the clock timer.
   */
  public void stop() {
    if (null != this.timer) {
        this.timer.cancel();
        this.timer = new Timer();
    }
      logger.info("Clock stopped");
  }

  public LocalDateTime getCurrentTime() {
    return this.currentTime;
  }
}
