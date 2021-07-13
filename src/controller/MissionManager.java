package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.animal.collector.CollectorList;
import model.animal.domestic.DomesticList;
import model.animal.protective.ProtectiveList;
import model.animal.wild.WildList;
import model.factory.FactoryList;
import model.good.GoodList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MissionManager {

    private final ArrayList<Mission> missions;

    private MissionManager(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    private static MissionManager missionManagerInstance;

    public static MissionManager getInstance() {
        if (missionManagerInstance == null) {
            load();
        }
        return missionManagerInstance;
    }

    private void processMission(int level, Mission mission) {
        if (level <= missions.size() && level > 0) {
            missions.set(level - 1, mission);
            Logger.log("admin", "The missions of the level " + level + " was changed!");
            System.out.println("Successful change!");
            UserManager.getInstance().resetUsers();
            Logger.log("admin", "The users info was reset!");
            System.out.println("All users have been reset!");
            save();
        } else if (level == missions.size() + 1) {
            missions.add(level - 1, mission);
            Logger.log("admin", "A new level (" + level + ") was added to levels!");
            System.out.println("Successful add!");
            UserManager.getInstance().resetUsers();
            Logger.log("admin", "The users info was reset!");
            System.out.println("All users have been reset!");
            save();
        } else {
            Logger.log("admin", "The level admin entered was invalid!");
            System.out.println("The level is invalid!");
        }
    }

    public Mission getMission(int level) {
        return missions.get(level - 1);
    }

    public int getNumberOfLevels() {
        return missions.size();
    }

    private static void load() {
        String missionsText = FileManager.read("missions.json");
        if (!missionsText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ArrayList<Mission> missions = gson.fromJson(missionsText, new TypeToken<ArrayList<Mission>>() {
            }.getType());
            missionManagerInstance = new MissionManager(missions);
        } else {
            missionManagerInstance = new MissionManager(new ArrayList<>());
        }
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String missionsText = gson.toJson(missions);
        FileManager.write("missions.json", missionsText);
    }

    public static void main(String[] args) {

        int level;
        int numberOfInitialCoins;
        HashMap<String, Integer> domesticAnimals = new HashMap<>();
        HashMap<String, Integer> protectiveAnimals = new HashMap<>();
        HashMap<String, Integer> collectorAnimals = new HashMap<>();
        HashSet<String> factoriesSet = new HashSet<>();
        HashMap<String, Integer[]> wildAnimalsTime = new HashMap<>();
        HashMap<String, Integer> tasksMap = new HashMap<>();
        int maxPrizeTime;
        int prize;

        // ---------- Enter the Specifications -----------
        level = 3;

        numberOfInitialCoins = 200;

        domesticAnimals.put("Chicken", 2);
        //domesticAnimals.put("Buffalo", 2);

        //protectiveAnimals.put("Hound", 2);

        //collectorAnimals.put("Cat", 5);

        factoriesSet.add("Mill");
        //factoriesSet.add("MilkPacker");

        wildAnimalsTime.put("Lion", new Integer[]{2, 6, 12, 18});
        //wildAnimalsTime.put("Bear", new Integer[]{20, 21});

        //tasksMap.put("Chicken", 1);
        //tasksMap.put("Flour", 10);
        tasksMap.put("Bread", 10);
        //tasksMap.put("Egg", 10);
        //tasksMap.put("Buffalo", 3);
        //tasksMap.put("Coin", 500);
        //tasksMap.put("Lion", 2);

        maxPrizeTime = 30;
        prize = 50;
        // -----------------------------------------------

        HashMap<DomesticList, Integer> domestics = new HashMap<>();
        HashMap<ProtectiveList, Integer> protectives = new HashMap<>();
        HashMap<CollectorList, Integer> collectors = new HashMap<>();
        HashSet<FactoryList> factories = new HashSet<>();
        HashMap<WildList, Integer[]> wildsTime = new HashMap<>();
        HashMap<String, Integer> tasks = new HashMap<>();

        for (String animal : domesticAnimals.keySet()) {
            if (DomesticList.getDomestic(animal) == null || domesticAnimals.get(animal) == 0) {
                System.out.println("Domestic animals have some problems!");
                return;
            }
            domestics.put(DomesticList.getDomestic(animal), domesticAnimals.get(animal));
        }

        for (String animal : protectiveAnimals.keySet()) {
            if (ProtectiveList.getProtective(animal) == null || protectiveAnimals.get(animal) == 0) {
                System.out.println("Protective animals have some problems!");
                return;
            }
            protectives.put(ProtectiveList.getProtective(animal), protectiveAnimals.get(animal));
        }

        for (String animal : collectorAnimals.keySet()) {
            if (CollectorList.getCollector(animal) == null || collectorAnimals.get(animal) == 0) {
                System.out.println("Collector animals have some problems!");
                return;
            }
            collectors.put(CollectorList.getCollector(animal), collectorAnimals.get(animal));
        }

        for (String animal : wildAnimalsTime.keySet()) {
            if (WildList.getWild(animal) == null || Arrays.asList(wildAnimalsTime.get(animal)).contains(0)) {
                System.out.println("Wild animals have some problems!");
                return;
            }
            wildsTime.put(WildList.getWild(animal), wildAnimalsTime.get(animal));
        }

        for (String factory : factoriesSet) {
            if (FactoryList.getFactory(factory) == null || factories.contains(FactoryList.getFactory(factory))) {
                System.out.println("Factories have some problems!");
                return;
            }
            factories.add(FactoryList.getFactory(factory));
        }

        if (tasksMap.isEmpty()) {
            System.out.println("Tasks have some problems!");
            return;
        }
        for (String task : tasksMap.keySet()) {
            if (tasksMap.get(task) == 0) {
                System.out.println("Tasks have some problems!");
                return;
            }
            if (GoodList.getGood(task) != null) {
                tasks.put(GoodList.getGood(task).getClassName(), tasksMap.get(task));
            } else if (DomesticList.getDomestic(task) != null) {
                tasks.put(DomesticList.getDomestic(task).getClassName(), tasksMap.get(task));
            } else if (ProtectiveList.getProtective(task) != null) {
                tasks.put(ProtectiveList.getProtective(task).getClassName(), tasksMap.get(task));
            } else if (CollectorList.getCollector(task) != null) {
                tasks.put(CollectorList.getCollector(task).getClassName(), tasksMap.get(task));
            } else if (WildList.getWild(task) != null) {
                tasks.put(WildList.getWild(task).getClassName(), tasksMap.get(task));
            } else if (task.equalsIgnoreCase("Coin")) {
                tasks.put("Coin", tasksMap.get(task));
            } else {
                System.out.println("Tasks have some problems!");
                return;
            }
        }

        Mission mission = new Mission(level, numberOfInitialCoins, domestics, protectives, collectors, factories, wildsTime, tasksMap, maxPrizeTime, prize);
        MissionManager.getInstance().processMission(level, mission);
    }
}