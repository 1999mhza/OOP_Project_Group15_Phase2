package model;

import controller.ResultType;

public class Well {
    private int level;
    private int finalLevel;
    private int bucketCapacity;
    private int water;
    private int fillTime;
    private int fillRemainingTime;
    private int fillPrice;
    private int upgradePrice;

    public Well() {
        finalLevel = 3;
        level = 1;
        bucketCapacity = 5;
        water = bucketCapacity;
        fillTime = 3;
        fillRemainingTime = 0;
        fillPrice = 20;
        upgradePrice = 300;
    }

    public void upgrade() {
        level++;
        bucketCapacity += 1;
        fillPrice -= 2;
        upgradePrice *= 1.2;
    }

    public ResultType work() {
        if (water > 0) return ResultType.BAD_CONDITION;
        if (isWorking()) return ResultType.EXISTED;
        if (fillPrice > Game.getInstance().getCoin()) return ResultType.NOT_ENOUGH;
        Game.getInstance().decreaseCoin(fillPrice);
        fillRemainingTime = fillTime;
        return ResultType.SUCCESS;
    }

    public void decreaseWater() {
        water--;
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public int getWater() {
        return water;
    }

    public boolean isWorking() {
        return fillRemainingTime > 0;
    }

    public void update() {
        if (fillRemainingTime > 0) {
            fillRemainingTime--;
            if (fillRemainingTime == 0) {
                water = bucketCapacity;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Well L").append(level).append(":\n");
        if (isWorking())
            sb.append("\t").append("Remaining Time to Fill Well = ").append(fillRemainingTime).append("\n");
        else
            sb.append("\t").append("Water = ").append(water).append("\n");
        return sb.toString();
    }
}
