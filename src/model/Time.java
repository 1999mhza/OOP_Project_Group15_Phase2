package model;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Time extends Transition {
    private double time;
    private double startTime;
    private double lastTime;
    private Label show;

    public Time(Label show) {
        startTime = (double) System.currentTimeMillis();
        lastTime = (double) System.currentTimeMillis();
        time = 0;
        this.show = show;
        show.setText(String.format("%02d:%02d", 0, 0));

        setCycleCount(-1);
        setCycleDuration(Duration.ONE);
    }

    public double getTime() {
        return time / 1000;
    }

    public void pause() {
        lastTime = (double) System.currentTimeMillis();
        super.pause();
    }

    public void play() {
        startTime += (double) System.currentTimeMillis() - lastTime;
        super.play();
    }

    @Override
    protected void interpolate(double v) {
        time = (double) System.currentTimeMillis() - startTime;
        int second = (int) (time / 1000);
        show.setText(String.format("%02d:%02d", second / 60, second % 60));
    }
}
