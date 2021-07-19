package model;

import controller.MissionManager;
import controller.UserManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.animal.collector.Collector;
import model.animal.collector.CollectorList;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.protective.Protective;
import model.animal.protective.ProtectiveList;
import model.animal.wild.Wild;
import model.animal.wild.WildList;
import model.factory.Factory;
import model.factory.FactoryList;
import model.good.Good;
import view.FinishController;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Game {

    private static Game gameInstance = null;

    public static Game getInstance() {
        return gameInstance;
    }

    public static void initiateGame(double stageWidth, double stageHeight, MediaPlayer homeMedia, MediaPlayer gameMedia, int level, String username, double width, double height, AnchorPane root, AnchorPane parent, double scale, double oldX, double oldY, double newX, double newY, Label result, Label coin, Label time, Label[] labels, Label[] labels1, ImageView[] imageViews, ImageView road) {
        gameInstance = new Game(stageWidth, stageHeight, homeMedia, gameMedia, level, username, width, height, root, parent, scale, oldX, oldY, newX, newY, result, coin, time, labels, labels1, imageViews, road);
    }

    public AnchorPane getRoot() {
        return root;
    }

    public double getTime() {
        return time.getTime();
    }

    public void setResult(String s) {
        result.setVisible(true);
        result.setText(s);
        fadeOut.playFromStart();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private double stageWidth, stageHeight;
    private MediaPlayer homeMedia, gameMedia;

    private AnchorPane root;
    private double width;
    private double height;

    private AnchorPane parent;
    private double scale, oldX, oldY, newX, newY;

    private Label result;
    FadeTransition fadeOut;

    private final int level;
    private final String username;

    private HashMap<String, Integer[]> tasks;
    private Label[] labels;
    private Label[] labels1;
    private ImageView[] imageViews;
    private ArrayList<String> taskNames;

    private Time time;
    private Label timeL;
    private HashMap<WildList, ArrayList<Double>> unloadedWilds;

    private int coin;
    private Label coinL;
    private ImageView road;

    private Warehouse warehouse;
    private Truck truck;
    private Well well;
    private Cage cage;

    private ArrayList<Grass> grasses;

    private HashSet<Factory> factories;
    private HashSet<Domestic> domesticAnimals;
    private HashSet<Wild> wildAnimals;
    private HashSet<Good> goods;
    private HashSet<Protective> protectiveAnimals;
    private HashSet<Collector> collectorAnimals;

    private Game(double stageWidth, double stageHeight, MediaPlayer homeMedia, MediaPlayer gameMedia, int level, String username, double width, double height, AnchorPane root, AnchorPane parent, double scale, double oldX, double oldY, double newX, double newY, Label result, Label coin, Label time, Label[] labels, Label[] labels1, ImageView[] imageViews, ImageView road) {
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
        this.homeMedia = homeMedia;
        this.gameMedia = gameMedia;

        this.labels = labels;
        this.labels1 = labels1;
        this.imageViews = imageViews;
        this.road = road;

        this.width = width;
        this.height = height;
        this.root = root;
        this.coinL = coin;
        this.timeL = time;

        this.parent = parent;
        this.scale = scale;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;

        this.result = result;
        fadeOut = new FadeTransition(Duration.seconds(1), result);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        this.level = level;
        this.username = username;
    }

    public ImageView getRoad() {
        return road;
    }

    public AnchorPane getParent() {
        return parent;
    }

    public double getScale() {
        return scale;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public double getNewX() {
        return newX;
    }

    public double getNewY() {
        return newY;
    }

    public void initiate() {
        this.time = new Time(timeL);

        tasks = new HashMap<>();
        taskNames = new ArrayList<>();
        HashMap<String, Integer> missionTasks = MissionManager.getInstance().getTasks(level);
        for (String s : missionTasks.keySet()) {
            tasks.put(s, new Integer[]{missionTasks.get(s), 0});
            taskNames.add(s);
        }
        for (int i = 0; i < taskNames.size(); i++) {
            imageViews[i].setImage(new Image(new File("src/resource/Task/" + taskNames.get(i) + ".png").toURI().toString()));
            labels[i].setText(String.valueOf(tasks.get(taskNames.get(i))[1]));
            labels1[i].setText(String.valueOf(tasks.get(taskNames.get(i))[0]));
            ((VBox) labels[i].getParent()).setVisible(true);
        }

        coin = MissionManager.getInstance().getNumberOfInitialCoins(level);
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);

        warehouse = new Warehouse();
        truck = new Truck();
        well = new Well();
        cage = new Cage();

        grasses = new ArrayList<>();

        factories = new HashSet<>();
        for (FactoryList name : MissionManager.getInstance().getFactories(level)) {
            try {
                factories.add((Factory) Class.forName(name.getPackageName()).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        domesticAnimals = new HashSet<>();
        for (Map.Entry<DomesticList, Integer> entry : MissionManager.getInstance().getDomesticAnimals(level).entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                try {
                    Domestic animal = (Domestic) Class.forName(entry.getKey().getPackageName()).newInstance();
                    domesticAnimals.add(animal);
                    animal.setAnimation();
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        protectiveAnimals = new HashSet<>();
        for (Map.Entry<ProtectiveList, Integer> entry : MissionManager.getInstance().getProtectiveAnimals(level).entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                try {
                    Protective animal = (Protective) Class.forName(entry.getKey().getPackageName()).newInstance();
                    protectiveAnimals.add(animal);
                    animal.setAnimation();
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        collectorAnimals = new HashSet<>();
        for (Map.Entry<CollectorList, Integer> entry : MissionManager.getInstance().getCollectorAnimals(level).entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                try {
                    Collector animal = (Collector) Class.forName(entry.getKey().getPackageName()).newInstance();
                    collectorAnimals.add(animal);
                    animal.setAnimation();
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        unloadedWilds = MissionManager.getInstance().getWildAnimalsTime(level);
        wildAnimals = new HashSet<>();

        goods = new HashSet<>();
    }

    public void loadWilds() {
        if (unloadedWilds.size() == 0) return;
        Wild animal = null;
        WildList wildList = null;
        for (Map.Entry<WildList, ArrayList<Double>> entry : unloadedWilds.entrySet()) {
            for (Double loadTime : entry.getValue()) {
                if (loadTime < time.getTime()) {
                    try {
                        wildList = entry.getKey();
                        animal = (Wild) Class.forName(entry.getKey().getPackageName()).newInstance();
                        wildAnimals.add(animal);
                        animal.setAnimation();
                        animal.play();
                        break;
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                    }
                }
            }
            if (animal != null) {
                entry.getValue().remove(0);
                break;
            }
        }
        if (wildList != null && unloadedWilds.get(wildList).isEmpty()) {
            unloadedWilds.remove(wildList);
        }
    }

    public void play() {
        time.play();
        for (Domestic domesticAnimal : domesticAnimals) {
            domesticAnimal.play();
        }
        for (Wild wildAnimal : wildAnimals) {
            wildAnimal.play();
        }
        for (Protective protectiveAnimal : protectiveAnimals) {
            protectiveAnimal.play();
        }
        for (Collector collectorAnimal : collectorAnimals) {
            collectorAnimal.play();
        }
        for (Good good : goods) {
            good.play();
        }
        well.play();
        truck.play();
        for (Factory factory : factories) {
            factory.play();
        }
    }

    public void pause() {
        time.pause();
        for (Domestic domesticAnimal : domesticAnimals) {
            domesticAnimal.pause();
        }
        for (Wild wildAnimal : wildAnimals) {
            wildAnimal.pause();
        }
        for (Protective protectiveAnimal : protectiveAnimals) {
            protectiveAnimal.pause();
        }
        for (Collector collectorAnimal : collectorAnimals) {
            collectorAnimal.pause();
        }
        for (Good good : goods) {
            good.pause();
        }
        well.pause();
        truck.pause();
        for (Factory factory : factories) {
            factory.pause();
        }
    }

    public void updateTask(String name, boolean isAdd) {
        for (String task : tasks.keySet()) {
            Integer[] values = tasks.get(task);
            if (task.equals(name) && !values[1].equals(values[0])) {
                if (task.equals("Coin")) {
                    values[1] = coin;
                    if (values[1] >= values[0]) {
                        values[1] = values[0];
                    }
                } else {
                    if (isAdd) {
                        values[1]++;
                    } else if (values[1] > 0) {
                        values[1]--;
                    }
                }
                int i = taskNames.indexOf(name);
                if (!values[1].equals(values[0])) {
                    labels[i].setText(String.valueOf(values[1]));
                    labels1[i].setText(String.valueOf(values[0]));
                } else {
                    labels[i].setText("OK");
                    labels1[i].setText("");
                }
            }
        }

        checkTaskFinished();
    }

    public void checkTaskFinished() {
        for (String task : tasks.keySet()) {
            Integer[] values = tasks.get(task);
            if (!values[1].equals(values[0])) {
                return;
            }
        }
        pause();
        int reward = 0;
        if (time.getTime() <= MissionManager.getInstance().getMaxPrizeTime(level)) {
            reward = MissionManager.getInstance().getPrize(level);
        }
        UserManager.getInstance().updateUser(username, level, coin + reward, reward != 0);

        MediaPlayer winMedia = new MediaPlayer(new Media(new File("src/resource/Sounds/Win.mp3").toURI().toString()));
        winMedia.setOnEndOfMedia(() -> winMedia.seek(Duration.ZERO));
        gameMedia.stop();
        gameMedia.seek(Duration.ZERO);
        winMedia.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/finish_dialog.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        ((FinishController) (loader.getController())).initiate(username, winMedia, homeMedia, gameMedia, stageWidth, stageHeight, coin, (int) getTime(), reward, UserManager.getInstance().getCollectedCoin(username));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(parent.getScene().getWindow());
        stage.show();
    }

    public int getLevel() {
        return level;
    }

    public void plant(double x, double y) {
        new Grass(x, y);
    }

    public HashMap<String, Integer[]> getTasks() {
        return tasks;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Truck getTruck() {
        return truck;
    }

    public Well getWell() {
        return well;
    }

    public Cage getCage() {
        return cage;
    }

    public int getCoin() {
        return coin;
    }

    public ArrayList<Grass> getGrasses() {
        return grasses;
    }


    public HashSet<Factory> getFactories() {
        return factories;
    }

    public int getDomesticSpace(String name) {
        for (Domestic domestic : domesticAnimals) {
            if (domestic.isAlive() && domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                return domestic.getSpace();
            }
        }
        return -1;
    }

    public int getDomesticNumber(String name) {
        int num = 0;
        for (Domestic domestic : domesticAnimals) {
            if (domestic.isAlive() && domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public int getWildNumber(String name) {
        int num = 0;
        for (Wild wild : warehouse.getWilds()) {
            if (wild.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public int getGoodNumber(String name) {
        int num = 0;
        for (Good good : warehouse.getGoods()) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                num++;
            }
        }
        return num;
    }

    public Domestic getDomestic(String name) {
        for (Domestic domestic : domesticAnimals) {
            if (domestic.isAlive() && domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                root.getChildren().remove(domestic.getImageView());
                root.getChildren().remove(domestic.getLifeBar());
                domesticAnimals.remove(domestic);
                return domestic;
            }
        }
        return null;
    }

    public void addDomestic(Domestic domestic) {
        domesticAnimals.add(domestic);
        root.getChildren().add(domestic.getImageView());
        root.getChildren().add(domestic.getLifeBar());
        updateTask(domestic.getClass().getSimpleName(), true);
    }

    public void decreaseCoin(int price) {
        coin -= price;
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
    }

    public HashSet<Domestic> getDomesticAnimals() {
        return domesticAnimals;
    }

    public HashSet<Wild> getWildAnimals() {
        return wildAnimals;
    }

    public HashSet<Protective> getProtectiveAnimals() {
        return protectiveAnimals;
    }

    public HashSet<Collector> getCollectorAnimals() {
        return collectorAnimals;
    }

    public HashSet<Good> getGoods() {
        return goods;
    }
}
