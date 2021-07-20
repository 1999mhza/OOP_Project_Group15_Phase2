package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.animal.wild.Wild;
import model.good.Good;
import view.TruckController;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Warehouse {
    private int capacity;
    private int fulCapacity;
    private int upgradePrice;
    private int level;
    private int finalLevel;
    private HashSet<Wild> wilds;
    private HashSet<Good> goods;

    private int row, column;
    private double length;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ImageView[][] imageViews;

    public Warehouse() {
        capacity = 30;
        fulCapacity = 30;
        wilds = new HashSet<>();
        goods = new HashSet<>();
        upgradePrice = 450;
        level = 1;
        finalLevel = 4;

        row = 6;
        column = 10;
        length = 12;
        Image image = new Image(new File("src/resource/Warehouse/1.png").toURI().toString());
        imageView = new ImageView(image);
        progressBar = new ProgressBar();

        Game game = Game.getInstance();
        game.getParent().getChildren().add(imageView);
        game.getParent().getChildren().add(progressBar);

        double scale = game.getScale();

        imageView.setFitWidth(image.getWidth() * scale);
        imageView.setFitHeight(image.getHeight() * scale);

        imageView.setLayoutX(scale * (500 - game.getOldX()) + game.getNewX() - imageView.getFitWidth() / 2);
        imageView.setLayoutY(scale * (530 - game.getOldY()) + game.getNewY() - imageView.getFitHeight() / 2);

        imageView.setOnMouseEntered(mouseEvent -> {
            if (!game.getTruck().isWorking())
                imageView.setOpacity(0.8);
        });
        imageView.setOnMouseExited(mouseEvent -> {
            if (!game.getTruck().isWorking())
                imageView.setOpacity(1);
        });

        imageView.setOnMouseClicked(mouseEvent -> {
            if (!game.getTruck().isWorking()) {
                imageView.setOpacity(1);
                Game.getInstance().pause();

                double height = Screen.getPrimary().getBounds().getHeight();
                double width = Screen.getPrimary().getBounds().getWidth();

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/truck.fxml"));
                try {
                    stage.setScene(new Scene(loader.load(), width, height));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setFullScreen(true);
                stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initOwner(((Node) (mouseEvent.getSource())).getScene().getWindow());
                TruckController truckController = (TruckController) (loader.getController());
                truckController.initiate(width, height);
                stage.show();
            }
        });

        progressBar.setPrefWidth(150);
        progressBar.setPrefHeight(20);

        progressBar.setLayoutX(imageView.getLayoutX() + imageView.getFitWidth() / 2 - progressBar.getPrefWidth() / 2);
        progressBar.setLayoutY(imageView.getLayoutY() + imageView.getFitHeight() - 30);

        double p = 1 - 1.0 * capacity / fulCapacity;
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        progressBar.setProgress(p);

        length *= 0.9 * scale;

        imageViews = new ImageView[row][column];
        for (int i = 0; i < row; i++) {
            imageViews[i] = new ImageView[column];
            for (int j = 0; j < column; j++) {
                imageViews[i][j] = new ImageView();
                imageViews[i][j].setFitWidth(length);
                imageViews[i][j].setFitHeight(length);
            }
        }

        double biasX = (imageView.getFitWidth() - column * length) / 2;
        double biasY = 80;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                game.getParent().getChildren().add(imageViews[i][j]);
                imageViews[i][j].setLayoutX(imageView.getLayoutX() + biasX + j * length);
                imageViews[i][j].setLayoutY(imageView.getLayoutY() + biasY + (row - 1 - i) * length);
            }
        }
        format();
    }

    public HashSet<Wild> getWilds() {
        return wilds;
    }

    public HashSet<Good> getGoods() {
        return goods;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void upgrade() {
        capacity += 10;
        fulCapacity += 10;
        upgradePrice *= 1.2;
        level++;

        Image image = new Image(new File("src/resource/Warehouse/" + level + ".png").toURI().toString());
        imageView.setImage(image);
        double p = 1 - 1.0 * capacity / fulCapacity;
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        progressBar.setProgress(p);
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    private void format() {
        double p = 1 - 1.0 * capacity / fulCapacity;
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        progressBar.setProgress(p);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                imageViews[i][j].setImage(null);
            }
        }

        int num = 0;
        for (Wild wild : wilds) {
            Image image = new Image(new File("src/resource/Product/" + wild.getClass().getSimpleName() + ".png").toURI().toString());
            for (int i = 0; i < wild.getSpace(); i++) {
                imageViews[num / column][num % column].setImage(image);
                num++;
            }
        }
        for (Good good : goods) {
            Image image = new Image(new File("src/resource/Product/" + good.getClass().getSimpleName() + ".png").toURI().toString());
            for (int i = 0; i < good.getSpace(); i++) {
                imageViews[num / column][num % column].setImage(image);
                num++;
            }
        }
    }

    public boolean addWild(Wild wild) {
        if (wild.getSpace() > capacity) return false;
        capacity -= wild.getSpace();
        wilds.add(wild);
        format();
        return true;
    }

    public boolean addGood(Good good) {
        if (good.getSpace() > capacity) return false;
        capacity -= good.getSpace();
        goods.add(good);
        format();
        return true;
    }

    public Wild getWild(String name) {
        for (Wild wild : wilds) {
            if (wild.getClass().getSimpleName().equalsIgnoreCase(name)) {
                wilds.remove(wild);
                capacity += wild.getSpace();
                format();
                return wild;
            }
        }
        return null;
    }

    public int getWildSpace(String name) {
        for (Wild wild : wilds) {
            if (wild.getClass().getSimpleName().equalsIgnoreCase(name)) {
                return wild.getSpace();
            }
        }
        return -1;
    }

    public Good getGood(String name) {
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                goods.remove(good);
                capacity += good.getSpace();
                format();
                return good;
            }
        }
        return null;
    }

    public int getGoodSpace(String name) {
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                return good.getSpace();
            }
        }
        return -1;
    }

    public int getLevel() {
        return level;
    }
}
