package model.factory;

import controller.ResultType;
import model.Game;
import model.good.Good;
import model.good.GoodList;

import java.util.HashSet;

public abstract class Factory {
    protected int price;
    protected int upgradePrice;
    protected int level;
    protected int finalLevel;
    protected int number;
    protected int produceTime;
    protected int produceRemainingTime;
    protected String input;
    protected String output;

    public Factory(int price, int produceTime, String input, String output) {
        this.price = price;
        this.upgradePrice = price;
        this.level = 1;
        this.finalLevel = 5;
        this.number = 1;
        this.produceTime = produceTime;
        this.produceRemainingTime = 0;
        this.input = input;
        this.output = output;
    }

    public int getPrice() {
        return price;
    }

    public void upgrade() {
        level++;
        upgradePrice *= 1.2;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public ResultType work(int number) {
        if (produceRemainingTime > 0) return ResultType.EXISTED;
        if (number > level || number <= 0) return ResultType.INVALID_NUMBER;
        HashSet<Good> goods = Game.getInstance().getWarehouse().getGood(input, number);
        if (goods == null) return ResultType.NOT_ENOUGH;
        this.number = number;
        produceRemainingTime = produceTime * number / level + (((produceTime * number) % level == 0) ? 0 : 1);
        return ResultType.SUCCESS;
    }

    public void update() {
        if (produceRemainingTime > 0) {
            produceRemainingTime--;
            if (produceRemainingTime == 0) {
                GoodList goodList = GoodList.getGood(output);
                try {
                    for (int i = 0; i < number; i++) {
                        Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                        Game.getInstance().getGoods().add(good);
                    }
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }
    }

    @Override
    public String toString() {
        return (produceRemainingTime > 0 ? "* " : "") + this.getClass().getSimpleName() + " L" + level + (produceRemainingTime > 0 ? (": Remaining Time to Factory Finish = " + produceRemainingTime) : "");
    }
}
