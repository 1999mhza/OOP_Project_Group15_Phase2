package model.animal.domestic;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.animal.Animal;

public class DeathAnimation extends Transition {
    private final Domestic domestic;

    public DeathAnimation(Domestic domestic) {
        this.domestic = domestic;
        setCycleDuration(Duration.millis(1000.0));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        domestic.updateDeath(v);
    }
}
