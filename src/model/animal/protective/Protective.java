package model.animal.protective;

import model.Game;
import model.animal.Animal;
import model.animal.wild.Wild;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Protective extends Animal {

    public Protective(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1.0);
    }

    public void move() {
        double distance = -1;

        for (Wild wild : new ArrayList<>(Game.getInstance().getWildAnimals())) {
            if (!wild.isInCage()) {
                double dis = Math.abs(wild.getI() - getI()) + Math.abs(wild.getJ() - getJ());
                if (distance == -1 || dis < distance) {
                    distance = dis;
                    if (wild.getJ() - getJ() == 0) angle = wild.getI() - getI() > 0 ? 90 : -90;
                    else angle = Math.atan((wild.getI() - getI()) / (wild.getJ() - getJ())) + (wild.getJ() - getJ() > 0 ? 0 : Math.PI);
                }
            }
        }

        super.move();
    }

    public void work() {
        ArrayList<Wild> list1 = new ArrayList<>(Game.getInstance().getWildAnimals());
        ArrayList<Wild> list2 = new ArrayList<>();

        for (int k = Game.getInstance().getWildAnimals().size(); k > 0; k--) {
            list2.add(list1.remove(rand.nextInt(k)));
        }

        Wild removedWild = null;

        for (Wild wild : list2) {
            if (wild.getI() == getI() && wild.getJ() == getJ() && !wild.isInCage()) {
                if (removedWild == null) {
                    removedWild = wild;
                } else if (wild.isGreaterThan(removedWild)) {
                    removedWild = wild;
                }
            } else if (!wild.isInCage() && wild.encounter(this)) {
                if (removedWild == null) {
                    removedWild = wild;
                } else if (wild.isGreaterThan(removedWild)) {
                    removedWild = wild;
                }
            }
        }

        if (removedWild == null) return;
        Game.getInstance().getWildAnimals().remove(removedWild);
        Game.getInstance().getProtectiveAnimals().remove(this);
    }
}
