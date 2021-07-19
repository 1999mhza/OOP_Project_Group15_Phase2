package model.animal.wild;

import javafx.animation.Transition;
import javafx.util.Duration;

public class BreakAnimation extends Transition {
    private Wild wild;

    public BreakAnimation(Wild wild) {
        this.wild = wild;
        setCycleCount(1);
    }

    public void play(double time) {
        setCycleDuration(Duration.millis(1000 * time));
        super.play();
    }

    @Override
    protected void interpolate(double v) {
        wild.updateBreak(v);
    }
}
