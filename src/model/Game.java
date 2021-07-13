package model;

import controller.UserManager;
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

import java.util.*;

public class Game {

    private static Game gameInstance = null;

    public static Game getInstance() {
        return gameInstance;
    }

    public static void initiateGame(Mission mission, User user) {
        gameInstance = new Game(mission, user);
    }

    public static final int SIZE = 6;

    private final Mission mission;
    private final User user;

    private final HashMap<String, Integer[]> tasks;
    private boolean isFinished;

    private int time;
    private int coin;

    private final Warehouse warehouse;
    private final Truck truck;
    private final Well well;

    private final int[][] grass;

    private final HashSet<Factory> factories;
    private final HashSet<Domestic> domesticAnimals;
    private final HashSet<Wild> wildAnimals;
    private final HashSet<Good> goods;
    private final HashSet<Protective> protectiveAnimals;
    private final HashSet<Collector> collectorAnimals;

    private Game(Mission mission, User user) {
        this.mission = mission;
        this.user = user;

        tasks = new HashMap<>();
        HashMap<String, Integer> missionTasks = mission.getTasks();
        for (String s : missionTasks.keySet()) {
            tasks.put(s, new Integer[]{missionTasks.get(s), 0});
        }
        isFinished = false;

        time = 0;
        coin = mission.getNumberOfInitialCoins();
        updateTask("Coin", true);

        warehouse = new Warehouse();
        truck = new Truck();
        well = new Well();

        grass = new int[SIZE][SIZE];
        for (int[] ints : grass)
            Arrays.fill(ints, 0);

        factories = new HashSet<>();
        for (FactoryList name : mission.getFactories()) {
            try {
                factories.add((Factory) Class.forName(name.getPackageName()).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        domesticAnimals = new HashSet<>();
        for (DomesticList name : mission.getDomesticAnimals().keySet()) {
            for (int i = 0; i < mission.getDomesticAnimals().get(name); i++) {
                try {
                    Domestic animal = (Domestic) Class.forName(name.getPackageName()).newInstance();
                    domesticAnimals.add(animal);
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        protectiveAnimals = new HashSet<>();
        for (ProtectiveList name : mission.getProtectiveAnimals().keySet()) {
            for (int i = 0; i < mission.getProtectiveAnimals().get(name); i++) {
                try {
                    Protective animal = (Protective) Class.forName(name.getPackageName()).newInstance();
                    protectiveAnimals.add(animal);
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        collectorAnimals = new HashSet<>();
        for (CollectorList name : mission.getCollectorAnimals().keySet()) {
            for (int i = 0; i < mission.getCollectorAnimals().get(name); i++) {
                try {
                    Collector animal = (Collector) Class.forName(name.getPackageName()).newInstance();
                    collectorAnimals.add(animal);
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
        for (WildList name : mission.getWildAnimalsTime().keySet()) {
            for (int i = 0; i < mission.getWildAnimalsTime().get(name).length; i++) {
                if (mission.getWildAnimalsTime().get(name)[i] == time) {
                    try {
                        wildAnimals.add((Wild) Class.forName(name.getPackageName()).newInstance());
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                    }
                }
            }
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
        if (time <= mission.getMaxPrizeTime()) {
            coin += mission.getPrize();
            rewarded = true;
        }
        UserManager.getInstance().updateUser(user, mission.getLevel(), coin, rewarded);
        return true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Mission getMission() {
        return mission;
    }

    public void plant(int i, int j) {
        grass[i][j]++;
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
        return grass;
    }

    public void addDomesticAnimal(Domestic domestic) {
        coin -= domestic.getPrice();
        updateTask("Coin", true);
        domesticAnimals.add(domestic);
        updateTask(domestic.getClass().getSimpleName(), true);
    }

    public void addProtectiveAnimal(Protective protective) {
        coin -= protective.getPrice();
        updateTask("Coin", true);
        protectiveAnimals.add(protective);
        updateTask(protective.getClass().getSimpleName(), true);
        protective.work();
    }

    public void addCollectorAnimal(Collector collector) {
        coin -= collector.getPrice();
        updateTask("Coin", true);
        collectorAnimals.add(collector);
        updateTask(collector.getClass().getSimpleName(), true);
        collector.work();
    }

    public void addFactory(Factory factory) {
        coin -= factory.getPrice();
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
