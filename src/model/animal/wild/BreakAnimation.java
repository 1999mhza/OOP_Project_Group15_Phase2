package model.animal.wild;

import javafx.animation.Transition;
import javafx.util.Duration;

public class BreakAnimation extends Transition {
    private Wild wild;

    public BreakAnimation(Wild wild) {
        this.wild = wild;
        setCycleDuration(Duration.millis(1000 * wild.getFullLifetime()));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        wild.updateBreak(v);
    }
}
