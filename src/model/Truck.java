package model;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;
import view.TruckController;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Truck {
    private int capacity;
    private int fulCapacity;
    private int upgradePrice;
    private int level;
    private int finalLevel;
    private HashSet<Animal> animals;
    private HashSet<Good> goods;
    private double timeOfWork;
    private boolean isWorking;

    private ImageView imageView;
    private ImageView road;
    private Image right, left;
    private ImageView mini;
    private double miniStartX, miniFinishX;
    private HBox coin;
    private Rectangle2D[] miniCells;
    private TruckAnimation truckAnimation;
    private double preV;
    private boolean isBack;

    public Truck() {
        capacity = 20;
        fulCapacity = 20;
        animals = new HashSet<>();
        goods = new HashSet<>();
        timeOfWork = 10;
        upgradePrice = 400;
        isWorking = false;
        level = 1;
        finalLevel = 4;

        Image image = new Image(new File("src/resource/Truck/1_front.png").toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);

        Game game = Game.getInstance();
        game.getParent().getChildren().add(imageView);

        road = game.getRoad();
        right = new Image(new File("src/resource/Truck/1_right.png").toURI().toString());
        left = new Image(new File("src/resource/Truck/1_left.png").toURI().toString());
        miniCells = new Rectangle2D[2];
        miniCells[0] = new Rectangle2D(0, 0, right.getWidth() / 2, right.getHeight());
        miniCells[1] = new Rectangle2D(right.getWidth() / 2, 0, right.getWidth() / 2, right.getHeight());

        mini = new ImageView();
        mini.setFitWidth(right.getWidth() / 2);
        mini.setFitHeight(right.getHeight());
        miniStartX = road.getLayoutX() + 32;
        miniFinishX = road.getLayoutX() + road.getFitWidth() - mini.getFitWidth() - 5;
        mini.setLayoutX(miniStartX);
        mini.setLayoutY(road.getLayoutY() + road.getFitHeight() - mini.getFitHeight() - 3);
        mini.setVisible(false);

        coin = new HBox();
        coin.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10");
        coin.setPrefHeight(24);
        coin.setPrefWidth(60);
        Label price = new Label();
        price.setAlignment(Pos.CENTER);
        price.setStyle("-fx-font-size: 10; -fx-font-weight: bold");
        price.setPrefHeight(24);
        price.setPrefWidth(36);
        ImageView coinImage = new ImageView(new Image(new File("src/resource/Task/Coin.png").toURI().toString()));
        coinImage.setFitHeight(24);
        coinImage.setFitWidth(24);
        coin.getChildren().add(price);
        coin.getChildren().add(coinImage);
        coin.setAlignment(Pos.CENTER);
        setXCoin();
        coin.setLayoutY(mini.getLayoutY() - coin.getPrefHeight());
        coin.setVisible(false);

        game.getParent().getChildren().add(mini);
        game.getParent().getChildren().add(coin);

        truckAnimation = new TruckAnimation(this);
        truckAnimation.setOnFinished(event -> {
            Game.getInstance().decreaseCoin(-calculatePrice());
            clear();
            mini.setVisible(false);
            coin.setVisible(false);
            imageView.setVisible(true);
            preV = 0;
            isBack = false;
            isWorking = false;
        });

        preV = 0;
        isBack = false;

        double scale = game.getScale();

        imageView.setFitWidth(image.getWidth() * scale);
        imageView.setFitHeight(image.getHeight() * scale);

        imageView.setLayoutX(scale * (250 - game.getOldX()) + game.getNewX() - imageView.getFitWidth() / 2);
        imageView.setLayoutY(scale * (530 - game.getOldY()) + game.getNewY() - imageView.getFitHeight() / 2);

        imageView.setOnMouseEntered(mouseEvent -> {
            if (!isWorking)
                imageView.setOpacity(0.8);
        });
        imageView.setOnMouseExited(mouseEvent -> {
            if (!isWorking)
                imageView.setOpacity(1);
        });

        imageView.setOnMouseClicked(mouseEvent -> {
            if (!isWorking) {
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
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void play() {
        if (isWorking && truckAnimation.getCurrentRate() == 0.0) truckAnimation.play();
    }

    public void pause() {
        if (isWorking) truckAnimation.pause();
    }

    public void upgrade() {
        capacity += 10;
        fulCapacity += 10;
        timeOfWork -= 1;
        level++;
        upgradePrice *= 1.2;

        right = new Image(new File("src/resource/Truck/" + level + "_right.png").toURI().toString());
        left = new Image(new File("src/resource/Truck/" + level + "_left.png").toURI().toString());
        imageView.setImage(new Image(new File("src/resource/Truck/" + level + "_front.png").toURI().toString()));
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public boolean isEmpty() {
        return goods.isEmpty() && animals.isEmpty();
    }

    public void go() {
        isWorking = true;
        isBack = false;
        imageView.setVisible(false);
        mini.setVisible(true);
        mini.setImage(right);
        mini.setViewport(miniCells[0]);
        coin.setVisible(true);
        for (Node child : coin.getChildren()) {
            if (child instanceof Label) {
                ((Label) child).setText(String.valueOf(calculatePrice()));
            }
        }
        truckAnimation.play(timeOfWork);
    }

    public int calculatePrice() {
        int loadPrice = 0;
        for (Good good : goods) {
            loadPrice += good.getPrice();
        }
        for (Animal animal : animals) {
            if (animal instanceof Domestic)
                loadPrice += animal.getPrice() / 2;
            else
                loadPrice += animal.getPrice();
        }
        return loadPrice;
    }

    public int getLevel() {
        return level;
    }

    public double getFullCapacity() {
        return fulCapacity;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void addAnimal(Animal animal) {
        if (animal.getSpace() > capacity) return;
        capacity -= animal.getSpace();
        animals.add(animal);
    }

    public void addGood(Good good) {
        if (good.getSpace() > capacity) return;
        capacity -= good.getSpace();
        goods.add(good);
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public HashSet<Good> getGoods() {
        return goods;
    }

    public void clear() {
        animals.clear();
        goods.clear();
        capacity = fulCapacity;
    }

    public Animal getAnimal(String name) {
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                capacity += animal.getSpace();
                animals.remove(animal);
                return animal;
            }
        }
        return null;
    }

    public int getAnimalSpace(String name) {
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                return animal.getSpace();
            }
        }
        return -1;
    }

    public Good getGood(String name) {
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                capacity += good.getSpace();
                goods.remove(good);
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

    public int getDomesticNumber(String name) {
        int num = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public int getWildNumber(String name) {
        int num = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public int getGoodNumber(String name) {
        int num = 0;
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public void update(double v) {
        if (v < preV) isBack = true;
        preV = v;
        if (!isBack) {
            mini.setImage(right);
            mini.setViewport(miniCells[((int) (v * timeOfWork * 4)) % 2]);
            mini.setLayoutX(v * (miniFinishX - miniStartX) + miniStartX);
        } else {
            mini.setImage(left);
            mini.setViewport(miniCells[((int) (v * timeOfWork * 4)) % 2]);
            mini.setLayoutX((1 - v) * (miniFinishX - miniStartX) + miniStartX);
        }
        setXCoin();
    }

    private void setXCoin() {
        coin.setLayoutX(mini.getLayoutX() + mini.getFitWidth() / 2 - coin.getPrefWidth() / 2);
    }
}
