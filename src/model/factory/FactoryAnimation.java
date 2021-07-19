package model.factory;

import javafx.animation.Transition;
import javafx.util.Duration;

public class FactoryAnimation extends Transition {
    private Factory factory;

    public FactoryAnimation(Factory factory) {
        this.factory = factory;
        setCycleCount(1);
    }

    public void play(double time) {
        setCycleDuration(Duration.millis(time * 1000));
        super.play();
    }

    @Override
    protected void interpolate(double v) {
        factory.update(v);
    }
}
