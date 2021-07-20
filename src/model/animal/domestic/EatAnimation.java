package model.animal.domestic;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.Grass;

public class EatAnimation extends Transition {
    private Domestic domestic;

    public EatAnimation(Domestic domestic) {
        this.domestic = domestic;
        setCycleDuration(Duration.millis(1000));
        setCycleCount(-1);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        domestic.updateEat(v);
    }
}
