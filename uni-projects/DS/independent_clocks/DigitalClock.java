package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

public class DigitalClock extends Clock {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
    private JLabel clockLabel;
    private ClockTimer clockTimer;

    public DigitalClock(int hoursOffsetTimeZoneOffset, String worldPlace, ClockTimer clockTimer) {
        super(hoursOffsetTimeZoneOffset, worldPlace, clockTimer);
        this.clockTimer = clockTimer;

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 45, 5, 45));
        panel.setPreferredSize(new Dimension(500, 120));
        clockLabel = new JLabel();
        clockLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 72));
        clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(clockLabel);
        JLabel placeLabel = new JLabel(worldPlace);
        placeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
        placeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(placeLabel);
    }

    public void show() {
        JFrame frame = new JFrame(worldPlace);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocalDateTime) {
            LocalDateTime now = (LocalDateTime) arg;
            now = adjustTimeZone(now); // Ajusta la zona horaria
            String timeDisplay = now.format(formatter);
            SwingUtilities.invokeLater(() -> clockLabel.setText(timeDisplay));
        }
    }
}