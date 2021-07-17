package model;

import controller.MissionManager;
import controller.UserManager;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

import java.io.File;
import java.util.*;

public class Game {

    private static Game gameInstance = null;

    public static Game getInstance() {
        return gameInstance;
    }

    public static void initiateGame(int level, String username, double width, double height, AnchorPane root, AnchorPane parent, double scale, double oldX, double oldY, double newX, double newY, Label result, Label coin, Label time, Label[] labels, Label[] labels1, ImageView[] imageViews) {
        gameInstance = new Game(level, username, width, height, root, parent, scale, oldX, oldY, newX, newY, result, coin, time, labels, labels1, imageViews);
    }

    public AnchorPane getRoot() {
        return root;
    }

    public double getTimer() {
        return timer.getTime();
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

    public static final int SIZE = 6;

    private AnchorPane root;
    private double width;
    private double height;

    private AnchorPane parent;
    private double scale, oldX, oldY, newX, newY;

    private Label result;
    FadeTransition fadeOut;

    private final int level;
    private final String username;

    private  HashMap<String, Integer[]> tasks;
    private boolean isFinished;
    private Label[] labels;
    private Label[] labels1;
    private ImageView[] imageViews;
    private ArrayList<String> taskNames;

    private Time timer;
    private int time;
    private int coin;
    private Label coinL;

    private  Warehouse warehouse;
    private  Truck truck;
    private  Well well;

    private ArrayList<Grass> grasses;

    private  HashSet<Factory> factories;
    private  HashSet<Domestic> domesticAnimals;
    private  HashSet<Wild> wildAnimals;
    private  HashSet<Good> goods;
    private  HashSet<Protective> protectiveAnimals;
    private  HashSet<Collector> collectorAnimals;

    private Game(int level, String username, double width, double height, AnchorPane root, AnchorPane parent, double scale, double oldX, double oldY, double newX, double newY, Label result, Label coin, Label time, Label[] labels, Label[] labels1, ImageView[] imageViews) {

        this.labels = labels;
        this.labels1 = labels1;
        this.imageViews = imageViews;
        this.timer = new Time(time);

        this.width = width;
        this.height = height;
        this.root = root;
        this.coinL = coin;

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
        isFinished = false;

        time = 0;
        coin = MissionManager.getInstance().getNumberOfInitialCoins(level);
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);

        warehouse = new Warehouse();
        truck = new Truck();
        well = new Well();

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

        wildAnimals = new HashSet<>();
        loadWilds();

        goods = new HashSet<>();
    }

    public void loadWilds() {
        for (Map.Entry<WildList, Integer[]> entry : MissionManager.getInstance().getWildAnimalsTime(level).entrySet()) {
            for (int i = 0; i < entry.getValue().length; i++) {
                if (entry.getValue()[i] == time) {
                    try {
                        Wild animal = (Wild) Class.forName(entry.getKey().getPackageName()).newInstance();
                        wildAnimals.add(animal);
                        animal.setAnimation();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                    }
                }
            }
        }
    }

    public void play() {
        timer.play();
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
        well.play();
    }

    public void pause() {
        timer.pause();
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
        well.pause();
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
    }

    public boolean checkTaskFinished() {
        for (String task : tasks.keySet()) {
            Integer[] values = tasks.get(task);
            if (!values[1].equals(values[0])) {
                return false;
            }
        }
        isFinished = true;
        boolean rewarded = false;
        if (time <= MissionManager.getInstance().getMaxPrizeTime(level)) {
            coin += MissionManager.getInstance().getPrize(level);
            rewarded = true;
        }
        UserManager.getInstance().updateUser(username, level, coin, rewarded);
        return true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getLevel() {
        return level;
    }

    public void plant(double i, double j) {
        //grass[i][j]++;
        grasses.add(new Grass(i, j));
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

    public int getCoin() {
        return coin;
    }

    public int getTime() {
        return time;
    }

    public int[][] getGrass() {
        return new int[1][1];
    }

    public ArrayList<Grass> getGrasses() {
        return grasses;
    }

    public void addDomesticAnimal(Domestic domestic) {
        coin -= domestic.getPrice();
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
        domesticAnimals.add(domestic);
        domestic.setAnimation();
        updateTask(domestic.getClass().getSimpleName(), true);
    }

    public void addProtectiveAnimal(Protective protective) {
        coin -= protective.getPrice();
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
        protectiveAnimals.add(protective);
        protective.setAnimation();
        updateTask(protective.getClass().getSimpleName(), true);
        protective.work();
    }

    public void addCollectorAnimal(Collector collector) {
        coin -= collector.getPrice();
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
        collectorAnimals.add(collector);
        collector.setAnimation();
        updateTask(collector.getClass().getSimpleName(), true);
        collector.work();
    }

    public void addFactory(Factory factory) {
        coin -= factory.getPrice();
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
        factories.add(factory);
    }

    public HashSet<Factory> getFactories() {
        return factories;
    }

    public int getDomesticSpace(String name, int number) {
        int space = 0;
        for (Domestic domestic : domesticAnimals) {
            if (domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += domestic.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return -1;
        return space;
    }

    public HashSet<Domestic> getDomestic(String name, int number) {

        Random rand = new Random();
        ArrayList<Domestic> list1 = new ArrayList<>(domesticAnimals);
        ArrayList<Domestic> list2 = new ArrayList<>();
        for (int k = domesticAnimals.size(); k > 0; k--) {
            list2.add(list1.remove(rand.nextInt(k)));
        }

        HashSet<Domestic> removed = new HashSet<>();
        for (Domestic domestic : list2) {
            if (domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(domestic);
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        domesticAnimals.removeAll(removed);
        return removed;
    }

    public void addDomestic(HashSet<Domestic> domestics) {
        for (Domestic domestic : domestics) {
            domesticAnimals.add(domestic);
            updateTask(domestic.getClass().getSimpleName(), true);
        }
    }

    public void decreaseCoin(int price) {
        coin -= price;
        coinL.setText(String.valueOf(coin));
        updateTask("Coin", true);
    }

    public void increaseTime() {
        time++;
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
