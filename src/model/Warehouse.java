package model;

import model.animal.wild.Wild;
import model.good.Good;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Warehouse {
    private int capacity;
    private int upgradePrice;
    private int level;
    private int finalLevel;
    private HashSet<Wild> wilds;
    private HashSet<Good> goods;

    public Warehouse() {
        capacity = 30;
        wilds = new HashSet<>();
        goods = new HashSet<>();
        upgradePrice = 450;
        level = 1;
        finalLevel = 3;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void upgrade() {
        capacity += 15;
        upgradePrice *= 1.2;
        level++;
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public boolean addWild(HashSet<Wild> wildSet) {
        int space = 0;
        for (Wild wild : wildSet) {
            space += wild.getSpace();
        }
        if (space > capacity) return false;
        capacity -= space;
        wilds.addAll(wildSet);
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
        return true;
    }

    public HashSet<Wild> getWild(String name, int number) {
        HashSet<Wild> removed = new HashSet<>();
        int space = 0;
        for (Wild wild : wilds) {
            if (wild.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(wild);
                space += wild.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        wilds.removeAll(removed);
        capacity += space;
        return removed;
    }

    public int getWildSpace(String name, int number) {
        int space = 0;
        for (Wild wild : wilds) {
            if (wild.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += wild.getSpace();
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
        return goods.isEmpty() && wilds.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Warehouse L").append(level).append(":\n");
        sb.append("\t").append("Capacity = ").append(capacity).append("\n");

        HashMap<String, Integer> wildsMap = new HashMap<>();
        for (Wild wild : wilds) {
            String name = wild.getClass().getSimpleName();
            if (wildsMap.containsKey(name)) {
                wildsMap.replace(name, wildsMap.get(name) + 1);
            } else {
                wildsMap.put(name, 1);
            }
        }
        if (!wildsMap.isEmpty()) {
            sb.append("\n").append("\t").append("Wilds:").append("\n");
            for (Map.Entry<String, Integer> wild : wildsMap.entrySet()) {
                sb.append("\t").append("\t").append(wild.getKey()).append(": ").append(wild.getValue()).append("\n");
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
        return sb.toString();
    }
}
