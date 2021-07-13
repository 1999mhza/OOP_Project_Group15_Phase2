package model.animal.wild;

import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;

import java.util.HashSet;

public abstract class Wild extends Animal {
    protected int cageNeeded;
    protected int cageRemaining;

    public Wild(int cageNeeded, int price, int step) {
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
        preI = i;
        preJ = j;
        if (!isInCage()) {
            super.move(rand.nextBoolean(), rand.nextBoolean());
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
                if (good.getI() == i && good.getJ() == j) {
                    removedGoods.add(good);
                } else if (preI != -1 && preJ != -1) {
                    if (i == preI && good.getI() == i && good.getJ() >= Math.min(preJ, j) && good.getJ() <= Math.max(preJ, j)) {
                        removedGoods.add(good);
                    }
                    if (j == preJ && good.getJ() == j && good.getI() >= Math.min(preI, i) && good.getI() <= Math.max(preI, i)) {
                        removedGoods.add(good);
                    }
                }
            }
            Game.getInstance().getGoods().removeAll(removedGoods);

            HashSet<Domestic> removedDomestics = new HashSet<>();
            for (Domestic domestic : Game.getInstance().getDomesticAnimals()) {
                if (domestic.getI() == i && domestic.getJ() == j) {
                    removedDomestics.add(domestic);
                } else if (preI != -1 && preJ != -1 && domestic.getPreI() != -1 && domestic.getPreJ() != -1 && this.encounter(domestic)) {
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
        return (isInCage() ? "* " : "") + this.getClass().getSimpleName() + " " + (isInCage() ? lifetime : cageRemaining) + " " + "[" + (i + 1) + " " + (j + 1) + "]";
    }
}
