package model.animal.protective;

import model.Game;
import model.animal.Animal;
import model.animal.wild.Wild;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Protective extends Animal {

    public Protective(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
    }

    public void move() {
        preI = i;
        preJ = j;
        boolean horizontal = false;
        boolean vertical = false;
        int distance = Integer.MAX_VALUE;

        ArrayList<Wild> list1 = new ArrayList<>(Game.getInstance().getWildAnimals());
        ArrayList<Wild> list2 = new ArrayList<>();

        for (int k = Game.getInstance().getWildAnimals().size(); k > 0; k--) {
            list2.add(list1.remove(rand.nextInt(k)));
        }

        for (Wild wild : list2) {
            if (!wild.isInCage()) {
                int ii = wild.getI();
                int jj = wild.getJ();
                int dis = Math.abs(ii - i) + Math.abs(jj - j);

                if (dis < distance) {
                    distance = dis;
                    vertical = Math.abs(ii - i) > Math.abs(jj - j);
                    horizontal = (ii - i) + (jj - j) > 0;
                }
            }
        }
        if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
        else if (distance != 0) super.move(vertical, horizontal);
    }

    public void work() {
        ArrayList<Wild> list1 = new ArrayList<>(Game.getInstance().getWildAnimals());
        ArrayList<Wild> list2 = new ArrayList<>();

        for (int k = Game.getInstance().getWildAnimals().size(); k > 0; k--) {
            list2.add(list1.remove(rand.nextInt(k)));
        }

        Wild removedWild = null;

        for (Wild wild : list2) {
            if (wild.getI() == i && wild.getJ() == j && !wild.isInCage()) {
                if (removedWild == null) {
                    removedWild = wild;
                } else if (wild.isGreaterThan(removedWild)) {
                    removedWild = wild;
                }
            } else if (preI != -1 && preJ != -1 && wild.getPreI() != -1 && wild.getPreJ() != -1 && !wild.isInCage() && wild.encounter(this)) {
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
