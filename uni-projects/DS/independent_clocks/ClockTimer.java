package independent_clocks;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;

public class ClockTimer extends Observable {
    private Timer timer;
    private int period; // milliseconds

    public ClockTimer(int period) {
        this.period = period;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(LocalDateTime.now());
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, period, period);

    }
    public int getPeriod() {
        return period;
    }
}