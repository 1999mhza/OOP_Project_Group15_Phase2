package model.good;

import javafx.animation.Transition;
import javafx.util.Duration;

public class GoodAnimation extends Transition {
    private Good good;

    public GoodAnimation(Good good) {
        this.good = good;
        setCycleDuration(Duration.millis(1000 * good.getFullLifeTime()));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        good.update(v);
    }
}
