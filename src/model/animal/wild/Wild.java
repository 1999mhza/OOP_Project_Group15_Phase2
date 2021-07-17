package model.animal.wild;

import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;

import java.util.HashSet;

public abstract class Wild extends Animal {
    protected int cageNeeded;
    protected int cageRemaining;

    public Wild(int cageNeeded, int price, double step) {
        super(price, 5, 15, step);
        this.cageNeeded = cageNeeded;
        this.cageRemaining = cageNeeded;
    }

    public boolean isInCage() {
        return cageRemaining <= 0;
    }

    public void addCage() {
        if (cageRemaining > 0) cageRemaining--;
        else cageRemaining = 0;
    }

    public boolean isGreaterThan(Wild that) {
        return this.price > that.price;
    }

    public void move() {
        if (!isInCage()) {
            super.move();
        }
    }

    public void work() {
        if (isInCage()) {
            if (lifetime > 0) {
                lifetime--;
                if (lifetime == 0) {
                    Game.getInstance().getWildAnimals().remove(this);
                }
            }
        } else {
            if (cageRemaining < cageNeeded) cageRemaining++;

            HashSet<Good> removedGoods = new HashSet<>();
            for (Good good : Game.getInstance().getGoods()) {
                if (encounter(good)) {
                    removedGoods.add(good);
                }
            }
            Game.getInstance().getGoods().removeAll(removedGoods);

            HashSet<Domestic> removedDomestics = new HashSet<>();
            for (Domestic domestic : Game.getInstance().getDomesticAnimals()) {
                if (encounter(domestic)) {
                    removedDomestics.add(domestic);
                }
            }
            for (Domestic domestic : removedDomestics) {
                Game.getInstance().getDomesticAnimals().remove(domestic);
                Game.getInstance().updateTask(domestic.getClass().getSimpleName(), false);
            }
        }
    }

    @Override
    public String toString() {
        return (isInCage() ? "* " : "") + this.getClass().getSimpleName() + " " + (isInCage() ? lifetime : cageRemaining) + " " + "[" + getI() + " " + getJ() + "]";
    }
}
