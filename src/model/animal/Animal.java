package model.animal;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.good.Good;

import java.util.Random;

public abstract class Animal {
    protected Random rand;
    protected int price;
    protected int space;
    protected double lifetime;
    protected double fullLifetime;
    protected double speed;
    protected double defaultSpeed;
    protected double angle;

    protected ImageView imageView;
    protected AnimalAnimation animalAnimation;
    protected Image[] images;
    protected Rectangle2D[][] cells;

    public Animal(int price, double lifetime, int space, double speed) {
        this.rand = new Random();
        this.price = price;
        this.lifetime = lifetime;
        this.fullLifetime = lifetime;
        this.space = space;
        this.speed = speed;
        this.defaultSpeed = speed;
        this.angle = rand.nextDouble() * 360.0;
    }

    public abstract void setAnimation();

    public void setAnimation(Image[] images, Rectangle2D[][] cells) {
        imageView = new ImageView();
        this.cells = cells;
        this.images = images;

        createImage(0);

        animalAnimation = new AnimalAnimation(this);
        Game.getInstance().getRoot().getChildren().add(imageView);
    }

    public void setPosition(double x, double y) {
        setAngle(0, 180);
        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;
        imageView.setLayoutX(x - cells[direction][0].getWidth() / 2);
        imageView.setLayoutY(y - cells[direction][0].getHeight() / 2);
    }

    public double getSpeed() {
        return speed;
    }

    public void setAngle(double first, double last) {
        this.angle = first + (last - first) * rand.nextDouble();
    }

    public void pause() {
        animalAnimation.pause();
    }

    public void play() {
        animalAnimation.play();
    }

    public double getWidth() {
        return cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getWidth();
    }

    public double getHeight() {
        return cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getHeight();
    }

    public double getY() {
        return imageView.getLayoutY() + cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getHeight() / 2;
    }

    public double getX() {
        return imageView.getLayoutX() + cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getWidth() / 2;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPrice() {
        return price;
    }

    public int getSpace() {
        return space;
    }

    public void move() {
        imageView.setLayoutX(imageView.getLayoutX() + speed * Math.cos(Math.toRadians(angle)));
        imageView.setLayoutY(imageView.getLayoutY() + speed * Math.sin(Math.toRadians(angle)));
    }

    public void createImage(int num) {

        if (num >= 24) num = 23;
        if (num < 0) num = 0;
        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;

        imageView.setImage(images[direction]);
        imageView.setViewport(cells[direction][num]);

        imageView.setLayoutX(rand.nextDouble() * (Game.getInstance().getWidth() - cells[direction][num].getWidth()));
        imageView.setLayoutY(rand.nextDouble() * (Game.getInstance().getHeight() - cells[direction][num].getHeight()));
    }

    public void update(double v) {
        int num = (int) (v * 23.05);
        if (num >= 24) num = 23;
        if (num < 0) num = 0;
        int direction = ((int) ((angle + 360.0 + 22.5) / 45.0)) % 8;

        if (imageView.getLayoutX() + cells[direction][num].getWidth() >= Game.getInstance().getWidth()) {
            if (imageView.getLayoutY() < cells[direction][num].getHeight()) {
                if (angle < 90 || angle > 180) setAngle(90, 180);
            } else if (imageView.getLayoutY() + 2 * cells[direction][num].getHeight() > Game.getInstance().getHeight()) {
                if (angle < 180 || angle > 270) setAngle(180, 270);
            } else {
                if (angle < 90 || angle > 270) setAngle(90, 270);
            }
        } else if (imageView.getLayoutX() <= 0) {
            if (imageView.getLayoutY() < cells[direction][num].getHeight()) {
                if (angle < 0 || angle > 90) setAngle(0, 90);
            } else if (imageView.getLayoutY() + 2 * cells[direction][num].getHeight() > Game.getInstance().getHeight()) {
                if (angle > 0) setAngle(-90, 0);
            } else {
                if (angle > 90) setAngle(-90, 90);
            }
        } else if (imageView.getLayoutY() + cells[direction][num].getHeight() >= Game.getInstance().getHeight()) {
            if (imageView.getLayoutX() < cells[direction][num].getWidth()) {
                if (angle < 270 && angle > 0) setAngle(270, 360);
            } else if (imageView.getLayoutX() + 2 * cells[direction][num].getWidth() > Game.getInstance().getWidth()) {
                if (angle < 180 || angle > 270) setAngle(180, 270);
            } else {
                if (angle > 0 && angle < 180) setAngle(180, 360);
            }
        } else if (imageView.getLayoutY() <= 0) {
            if (imageView.getLayoutX() < cells[direction][num].getWidth()) {
                if (angle < 0 || angle > 90) setAngle(0, 90);
            } else if (imageView.getLayoutX() + 2 * cells[direction][num].getWidth() > Game.getInstance().getWidth()) {
                if (angle < 90 || angle > 180) setAngle(90, 180);
            } else {
                if (angle < 0 || angle > 180) setAngle(0, 180);
            }
        }

        imageView.setImage(images[direction]);
        imageView.setViewport(cells[direction][num]);
    }
}
