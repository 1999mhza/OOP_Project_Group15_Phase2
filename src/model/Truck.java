package model;

import controller.ResultType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;
import view.HomeController;
import view.TruckController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Truck {
    private int capacity;
    private int fulCapacity;
    private int upgradePrice;
    private int level;
    private int finalLevel;
    private HashSet<Animal> animals;
    private HashSet<Good> goods;
    private double timeRemaining;
    private double timeOfWork;
    private int loadPrice;
    private boolean inTruck;

    private ImageView imageView;

    public Truck() {
        capacity = 20;
        fulCapacity = 20;
        animals = new HashSet<>();
        goods = new HashSet<>();
        timeOfWork = 10;
        timeRemaining = 0;
        upgradePrice = 400;
        loadPrice = 0;
        inTruck = false;
        level = 1;
        finalLevel = 4;

        Image image = new Image(new File("src/resource/Truck/1_front.png").toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);

        Game game = Game.getInstance();
        game.getParent().getChildren().add(imageView);

        double scale = game.getScale();

        imageView.setFitWidth(image.getWidth() * scale);
        imageView.setFitHeight(image.getHeight() * scale);

        imageView.setLayoutX(scale * (250 - game.getOldX()) + game.getNewX() - imageView.getFitWidth() / 2);
        imageView.setLayoutY(scale * (530 - game.getOldY()) + game.getNewY() - imageView.getFitHeight() / 2);

        imageView.setOnMouseEntered(mouseEvent -> imageView.setOpacity(0.8));
        imageView.setOnMouseExited(mouseEvent -> imageView.setOpacity(1));

        imageView.setOnMouseClicked(mouseEvent -> {
            imageView.setOpacity(1);
            Game.getInstance().pause();

            double height = Screen.getPrimary().getBounds().getHeight();
            double width = Screen.getPrimary().getBounds().getWidth();

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/truck.fxml"));
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
        });
    }

    public boolean isInTruck() {
        return inTruck;
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
        timeOfWork -= 2;
        level++;
        upgradePrice *= 1.2;

        Image image = new Image(new File("src/resource/Truck/" + level + "_front.png").toURI().toString());
        imageView.setImage(image);
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public ResultType go() {
        if (timeRemaining > 0) return ResultType.EXISTED;
        if (goods.isEmpty() && animals.isEmpty()) return ResultType.NOT_ENOUGH;
        timeRemaining = timeOfWork;
        loadPrice = calculatePrice();
        inTruck = true;
        return ResultType.SUCCESS;
    }

    private int calculatePrice() {
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

    public boolean isWorking() {
        return timeRemaining > 0;
    }

    public boolean addAnimal(Animal animal) {
        if (animal.getSpace() > capacity) return false;
        capacity -= animal.getSpace();
        animals.add(animal);
        inTruck = true;
        return true;
    }

    public boolean addGood(Good good) {
        if (good.getSpace() > capacity) return false;
        capacity -= good.getSpace();
        goods.add(good);
        inTruck = true;
        return true;
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

    public boolean isEmpty() {
        return goods.isEmpty() && animals.isEmpty();
    }

   /* public void update() {
        if (timeRemaining > 0) {
            timeRemaining--;
            if (timeRemaining == timeOfWork / 2) {
                for (Good good : new HashSet<>(goods)) {
                    getGood(good.getClass().getSimpleName(), 1);
                }
                for (Animal animal : new HashSet<>(animals)) {
                    getAnimal(animal.getClass().getSimpleName(), 1);
                }
            }
            if (timeRemaining == 0) {
                Game.getInstance().decreaseCoin(-loadPrice);
            }
        }
    }*/

    public int getLevel() {
        return level;
    }
}
