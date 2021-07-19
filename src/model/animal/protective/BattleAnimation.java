package model.animal.protective;

import javafx.animation.Transition;
import javafx.util.Duration;

public class BattleAnimation extends Transition {
    private Protective protective;

    public BattleAnimation(Protective protective) {
        this.protective = protective;
        setCycleDuration(Duration.millis(1000.0));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        protective.updateBattle(v);
    }
}
