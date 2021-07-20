package model.animal.wild;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

public class CageAnimation extends Transition {
    private Wild wild;

    public CageAnimation(Wild wild) {
        this.wild = wild;
        setCycleDuration(Duration.millis(1000.0));
        setCycleCount(-1);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        wild.updateCage(v);
    }
}
