package model.animal.protective;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.animal.wild.Wild;

public class BattleAnimation extends Transition {
    private Protective protective;
    private Wild wild;

    public BattleAnimation(Protective protective, Wild wild) {
        this.protective = protective;
        this.wild = wild;
        setCycleDuration(Duration.millis(1000.0));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        protective.updateBattle(v);
    }
}
