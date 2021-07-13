package model;

import controller.ResultType;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.good.Good;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Truck {
    private int capacity;
    private int upgradePrice;
    private int level;
    private int finalLevel;
    private HashSet<Animal> animals;
    private HashSet<Good> goods;
    private int timeRemaining;
    private int timeOfWork;
    private int loadPrice;
    private boolean checkShow;
    private boolean inTruck;

    public Truck() {
        capacity = 20;
        animals = new HashSet<>();
        goods = new HashSet<>();
        timeOfWork = 10;
        timeRemaining = 0;
        upgradePrice = 400;
        loadPrice = 0;
        checkShow = false;
        inTruck = false;
        level = 1;
        finalLevel = 3;
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
        timeOfWork -= 2;
        level++;
        upgradePrice *= 1.2;
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public ResultType go() {
        if (timeRemaining > 0) return ResultType.EXISTED;
        if (goods.isEmpty() && animals.isEmpty()) return ResultType.NOT_ENOUGH;
        timeRemaining = timeOfWork;
        loadPrice = calculatePrice();
        checkShow = true;
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

    public boolean addAnimal(HashSet<Animal> wildSet) {
        int space = 0;
        for (Animal animal : wildSet) {
            space += animal.getSpace();
        }
        if (space > capacity) return false;
        capacity -= space;
        animals.addAll(wildSet);
        inTruck = true;
        return true;
    }

    public boolean addGood(HashSet<Good> goodSet) {
        int space = 0;
        for (Good good : goodSet) {
            space += good.getSpace();
        }
        if (space > capacity) return false;
        capacity -= space;
        goods.addAll(goodSet);
        inTruck = true;
        return true;
    }

    public HashSet<Animal> getAnimal(String name, int number) {
        HashSet<Animal> removed = new HashSet<>();
        int space = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(animal);
                space += animal.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        animals.removeAll(removed);
        capacity += space;
        if (isEmpty())
            inTruck = false;
        return removed;
    }

    public int getAnimalSpace(String name, int number) {
        int space = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += animal.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return -1;
        return space;
    }

    public HashSet<Good> getGood(String name, int number) {
        HashSet<Good> removed = new HashSet<>();
        int space = 0;
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(good);
                space += good.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        goods.removeAll(removed);
        capacity += space;
        if (isEmpty())
            inTruck = false;
        return removed;
    }

    public int getGoodSpace(String name, int number) {
        int space = 0;
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += good.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return -1;
        return space;
    }

    public boolean isEmpty() {
        return goods.isEmpty() && animals.isEmpty();
    }

    public boolean isCheckShow() {
        return checkShow;
    }

    public void update() {
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
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Truck L").append(level).append(":\n");
        if (isWorking()) {
            sb.append("\t").append("Remaining Time to Truck Back = ").append(timeRemaining).append("\n");
            sb.append("\t").append("Coins you'll get = ").append(loadPrice).append("\n");
        } else {
            if (checkShow) {
                sb.append("\t").append(loadPrice).append(" coins was added!").append("\n");
                checkShow = false;
                inTruck = false;
                loadPrice = 0;
            } else {
                sb.append("\t").append("Capacity = ").append(capacity).append("\n");

                HashMap<String, Integer> animalsMap = new HashMap<>();
                for (Animal animal : animals) {
                    String name = animal.getClass().getSimpleName();
                    if (animalsMap.containsKey(name)) {
                        animalsMap.replace(name, animalsMap.get(name) + 1);
                    } else {
                        animalsMap.put(name, 1);
                    }
                }
                if (!animalsMap.isEmpty()) {
                    sb.append("\n").append("\t").append("Animals:").append("\n");
                    for (Map.Entry<String, Integer> animal : animalsMap.entrySet()) {
                        sb.append("\t").append("\t").append(animal.getKey()).append(": ").append(animal.getValue()).append("\n");
                    }
                }

                HashMap<String, Integer> goodsMap = new HashMap<>();
                for (Good good : goods) {
                    String name = good.getClass().getSimpleName();
                    if (goodsMap.containsKey(name)) {
                        goodsMap.replace(name, goodsMap.get(name) + 1);
                    } else {
                        goodsMap.put(name, 1);
                    }
                }
                if (!goodsMap.isEmpty()) {
                    sb.append("\n").append("\t").append("Goods:").append("\n");
                    for (Map.Entry<String, Integer> good : goodsMap.entrySet()) {
                        sb.append("\t").append("\t").append(good.getKey()).append(": ").append(good.getValue()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}
