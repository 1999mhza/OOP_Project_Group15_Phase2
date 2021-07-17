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
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1.0);
    }

    public void move() {
        int capacity = Game.getInstance().getWarehouse().getCapacity();

        if (capacity != 0 && !Game.getInstance().getGoods().isEmpty()) {
            System.out.println("OK");
            double distance = -1;

            for (Good good : Game.getInstance().getGoods()) {
                double dis = Math.abs(good.getI() - getI()) + Math.abs(good.getJ() - getJ());

                if (capacity >= good.getSpace() && (distance == -1 || dis < distance)) {
                    distance = dis;
                    if (good.getJ() - getJ() == 0) angle = good.getI() - getI() > 0 ? 90 : -90;
                    else angle = Math.atan((good.getI() - getI()) / (good.getJ() - getJ())) + (good.getJ() - getJ() > 0 ? 0 : Math.PI);
                }
            }
        }

        super.move();
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
