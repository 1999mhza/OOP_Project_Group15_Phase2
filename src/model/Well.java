package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Well {
    private int level;
    private int finalLevel;
    private double bucketCapacity;
    private double water;
    private double fillTime;
    private int fillPrice;
    private int upgradePrice;
    private boolean isWorking;

    protected ImageView imageView;
    protected WellAnimation wellAnimation;
    protected Rectangle2D[] cells;
    protected ProgressBar progressBar;

    public Well() {
        finalLevel = 4;
        level = 1;
        bucketCapacity = 5;
        water = bucketCapacity;
        fillTime = 4;
        fillPrice = 20;
        upgradePrice = 300;
        isWorking = false;

        Image image = new Image(new File("src/resource/Well/" + level + ".png").toURI().toString());
        imageView = new ImageView(image);
        progressBar = new ProgressBar();

        wellAnimation = new WellAnimation(this);
        wellAnimation.setOnFinished(event -> {
            isWorking = false;
            imageView.setViewport(cells[0]);
        });

        cells = new Rectangle2D[16];
        double cellWidth = image.getWidth() / 4;
        double cellHeight = image.getHeight() / 4;

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                cells[j * 4 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        createImage(0);
    }

    public void createImage(int num) {

        Game game = Game.getInstance();
        game.getParent().getChildren().add(imageView);
        game.getParent().getChildren().add(progressBar);

        imageView.setLayoutX(game.getScale() * (360 - game.getOldX()) + game.getNewX() - cells[0].getWidth());
        imageView.setLayoutY(game.getScale() * (165 - game.getOldY()) + game.getNewY() - cells[0].getHeight());

        imageView.setOnMouseClicked(mouseEvent -> {
            if (isWorking) {
                game.setResult("Well Is Working!");
                return;
            }
            if (water > 0) {
                game.setResult("Not Empty!");
                return;
            }
            if (fillPrice > Game.getInstance().getCoin()) {
                game.setResult("Not Enough Coin!");
                return;
            }
            Game.getInstance().decreaseCoin(fillPrice);
            isWorking = true;
            wellAnimation.play();
        });

        progressBar.setLayoutX(game.getScale() * (360 - game.getOldX()) + game.getNewX() - cells[0].getWidth() - 10);
        progressBar.setLayoutY(game.getScale() * (165 - game.getOldY()) + game.getNewY() - cells[0].getHeight() / 2);

        progressBar.setRotate(270);

        progressBar.setPrefWidth(50);
        progressBar.setPrefHeight(10);

        progressBar.setProgress(water / bucketCapacity);

        if (num >= 15) num = 15;
        if (num < 0) num = 0;
        imageView.setViewport(cells[num]);
    }

    public int getLevel() {
        return level;
    }

    public void play() {
        if (isWorking) wellAnimation.play();
    }

    public void pause() {
        if (isWorking) wellAnimation.pause();
    }

    public void upgrade() {
        level++;
        bucketCapacity += 1;
        fillPrice -= 2;
        upgradePrice *= 1.2;
        progressBar.setProgress(water / bucketCapacity);

        Image image = new Image(new File("src/resource/Well/" + level + ".png").toURI().toString());
        double cellWidth = image.getWidth() / 4;
        double cellHeight = image.getHeight() / 4;
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                cells[j * 4 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        imageView.setImage(image);
        imageView.setViewport(cells[0]);
    }

    public void update(double v) {
        water = v * bucketCapacity;
        progressBar.setProgress(v);
        imageView.setViewport(cells[((int) (v * 63.1)) % 16]);
    }

    public double getFillTime() {
        return fillTime;
    }

    public void decreaseWater() {
        water = Math.max(0, water - 1);
        progressBar.setProgress(water / bucketCapacity);
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public double getWater() {
        return water;
    }

    public boolean isWorking() {
        return isWorking;
    }
}
