package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;

public class Stopwatch extends Widget {
    private JLabel timeLabel;
    private JButton startStopButton;
    private ClockTimer clockTimer;
    private boolean isRunning = false;
    private Duration elapsedTime = Duration.ZERO;
    private LocalDateTime startTime;

    public Stopwatch(ClockTimer clockTimer) {
        super(0);
        this.clockTimer = clockTimer;

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        timeLabel = new JLabel("00:00:00:000");
        timeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 48));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(timeLabel);

        startStopButton = new JButton("Start");
        startStopButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
        startStopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startStopButton.addActionListener(e -> toggleStopwatch());
        panel.add(startStopButton);

        JLabel titleLabel = new JLabel("Stopwatch");
        titleLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        clockTimer.addObserver(this);
    }

    public void show() {
        JFrame frame = new JFrame("Stopwatch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocalDateTime && isRunning) {
            LocalDateTime now = (LocalDateTime) arg;
            if (startTime != null) {
                elapsedTime = elapsedTime.plus(Duration.between(startTime, now));
                startTime = now;
            }
            updateDisplay();
        }
    }

    private void toggleStopwatch() {
        if (!isRunning) {
            startTime = LocalDateTime.now();
            isRunning = true;
            startStopButton.setText("Stop");
        } else {
            isRunning = false;
            startStopButton.setText("Start");
            updateDisplay();
        }
    }

    private void updateDisplay() {
        long totalMillis = elapsedTime.toMillis();
        long hours = totalMillis / (1000 * 60 * 60);
        long minutes = (totalMillis / (1000 * 60)) % 60;
        long seconds = (totalMillis / 1000) % 60;
        long millis = totalMillis % 1000;
        String formattedTime = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
        timeLabel.setText(formattedTime);
    }
}