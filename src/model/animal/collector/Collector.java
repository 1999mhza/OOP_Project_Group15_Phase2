package model.animal.collector;

import model.Game;
import model.animal.Animal;
import model.good.Good;

import java.util.Collections;
import java.util.HashSet;

public abstract class Collector extends Animal {
    protected boolean found;

    public Collector(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1.0);
        found = false;
    }

    @Override
    public void move() {
        int capacity = Game.getInstance().getWarehouse().getCapacity();

        if (capacity != 0 && !Game.getInstance().getGoods().isEmpty()) {
            double distance = -1;
            for (Good good : Game.getInstance().getGoods()) {
                double dis = Math.abs(good.getY() - getY()) + Math.abs(good.getX() - getX());
                if (capacity >= good.getSpace() && (distance == -1 || dis < distance)) {
                    distance = dis;
                    if (good.getX() - getX() == 0) angle = good.getY() - getY() > 0 ? 90 : -90;
                    else
                        angle = Math.toDegrees(Math.atan((good.getY() - getY()) / (good.getX() - getX())) + (good.getX() - getX() > 0 ? 0 : Math.PI));
                }
            }

            if (distance != -1) {
                found = true;
                speed = 2 * defaultSpeed;
            } else {
                found = false;
                speed = defaultSpeed;
            }
        } else {
            found = false;
            speed = defaultSpeed;
        }

        super.move();
    }

    @Override
    public void update(double v) {
        v = (speed / defaultSpeed * v) % 1.0;
        super.update(v);

        Game game = Game.getInstance();
        for (Good good : game.getGoods()) {
            if (Math.sqrt(Math.pow(good.getX() - getX(), 2) + Math.pow(good.getY() - getY(), 2)) < 50) {
                if (game.getWarehouse().addGood(good)) {
                    good.pause();
                    game.getRoot().getChildren().remove(good.getImageView());
                    game.getGoods().remove(good);
                    game.updateTask(good.getClass().getSimpleName(), true);
                    return;
                } else {
                    game.setResult("No Capacity!");
                }
            }
        }
    }
}
