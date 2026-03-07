package independent_clocks;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClockTimer clockTimer = new ClockTimer(9);
        List<Widget> clocks = new ArrayList<>(List.of(
                new AnalogClock(0, "Cerdanyola, Catalonia", clockTimer),
                new DigitalClock(-9, "Anchorage, Alaska", clockTimer),
                new AnalogClock(3, "Moscow, Russia", clockTimer),
                new DigitalClock(-7, "Sonora, Mexico", clockTimer),
                new AnalogClock(-1, "Berlin, Germany", clockTimer),
                new DigitalClock(-4, "Yerevan, Armenia", clockTimer),
                new Stopwatch(clockTimer),
                new CountdownTimer(clockTimer)
        ));

        for (Widget c : clocks) {
            c.show();
        }
    }
}