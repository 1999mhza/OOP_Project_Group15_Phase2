package model.animal.collector;

import model.Game;
import model.animal.Animal;
import model.animal.wild.Wild;
import model.good.Good;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public abstract class Collector extends Animal {

    public Collector(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
    }

    public void move() {
        preI = i;
        preJ = j;
        boolean horizontal = false;
        boolean vertical = false;
        int distance = Integer.MAX_VALUE;

        ArrayList<Good> list1 = new ArrayList<>(Game.getInstance().getGoods());
        ArrayList<Good> list2 = new ArrayList<>();

        for (int k = Game.getInstance().getGoods().size(); k > 0; k--) {
            list2.add(list1.remove(rand.nextInt(k)));
        }

        int capacity = Game.getInstance().getWarehouse().getCapacity();
        for (Good good : list2) {
            int ii = good.getI();
            int jj = good.getJ();
            int dis = Math.abs(ii - i) + Math.abs(jj - j);

            if (capacity >= good.getSpace() && dis < distance) {
                distance = dis;
                vertical = Math.abs(ii - i) > Math.abs(jj - j);
                horizontal = (ii - i) + (jj - j) > 0;
            }
        }

        if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
        else if (distance != 0) super.move(vertical, horizontal);
    }

    public void work() {
        for (Good good : new HashSet<>(Game.getInstance().getGoods())) {
            if (Game.getInstance().getWarehouse().addGood(new HashSet<>(Collections.singletonList(good)))) {
                Game.getInstance().getGoods().remove(good);
                Game.getInstance().updateTask(good.getClass().getSimpleName(), true);
            }
        }
    }
}
