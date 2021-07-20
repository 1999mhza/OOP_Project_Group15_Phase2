package model.factory;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.good.Good;
import model.good.GoodList;

import java.io.File;
import java.util.Random;

public abstract class Factory {
    protected int price;
    protected int upgradePrice;
    protected int level;
    protected int finalLevel;
    protected int number;
    protected double produceTime;
    protected String input;
    protected String output;
    protected boolean isWorking;

    protected double x, y;
    protected double goodX, goodY;
    protected double barX, barY;
    protected Random random;

    protected ImageView imageView;
    protected FactoryAnimation factoryAnimation;
    protected Rectangle2D[] cells;
    protected ProgressBar progressBar;

    public Factory(int price, double produceTime, String input, String output, double x, double y, double goodX, double goodY, double barX, double barY) {
        this.price = price;
        this.upgradePrice = price;
        this.level = 1;
        this.finalLevel = 4;
        this.number = 1;
        this.produceTime = produceTime;
        this.input = input;
        this.output = output;
        this.isWorking = false;

        Game game = Game.getInstance();
        this.x = game.getScale() * (x - game.getOldX()) + game.getNewX();
        this.y = game.getScale() * (y - game.getOldY()) + game.getNewY();
        this.goodX = game.getScale() * goodX;
        this.goodY = game.getScale() * goodY;
        this.barX = barX;
        this.barY = barY;
        this.random = new Random();

        setAnimation();
    }

    protected void setAnimation() {

        Image image = new Image(new File("src/resource/" + getClass().getSimpleName() + "/" + level + ".png").toURI().toString());
        imageView = new ImageView(image);
        progressBar = new ProgressBar();
        progressBar.setVisible(false);

        factoryAnimation = new FactoryAnimation(this);
        factoryAnimation.setOnFinished(event -> {
            GoodList goodList = GoodList.getGood(output);
            try {
                for (int i = 0; i < number; i++) {
                    Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                    good.setPosition(goodX + random.nextDouble() * 20, goodY + random.nextDouble() * 20);
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
            isWorking = false;
            progressBar.setVisible(false);
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

        imageView.setLayoutX(x - cells[0].getWidth() / 2);
        imageView.setLayoutY(y - cells[0].getHeight() / 2);

        imageView.setOnMouseEntered(mouseEvent -> {
            if (!isWorking)
                imageView.setOpacity(0.8);
        });
        imageView.setOnMouseExited(mouseEvent -> {
            if (!isWorking)
                imageView.setOpacity(1);
        });

        imageView.setOnMouseClicked(mouseEvent -> {
            if (isWorking) {
                game.setResult("Factory Is Working!");
                return;
            }

            int i = 0;
            for (int j = 0; j < level; j++) {
                if (game.getWarehouse().getGood(input) != null) i++;
                else break;
            }
            if (i == 0) {
                game.setResult("Not Enough Goods!");
                return;
            }

            imageView.setOpacity(1);
            number = i;
            progressBar.setVisible(true);
            isWorking = true;
            factoryAnimation.play(produceTime * number / level);
        });

        progressBar.setLayoutX(x - cells[0].getWidth() / 2 + barX);
        progressBar.setLayoutY(y - cells[0].getHeight() / 2 + barY);

        progressBar.setRotate(270);

        progressBar.setPrefWidth(50);
        progressBar.setPrefHeight(10);

        progressBar.setProgress(0);

        if (num >= 15) num = 15;
        if (num < 0) num = 0;
        imageView.setViewport(cells[num]);
    }

    public int getLevel() {
        return level;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void play() {
        if (isWorking) factoryAnimation.play();
    }

    public void pause() {
        if (isWorking) factoryAnimation.pause();
    }

    public void update(double v) {
        progressBar.setProgress(v);
        imageView.setViewport(cells[((int) (v * produceTime * number / level * 15.1)) % 16]);
    }

    public int getPrice() {
        return price;
    }

    public void upgrade() {
        level++;
        upgradePrice *= 1.2;

        Image image = new Image(new File("src/resource/" + getClass().getSimpleName() + "/" + level + ".png").toURI().toString());
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

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

}
