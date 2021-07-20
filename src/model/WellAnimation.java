package model;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

public class WellAnimation extends Transition {
    private Well well;

    public WellAnimation(Well well) {
        this.well = well;
        setCycleDuration(Duration.millis(well.getFillTime() * 1000));
        setCycleCount(1);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        well.update(v);
    }
}
