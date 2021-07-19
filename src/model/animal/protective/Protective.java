package model.animal.protective;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import model.Game;
import model.animal.Animal;
import model.animal.wild.Wild;

import java.io.File;
import java.util.ArrayList;

public abstract class Protective extends Animal {
    protected boolean found;
    protected int currentAnimation;

    protected BattleAnimation battleAnimation;
    protected Image battleImage;
    protected Rectangle2D[] battleCells;

    public Protective(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1.0);
        found = false;
        currentAnimation = 1;
    }

    @Override
    public void setAnimation(Image[] images, Rectangle2D[][] cells) {
        super.setAnimation(images, cells);

        battleImage = new Image(new File("src/resource/battle.png").toURI().toString());
        int numberOfDeathRows = 4;
        int numberOfDeathColumns = 5;

        battleCells = new Rectangle2D[20];
        double cellWidth = battleImage.getWidth() / numberOfDeathColumns;
        double cellHeight = battleImage.getHeight() / numberOfDeathRows;

        for (int j = 0; j < numberOfDeathRows; j++) {
            for (int k = 0; k < numberOfDeathColumns; k++) {
                battleCells[j * numberOfDeathColumns + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    @Override
    public void move() {
        double distance = -1;

        for (Wild wild : Game.getInstance().getWildAnimals()) {
            if (!wild.isInCage()) {
                double dis = Math.abs(wild.getY() - getY()) + Math.abs(wild.getX() - getX());
                if (distance == -1 || dis < distance) {
                    distance = dis;
                    if (wild.getX() - getX() == 0) angle = wild.getY() - getY() > 0 ? 90 : -90;
                    else
                        angle = Math.toDegrees(Math.atan((wild.getY() - getY()) / (wild.getX() - getX())) + (wild.getX() - getX() > 0 ? 0 : Math.PI));
                }
            }
        }

        if (distance != -1) {
            found = true;
            speed = 2 * defaultSpeed;
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
        for (Wild wild : game.getWildAnimals()) {
            if (Math.sqrt(Math.pow(wild.getX() - getX(), 2) + Math.pow(wild.getY() - getY(), 2)) < 50) {
                if (!wild.isInCage()) {
                    wild.pause();
                    pause();
                    game.getRoot().getChildren().remove(wild.getImageView());
                    game.getWildAnimals().remove(wild);
                    battleAnimation = new BattleAnimation(this, wild);
                    battleAnimation.setOnFinished(event -> {
                        game.getProtectiveAnimals().remove(this);
                        game.getRoot().getChildren().remove(imageView);
                    });
                    currentAnimation = 2;
                    imageView.setLayoutX(imageView.getLayoutX() + (cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getWidth() - battleCells[0].getWidth()) / 2);
                    imageView.setLayoutY(imageView.getLayoutY() + (cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getHeight() - battleCells[0].getHeight()) / 2);
                    battleAnimation.play();
                    return;
                }
            }
        }
    }

    public void updateBattle(double v) {
        int num = (int) (v * 19.05);
        if (num >= 20) num = 20;
        if (num < 0) num = 0;

        imageView.setImage(battleImage);
        imageView.setViewport(battleCells[num]);
    }

    @Override
    public void play() {
        switch (currentAnimation) {
            case 1:
                animalAnimation.play();
                break;
            case 2:
                battleAnimation.play();
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
                battleAnimation.pause();
                break;
        }
    }
}
