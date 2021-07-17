package model;

import javafx.animation.Transition;
import javafx.util.Duration;

public class WellAnimation extends Transition {
    private Well well;

    public WellAnimation(Well well) {
        this.well = well;
        setCycleDuration(Duration.millis(well.getFillTime() * 1000));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        well.update(v);
    }
}
