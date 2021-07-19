package model.animal.domestic;

import javafx.animation.Transition;
import javafx.util.Duration;

public class EatAnimation extends Transition {
    private Domestic domestic;

    public EatAnimation(Domestic domestic) {
        this.domestic = domestic;
        setCycleDuration(Duration.millis(500));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        domestic.updateEat(v);
    }
}
