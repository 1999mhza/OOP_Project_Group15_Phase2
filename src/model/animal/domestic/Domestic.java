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

    private ProgressBar lifeBar;
    private double preV;

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
                if (Math.abs(grass.getX() - getX()) + Math.abs(grass.getY() - getY()) < 25) {
                    grass.decreaseValue();
                    animalAnimation.pause();
                    currentAnimation = 2;
                    eatAnimation.play();
                    eatAnimation.setOnFinished(event -> {
                        grass.decreaseImage();
                        lifetime++;
                        lifeBar.setProgress(lifetime / fullLifetime);
                        if (grass.isExist() && lifetime < fullLifetime - 1) {
                            grass.decreaseValue();
                            eatAnimation.play();
                        } else {
                            currentAnimation = 1;
                            animalAnimation.play();
                        }
                    });
                    return;
                }
            }
        }

        if (!isAlive()) {
            animalAnimation.pause();
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

        int num = (int) (v * 23.05);
        if (num >= 24) num = 23;
        if (num < 0) num = 0;
        int direction = ((int) ((angle + 360.0 + 90.0) / 180.0)) % 2;

        imageView.setOpacity(1 - v);
        imageView.setImage(deathImage[direction]);
        imageView.setViewport(deathCells[direction][num]);
    }

    public void updateEat(double v) {

        int num = (int) (v * 23.05);
        if (num >= 23) num = 23;
        if (num < 0) num = 0;
        int direction = ((int) ((angle + 360.0 + 90.0) / 180.0)) % 2;

        imageView.setImage(eatImage[direction]);
        imageView.setViewport(eatCells[direction][num]);
    }

    public void move() {
        if (!isHungry()) {
            speed = defaultSpeed;
        } else {
            speed = 2 * defaultSpeed;

            double distance = -1;
            for (Grass grass : Game.getInstance().getGrasses()) {
                double dis = Math.abs(grass.getX() - getX()) + Math.abs(grass.getY() - getY());
                if (distance == -1 || dis < distance) {
                    distance = dis;
                    if (grass.getX() - getX() == 0) angle = grass.getY() - getY() > 0 ? 90 : -90;
                    else
                        angle = Math.toDegrees(Math.atan((grass.getY() - getY()) / (grass.getX() - getX())) + (grass.getX() - getX() > 0 ? 0 : Math.PI));
                }
            }
        }
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
