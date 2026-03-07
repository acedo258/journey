package independent_clocks;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public abstract class Widget implements Observer {
    protected JPanel panel;
    protected int hoursOffsetTimeZone;

    public Widget(int hoursOffsetTimeZone) {
        this.hoursOffsetTimeZone = hoursOffsetTimeZone;
        this.panel = new JPanel();
    }

    public abstract void show();

    @Override
    public abstract void update(Observable o, Object arg);
}