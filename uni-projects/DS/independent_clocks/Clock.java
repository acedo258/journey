package independent_clocks;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Clock extends Widget {
    protected String worldPlace;

    public Clock(int hoursOffsetTimeZone, String worldPlace, ClockTimer clockTimer) {
        super(hoursOffsetTimeZone);
        this.worldPlace = worldPlace;
        clockTimer.addObserver(this);
    }

    protected LocalDateTime adjustTimeZone(LocalDateTime time) {
        return time.plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
    }
}