package model.animal;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;
public class AnimalAnimation extends Transition {
    private final Animal animal;

    public AnimalAnimation(Animal animal) {
        this.animal = animal;
        setCycleDuration(Duration.millis(1000.0 / animal.getSpeed()));
        setCycleCount(-1);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        animal.move();
        animal.update(v);
    }
}
