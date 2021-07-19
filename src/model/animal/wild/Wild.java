package model.animal.wild;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cage;
import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;

import java.util.HashSet;

public abstract class Wild extends Animal {
    protected double cageNeeded;
    protected double cageRemaining;

    protected int currentAnimation;
    protected double preV;

    protected CageAnimation cageAnimation;
    protected Image cagedWildImage;
    protected Rectangle2D[] cagedWildCells;

    protected ImageView cageImageView;
    protected Image buildCageImage;
    protected Rectangle2D[] buildCageCells;
    protected Image breakCageImage;
    protected Rectangle2D[] breakCageCells;

    protected int numberOfCageImage;
    protected double decreaseCageTime;

    protected BreakAnimation breakAnimation;

    public Wild(double cageNeeded, int price, double speed) {
        super(price, 10, 15, speed);
        this.cageNeeded = cageNeeded;
        this.cageRemaining = cageNeeded;
        this.decreaseCageTime = 0;
    }

    public void setAnimation(Image[] images, Rectangle2D[][] cells, Image cagedWildImage, Rectangle2D[] cagedWildCells) {
        super.setAnimation(images, cells);

        this.cagedWildImage = cagedWildImage;
        this.cagedWildCells = cagedWildCells;

        Game game = Game.getInstance();
        Cage cage = game.getCage();

        currentAnimation = 1;
        preV = 0;

        cageImageView = new ImageView();

        decreaseCageTime = cage.getCageTimeDecrease();
        buildCageImage = cage.getBuildImage();
        buildCageCells = cage.getBuildCells();
        breakCageImage = cage.getBreakImage();
        breakCageCells = cage.getBreakCells();
        numberOfCageImage = cage.getBuildCellsNumber();

        moveCage();
        cageImageView.setVisible(false);
        game.getRoot().getChildren().add(cageImageView);

        cageAnimation = new CageAnimation(this);

        breakAnimation = new BreakAnimation(this);
        breakAnimation.setOnFinished(event -> {
            game.getWildAnimals().remove(this);
            game.getRoot().getChildren().remove(imageView);
            game.getRoot().getChildren().remove(cageImageView);
        });

        imageView.setOnMouseClicked(mouseEvent -> {
            if (isFixedCage()) {
                if (game.getWarehouse().addWild(this)) {
                    pause();
                    game.getRoot().getChildren().remove(imageView);
                    game.getRoot().getChildren().remove(cageImageView);
                    game.getWildAnimals().remove(this);
                    game.updateTask(this.getClass().getSimpleName(), true);
                } else {
                    game.setResult("No Capacity!");
                }
            } else if (cageRemaining >= cageNeeded - decreaseCageTime) {
                decreaseCageTime = cage.getCageTimeDecrease();
                buildCageImage = cage.getBuildImage();
                buildCageCells = cage.getBuildCells();
                breakCageImage = cage.getBreakImage();
                breakCageCells = cage.getBreakCells();
                numberOfCageImage = cage.getBuildCellsNumber();

                cageImageView.setImage(buildCageImage);
                cageRemaining = cageNeeded - decreaseCageTime;

                cageRemaining--;
                speed = defaultSpeed * cageRemaining / (cageNeeded - decreaseCageTime);
                currentAnimation = 2;
                cageImageView.setVisible(true);
                cageAnimation.playFromStart();
            } else {
                cageRemaining--;
                if (cageRemaining <= 0) {
                    animalAnimation.pause();
                    cageAnimation.pause();
                    currentAnimation = 3;

                    imageView.setImage(cagedWildImage);
                    cageImageView.setImage(breakCageImage);
                    imageView.setLayoutX(cageImageView.getLayoutX() - (cagedWildCells[0].getWidth() - breakCageCells[0].getWidth()) / 2);
                    imageView.setLayoutY(cageImageView.getLayoutY() - (cagedWildCells[0].getHeight() - breakCageCells[0].getHeight()) / 2);
                    breakAnimation.play(fullLifetime + 2 * decreaseCageTime);
                }
            }
        });

        cageImageView.setOnMouseClicked(mouseEvent -> {
            if (isFixedCage()) {
                if (game.getWarehouse().addWild(this)) {
                    pause();
                    game.getRoot().getChildren().remove(imageView);
                    game.getRoot().getChildren().remove(cageImageView);
                    game.getWildAnimals().remove(this);
                    game.updateTask(this.getClass().getSimpleName(), true);
                } else {
                    game.setResult("No Capacity!");
                }
            } else if (cageRemaining >= cageNeeded - decreaseCageTime) {
                decreaseCageTime = cage.getCageTimeDecrease();
                buildCageImage = cage.getBuildImage();
                buildCageCells = cage.getBuildCells();
                breakCageImage = cage.getBreakImage();
                breakCageCells = cage.getBreakCells();
                numberOfCageImage = cage.getBuildCellsNumber();

                cageImageView.setImage(buildCageImage);
                cageRemaining = cageNeeded - decreaseCageTime;

                cageRemaining--;
                speed = defaultSpeed * cageRemaining / (cageNeeded - decreaseCageTime);
                currentAnimation = 2;
                cageImageView.setVisible(true);
                cageAnimation.playFromStart();
            } else {
                cageRemaining--;
                if (cageRemaining <= 0) {
                    animalAnimation.pause();
                    cageAnimation.pause();
                    currentAnimation = 3;

                    imageView.setImage(cagedWildImage);
                    cageImageView.setImage(breakCageImage);
                    imageView.setLayoutX(cageImageView.getLayoutX() - (cagedWildCells[0].getWidth() - buildCageCells[0].getWidth()) / 2);
                    imageView.setLayoutY(cageImageView.getLayoutY() - (cagedWildCells[0].getHeight() - buildCageCells[0].getHeight()) / 2);
                    breakAnimation.play(fullLifetime + 2 * decreaseCageTime);
                }
            }
        });
    }

    public boolean isInCage() {
        return cageRemaining < cageNeeded;
    }

    public boolean isFixedCage() {
        return cageRemaining <= 0;
    }

    public void move() {
        if (!isFixedCage()) {
            super.move();
            moveCage();
        }
    }

    public void moveCage() {
        cageImageView.setLayoutX(imageView.getLayoutX() + (cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getWidth() - buildCageCells[0].getWidth()) / 2);
        cageImageView.setLayoutY(imageView.getLayoutY() + (cells[((int) ((angle + 360.0 + 22.5) / 45.0)) % 8][0].getHeight() - buildCageCells[0].getHeight()) / 2);
    }

    public void updateCage(double v) {
        if (v > preV) {
            cageRemaining += (v - preV);
        } else if (v < preV) {
            cageRemaining += (1.0 - preV + v);
        }
        preV = v;
        speed = defaultSpeed * cageRemaining / (cageNeeded - decreaseCageTime);

        int num = (int) (numberOfCageImage * (1.0 - cageRemaining / (cageNeeded - decreaseCageTime)));
        if (num >= numberOfCageImage - 1) num = numberOfCageImage - 1;
        if (num < 0) num = 0;

        cageImageView.setViewport(buildCageCells[num]);

        if (cageRemaining >= cageNeeded - decreaseCageTime) {
            cageAnimation.stop();
            cageImageView.setVisible(false);
            currentAnimation = 1;
            cageRemaining = cageNeeded - decreaseCageTime;
            preV = 0;
        }
    }

    public void updateBreak(double v) {
        lifetime = (1 - v) * (fullLifetime + 2 * decreaseCageTime);
        if (lifetime < 2 && lifetime > 1.5) {
            imageView.setOpacity(0.5);
            cageImageView.setOpacity(0.5);
        } else if (lifetime < 1.5 && lifetime > 1) {
            imageView.setOpacity(1);
            cageImageView.setOpacity(1);
        } else if (lifetime < 1 && lifetime > 0.5) {
            imageView.setOpacity(0.5);
            cageImageView.setOpacity(0.5);
        } else {
            imageView.setOpacity(1);
            cageImageView.setOpacity(1);
        }

        int num = ((int) (v * 23.05 * (fullLifetime + 2 * decreaseCageTime))) % 24;
        imageView.setViewport(cagedWildCells[num]);
        cageImageView.setViewport(breakCageCells[num]);
    }

    @Override
    public void update(double v) {
        v = (speed / defaultSpeed * v) % 1.0;
        super.update(v);

        if (!isFixedCage()) {
            Game game = Game.getInstance();
            HashSet<Good> removedGoods = new HashSet<>();
            for (Good good : game.getGoods()) {
                if (Math.sqrt(Math.abs(good.getX() - getX()) + Math.abs(good.getY() - getY())) < 25) {
                    removedGoods.add(good);
                }
            }
            for (Good good : removedGoods) {
                good.pause();
                game.getRoot().getChildren().remove(good.getImageView());
                game.getGoods().remove(good);
            }
            HashSet<Domestic> removedDomestics = new HashSet<>();
            for (Domestic domestic : game.getDomesticAnimals()) {
                if (domestic.isAlive() && Math.sqrt(Math.pow(domestic.getX() - getX(), 2) + Math.pow(domestic.getY() - getY(), 2)) < 25) {
                    removedDomestics.add(domestic);
                }
            }
            for (Domestic domestic : removedDomestics) {
                domestic.pause();
                game.getRoot().getChildren().remove(domestic.getImageView());
                game.getRoot().getChildren().remove(domestic.getLifeBar());
                game.getDomesticAnimals().remove(domestic);
            }
        }
    }

    @Override
    public void play() {
        switch (currentAnimation) {
            case 1:
                animalAnimation.play();
                break;
            case 2:
                animalAnimation.play();
                cageAnimation.play();
                break;
            case 3:
                breakAnimation.play();
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
                animalAnimation.pause();
                cageAnimation.pause();
                break;
            case 3:
                breakAnimation.pause();
                break;
        }
    }
}
