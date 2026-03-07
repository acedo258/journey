package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;

public class CountdownTimer extends Widget {
    private JLabel timeLabel;
    private JButton startResetButton;
    private ClockTimer clockTimer;
    private boolean isRunning = false;
    private Duration remainingTime = Duration.ofMinutes(1);
    private LocalDateTime lastUpdateTime;

    public CountdownTimer(ClockTimer clockTimer) {
        super(0);
        this.clockTimer = clockTimer;

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        timeLabel = new JLabel("01:00");
        timeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 48));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(timeLabel);

        startResetButton = new JButton("Start");
        startResetButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
        startResetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startResetButton.addActionListener(e -> toggleCountdown());
        panel.add(startResetButton);

        JLabel titleLabel = new JLabel("Countdown Timer");
        titleLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        clockTimer.addObserver(this);
    }

    public void show() {
        JFrame frame = new JFrame("Countdown Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocalDateTime && isRunning) {
            LocalDateTime now = (LocalDateTime) arg;
            if (lastUpdateTime != null) {
                Duration elapsed = Duration.between(lastUpdateTime, now);
                remainingTime = remainingTime.minus(elapsed);
                if (remainingTime.isNegative()) {
                    remainingTime = Duration.ZERO;
                    isRunning = false;
                    startResetButton.setText("Start");
                }
            }
            lastUpdateTime = now;
            updateDisplay();
        }
    }

    private void toggleCountdown() {
        if (!isRunning) {
            lastUpdateTime = LocalDateTime.now();
            isRunning = true;
            startResetButton.setText("Reset");
        } else {
            isRunning = false;
            remainingTime = Duration.ofMinutes(1);
            startResetButton.setText("Start");
            updateDisplay();
        }
    }

    private void updateDisplay() {
        long minutes = remainingTime.toMinutesPart();
        long seconds = remainingTime.toSecondsPart();
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}