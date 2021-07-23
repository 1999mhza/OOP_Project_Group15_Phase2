package model;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

public class GrassAnimation extends Transition {
    private Grass grass;

    public GrassAnimation(Grass grass) {
        this.grass = grass;
        setCycleDuration(Duration.millis(500));
        setCycleCount(1);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        grass.update(v);
    }
}
