package model;

import model.animal.collector.CollectorList;
import model.animal.domestic.DomesticList;
import model.animal.protective.ProtectiveList;
import model.animal.wild.WildList;
import model.factory.FactoryList;

import java.util.HashMap;
import java.util.HashSet;

public class Mission {
    private int level;
    private int numberOfInitialCoins;
    private HashMap<DomesticList, Integer> domesticAnimals;
    private HashMap<ProtectiveList, Integer> protectiveAnimals;
    private HashMap<CollectorList, Integer> collectorAnimals;
    private HashSet<FactoryList> factories;
    private HashMap<WildList, Integer[]> wildAnimalsTime;
    private HashMap<String, Integer> tasks;
    private int maxPrizeTime;
    private int prize;

    public Mission(int level, int numberOfInitialCoins, HashMap<DomesticList, Integer> domesticAnimals, HashMap<ProtectiveList, Integer> protectiveAnimals, HashMap<CollectorList, Integer> collectorAnimals, HashSet<FactoryList> factories, HashMap<WildList, Integer[]> wildAnimalsTime, HashMap<String, Integer> tasks, int maxPrizeTime, int prize) {
        this.level = level;
        this.numberOfInitialCoins = numberOfInitialCoins;
        this.domesticAnimals = domesticAnimals;
        this.protectiveAnimals = protectiveAnimals;
        this.collectorAnimals = collectorAnimals;
        this.factories = factories;
        this.wildAnimalsTime = wildAnimalsTime;
        this.tasks = tasks;
        this.maxPrizeTime = maxPrizeTime;
        this.prize = prize;
    }

    public int getLevel() {
        return level;
    }

    public int getNumberOfInitialCoins() {
        return numberOfInitialCoins;
    }

    public HashMap<DomesticList, Integer> getDomesticAnimals() {
        return domesticAnimals;
    }

    public HashMap<ProtectiveList, Integer> getProtectiveAnimals() {
        return protectiveAnimals;
    }

    public HashMap<CollectorList, Integer> getCollectorAnimals() {
        return collectorAnimals;
    }

    public HashSet<FactoryList> getFactories() {
        return factories;
    }

    public HashMap<WildList, Integer[]> getWildAnimalsTime() {
        return wildAnimalsTime;
    }

    public HashMap<String, Integer> getTasks() {
        return tasks;
    }

    public int getMaxPrizeTime() {
        return maxPrizeTime;
    }

    public int getPrize() {
        return prize;
    }
}
