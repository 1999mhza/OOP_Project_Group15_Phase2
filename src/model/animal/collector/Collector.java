package model.animal.collector;

import model.Game;
import model.animal.Animal;
import model.good.Good;

public abstract class Collector extends Animal {

    public Collector(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1.0);
    }

    @Override
    public void update(double v) {

        Game game = Game.getInstance();
        speed = defaultSpeed;
        int capacity = game.getWarehouse().getCapacity();
        if (capacity != 0 && !game.getGoods().isEmpty()) {
            for (Good good : game.getGoods()) {
                if (capacity >= good.getSpace()) {
                    speed = 2 * defaultSpeed;
                    break;
                }
            }
        }

        v = (speed / defaultSpeed * v) % 1.0;
        super.update(v);

        if (capacity != 0 && !Game.getInstance().getGoods().isEmpty()) {
            double distance = -1;
            for (Good good : Game.getInstance().getGoods()) {
                double dis = Math.abs(good.getY() - getY()) + Math.abs(good.getX() - getX());
                if (capacity >= good.getSpace() && (distance == -1 || dis < distance)) {
                    distance = dis;
                    if (good.getX() - getX() == 0) {
                        angle = good.getY() - getY() > 0 ? 90 : -90;
                        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
                        imageView.setImage(images[direction]);
                        imageView.setViewport(cells[direction][((int) (v * 24)) % 24]);
                    }
                    else {
                        angle = Math.toDegrees(Math.atan((good.getY() - getY()) / (good.getX() - getX())) + (good.getX() - getX() > 0 ? 0 : Math.PI));
                        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
                        imageView.setImage(images[direction]);
                        imageView.setViewport(cells[direction][((int) (v * 24)) % 24]);
                    }
                }
            }
        }

        for (Good good : game.getGoods()) {
            if (Math.sqrt(Math.pow(good.getX() - getX(), 2) + Math.pow(good.getY() - getY(), 2)) < 50) {
                if (game.getWarehouse().addGood(good)) {
                    good.pause();
                    game.getGoodPane().getChildren().remove(good.getImageView());
                    game.getGoods().remove(good);
                    game.updateTask(good.getClass().getSimpleName(), true);
                    setAngle(0, 360);
                    int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
                    imageView.setImage(images[direction]);
                    imageView.setViewport(cells[direction][((int) (v * 24)) % 24]);
                    update(v);
                    return;
                } else {
                    game.setResult("No Capacity!");
                }
            }
        }
    }
}
