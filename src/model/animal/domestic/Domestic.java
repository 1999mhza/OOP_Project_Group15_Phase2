package model.animal.domestic;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import model.Game;
import model.Grass;
import model.animal.Animal;
import model.good.Good;
import model.good.GoodList;

public abstract class Domestic extends Animal {
    protected double produceTime;
    protected double produceRemainingTime;
    protected String product;
    protected Grass eatingGrass;

    protected ProgressBar lifeBar;
    protected double preV, preV2;

    protected int currentAnimation;

    protected DeathAnimation deathAnimation;
    protected Image[] deathImage;
    protected Rectangle2D[][] deathCells;

    protected EatAnimation eatAnimation;
    protected Image[] eatImage;
    protected Rectangle2D[][] eatCells;

    public Domestic(int price, double produceTime, String product) {
        super(price, 20, 5, 1.0);
        this.produceTime = produceTime;
        this.produceRemainingTime = produceTime;
        this.product = product;

        currentAnimation = 1;

        lifeBar = new ProgressBar();
        lifeBar.setPrefHeight(10);
        lifeBar.setPrefWidth(40);
        lifeBar.setProgress(lifetime / fullLifetime);
        preV = 0;
        preV2 = 0;
    }

    public void setAnimation(Image[] images, Rectangle2D[][] cells, Image[] deathImage, Rectangle2D[][] deathCells, Image[] eatImage, Rectangle2D[][] eatCells) {

        super.setAnimation(images, cells);

        this.eatCells = eatCells;
        this.eatImage = eatImage;

        eatAnimation = new EatAnimation(this);

        this.deathCells = deathCells;
        this.deathImage = deathImage;

        deathAnimation = new DeathAnimation(this);
        deathAnimation.setOnFinished(event -> {
            Game game = Game.getInstance();
            game.getDomesticAnimals().remove(this);
            game.getRoot().getChildren().remove(imageView);
        });

        Game.getInstance().getRoot().getChildren().add(lifeBar);
        lifeBar.setLayoutX(imageView.getLayoutX() + cells[0][0].getWidth() / 2 - 20);
        lifeBar.setLayoutY(imageView.getLayoutY() + cells[0][0].getHeight());
    }

    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public boolean isAlive() {
        return lifetime > 0;
    }

    @Override
    public void update(double v) {

        if (!isHungry()) {
            speed = defaultSpeed;
        } else {
            speed = 2 * defaultSpeed;
        }

        v = (speed / defaultSpeed * v) % 1.0;
        super.update(v);

        if (v > preV) {
            lifetime -= (v - preV) / speed;
            produceRemainingTime -= (v - preV) / speed;
        } else if (v < preV) {
            lifetime -= (1.0 - preV + v) / speed;
            produceRemainingTime -= (1.0 - preV + v) / speed;
        }
        preV = v;

        if (isHungry()) {
            double distance = -1;
            for (Grass grass : Game.getInstance().getGrasses()) {
                double dis = Math.abs(grass.getX() - getX()) + Math.abs(grass.getY() - getY());
                if (distance == -1 || dis < distance) {
                    distance = dis;
                    if (grass.getX() - getX() == 0) {
                        angle = grass.getY() - getY() > 0 ? 90 : -90;
                        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
                        imageView.setImage(images[direction]);
                        imageView.setViewport(cells[direction][((int) (v * 24)) % 24]);
                    } else {
                        angle = Math.toDegrees(Math.atan((grass.getY() - getY()) / (grass.getX() - getX())) + (grass.getX() - getX() > 0 ? 0 : Math.PI));
                        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
                        imageView.setImage(images[direction]);
                        imageView.setViewport(cells[direction][((int) (v * 24)) % 24]);
                    }
                }
            }
        }

        if (produceRemainingTime <= 0) {
            produceRemainingTime = produceTime;
            GoodList goodList = GoodList.getGood(product);
            try {
                Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                good.setPosition(getX(), getY());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        if (isHungry()) {
            for (Grass grass : Game.getInstance().getGrasses()) {
                if (Math.sqrt(Math.pow(grass.getX() - getX(), 2) + Math.pow(grass.getY() - getY(), 2)) < 50) {
                    animalAnimation.stop();
                    preV = 0;
                    eatingGrass = grass;
                    currentAnimation = 2;
                    preV2 = 0;
                    eatAnimation.playFromStart();
                    return;
                }
            }
        }

        if (!isAlive()) {
            animalAnimation.stop();
            Game game = Game.getInstance();
            game.getRoot().getChildren().remove(lifeBar);
            currentAnimation = 3;
            deathAnimation.play();
            game.updateTask(this.getClass().getSimpleName(), false);
            return;
        }

        lifeBar.setProgress(lifetime / fullLifetime);
    }

    public void updateDeath(double v) {
        int direction = ((int) ((angle + 360.0 + 90.0) / 180.0)) % 2;
        imageView.setOpacity(1 - v);
        imageView.setImage(deathImage[direction]);
        imageView.setViewport(deathCells[direction][((int) (v * 24)) % 24]);
    }

    public void updateEat(double v) {

        if (v > preV2) {
            produceRemainingTime -= (v - preV2);
            eatingGrass.decreaseValue(v - preV2);
            lifetime += (v - preV2);
        } else if (v < preV2) {
            produceRemainingTime -= (1.0 - preV2 + v);
            eatingGrass.decreaseValue(1.0 - preV2 + v);
            lifetime += (1.0 - preV2 + v);
        }
        preV2 = v;

        lifeBar.setProgress(lifetime / fullLifetime);

        if (produceRemainingTime <= 0) {
            produceRemainingTime = produceTime;
            GoodList goodList = GoodList.getGood(product);
            try {
                Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                good.setPosition(getX(), getY());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        if (!eatingGrass.isExist()) {
            currentAnimation = 1;
            eatAnimation.stop();
            setAngle(0, 360);
            int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
            imageView.setImage(images[direction]);
            imageView.setViewport(cells[direction][0]);
            animalAnimation.playFromStart();
            return;
        }

        if (lifetime >= fullLifetime) {
            lifetime = fullLifetime;
            currentAnimation = 1;
            eatAnimation.stop();
            setAngle(0, 360);
            int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
            imageView.setImage(images[direction]);
            imageView.setViewport(cells[direction][0]);
            animalAnimation.playFromStart();
            return;
        }

        int direction = ((int) ((angle + 360.0 + 90.0) / 180.0)) % 2;
        imageView.setImage(eatImage[direction]);
        imageView.setViewport(eatCells[direction][((int) (v * 24)) % 24]);
    }

    public void move() {
        super.move();
        moveBar();
    }

    public void moveBar() {
        lifeBar.setLayoutX(imageView.getLayoutX() + cells[0][0].getWidth() / 2 - 20);
        lifeBar.setLayoutY(imageView.getLayoutY() + cells[0][0].getHeight());
    }

    public boolean isHungry() {
        return lifetime < fullLifetime / 2;
    }

    @Override
    public void play() {
        switch (currentAnimation) {
            case 1:
                animalAnimation.play();
                break;
            case 2:
                eatAnimation.play();
                break;
            case 3:
                deathAnimation.play();
                break;
        }
    }

    @Override
    public void pause() {
        switch (currentAnimation) {
            case 1:
                animalAnimation.pause();
                break;
            case 2:
                eatAnimation.pause();
                break;
            case 3:
                deathAnimation.pause();
                break;
        }
    }
}
