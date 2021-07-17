package model.animal.domestic;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Grass;
import model.animal.Animal;
import model.animal.AnimalAnimation;
import model.good.Good;
import model.good.GoodList;

import java.util.ArrayList;

public abstract class Domestic extends Animal {
    protected double produceTime;
    protected double produceRemainingTime;
    protected String product;
    private double fulLifetime;

    private ProgressBar lifeBar;
    private double preV;

    protected DeathAnimation deathAnimation;
    protected Image deathImage;
    protected Rectangle2D[] deathCells;

    public Domestic(int price, double produceTime, String product) {
        super(price, 10, 5, 1.0);
        this.fulLifetime = 10;
        this.produceTime = produceTime;
        this.produceRemainingTime = produceTime;
        this.product = product;

        lifeBar = new ProgressBar();
        lifeBar.setPrefHeight(10);
        lifeBar.setPrefWidth(40);
        lifeBar.setProgress(lifetime / fulLifetime);
        preV = 0;
    }

    public void setAnimation(Image[] images, Rectangle2D[][] cells, Image deathImage, Rectangle2D[] deathCells) {

        super.setAnimation(images, cells);

        this.deathCells = deathCells;
        this.deathImage = deathImage;

        deathAnimation = new DeathAnimation(this);
        deathAnimation.setOnFinished(event -> Game.getInstance().getRoot().getChildren().remove(imageView));

        Game.getInstance().getRoot().getChildren().add(lifeBar);
        lifeBar.setLayoutX(imageView.getLayoutX());
        lifeBar.setLayoutY(imageView.getLayoutY() + cells[2][0].getHeight());
    }

    @Override
    public void update(double v) {
        v = (lifetime > 5) ? v : ((2 * v) % 1.0);
        super.update(v);

        if (v > preV) {
            lifetime -= (v - preV) / speed;
            produceRemainingTime -= (v - preV) / speed;
        } else if (v < preV) {
            lifetime -= (1.0 - preV + v) / speed;
            produceRemainingTime -= (1.0 - preV + v) / speed;
        }
        preV = v;

        if (produceRemainingTime <= 0) {
            produceRemainingTime = produceTime;
            GoodList goodList = GoodList.getGood(product);
            try {
                Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                System.out.println("G: " + Game.getInstance().getTimer());
                //good.setPlace(i, j);
                Game.getInstance().getGoods().add(good);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        if (lifetime <= 0) {
            animalAnimation.pause();
            Game game = Game.getInstance();
            game.getDomesticAnimals().remove(this);
            game.getRoot().getChildren().remove(lifeBar);
            deathAnimation.play();
            game.updateTask(this.getClass().getSimpleName(), false);
            return;
        }

        lifeBar.setProgress(lifetime / fulLifetime);
    }

    public void updateDeath(double v) {

        int num = (int) (v * 23.05);
        if (num >= 24) num = 23;
        if (num < 0) num = 0;

        imageView.setOpacity(1 - v);
        imageView.setImage(deathImage);
        imageView.setViewport(deathCells[num]);
    }

    public void move() {
        if (this.lifetime > 5) {
            speed = deafultSpeed;
        } else {
            speed = 2 * deafultSpeed;
            /*
            double distance = -1;
            for (Grass grass : Game.getInstance().getGrasses()) {
                double dis = Math.abs(grass.getI() - i) + Math.abs(grass.getJ() - j);
                if (distance == -1 || dis < distance) {
                    distance = dis;
                    if (grass.getJ() - j == 0) angle = grass.getI() - i > 0 ? 90 : -90;
                    else
                        angle = Math.atan((grass.getI() - i) / (grass.getJ() - j)) + (grass.getJ() - j > 0 ? 0 : Math.PI);
                }
            }*/
        }
        super.move();
        moveBar();
    }

    public void moveBar() {
        lifeBar.setLayoutX(imageView.getLayoutX());
        lifeBar.setLayoutY(imageView.getLayoutY() + cells[2][0].getHeight());
    }

    public void work() {
        if (produceRemainingTime > 0) {
            produceRemainingTime--;
            if (produceRemainingTime == 0) {
                produceRemainingTime = produceTime;
                GoodList goodList = GoodList.getGood(product);
                try {
                    Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                    //good.setPlace(i, j);
                    Game.getInstance().getGoods().add(good);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        if (lifetime > 0) {
            lifetime--;
            if (lifetime == 0) {
                Game.getInstance().getDomesticAnimals().remove(this);
                Game.getInstance().updateTask(this.getClass().getSimpleName(), false);
            }
        }
    }

    public void eatGrass() {
        //Game.getInstance().getGrass()[i][j]--;
        lifetime = fulLifetime + 1;
    }

    public boolean isHungry() {
        return lifetime <= 5;
    }

    public boolean isLessThan(Domestic that) {
        return this.lifetime < that.lifetime;
    }

    @Override
    public String toString() {
        return (lifetime > 5 ? "" : "* ") + this.getClass().getSimpleName() + " " + lifetime + " " + "[" + getI() + " " + getJ() + "]";
    }
}
