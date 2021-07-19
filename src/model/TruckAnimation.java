package model;

import javafx.animation.Transition;
import javafx.util.Duration;

public class TruckAnimation extends Transition {
    private Truck truck;

    public TruckAnimation(Truck truck) {
        this.truck = truck;
        setCycleCount(2);
    }

    @Override
    protected void interpolate(double v) {
        truck.update(v);
    }

    public void play(double time) {
        setCycleDuration(Duration.millis(1000 * time / 2));
        super.play();
    }
}
