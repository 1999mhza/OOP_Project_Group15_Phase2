package model.animal.domestic;

import model.Game;
import model.animal.Animal;
import model.good.Good;
import model.good.GoodList;

import java.util.ArrayList;

public abstract class Domestic extends Animal {
    protected int produceTime;
    protected int produceRemainingTime;
    protected String product;
    private int fulLifetime;

    public Domestic(int price, int produceTime, String product) {
        super(price, 10, 5, 1);
        this.fulLifetime = 10;
        this.produceTime = produceTime;
        this.produceRemainingTime = produceTime;
        this.product = product;
    }

    public void move() {
        preI = i;
        preJ = j;
        if (this.lifetime > 5) {
            move(rand.nextBoolean(), rand.nextBoolean());
        } else {
            boolean horizontal = false;
            boolean vertical = false;
            int distance = Integer.MAX_VALUE;

            ArrayList<Integer> list1 = new ArrayList<>();
            ArrayList<Integer> list2 = new ArrayList<>();
            ArrayList<Integer> listI = new ArrayList<>();
            ArrayList<Integer> listJ = new ArrayList<>();
            for (int k = 0; k < Game.SIZE; k++) {
                list1.add(k);
                list2.add(k);
            }
            for (int k = Game.SIZE; k > 0; k--) {
                listI.add(list1.remove(rand.nextInt(k)));
                listJ.add(list2.remove(rand.nextInt(k)));
            }

            for (Integer ii : listI) {
                for (Integer jj : listJ) {
                    int dis = Math.abs(ii - i) + Math.abs(jj - j);
                    if (Game.getInstance().getGrass()[ii][jj] > 0 && dis < distance) {
                        distance = dis;
                        vertical = Math.abs(ii - i) > Math.abs(jj - j);
                        horizontal = (ii - i) + (jj - j) > 0;
                    }
                }
            }

            if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
            else if (distance != 0) super.move(vertical, horizontal);
        }
    }

    public void work() {
        if (produceRemainingTime > 0) {
            produceRemainingTime--;
            if (produceRemainingTime == 0) {
                produceRemainingTime = produceTime;
                GoodList goodList = GoodList.getGood(product);
                try {
                    Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                    good.setPlace(i, j);
                    Game.getInstance().getGoods().add(good);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        if (lifetime > 0) {
            lifetime--;
            if (lifetime == 0) {
                Game.getInstance().getDomesticAnimals().remove(this);
                Game.getInstance().updateTask(this.getClass().getSimpleName(), false);
            }
        }
    }

    public void eatGrass() {
        Game.getInstance().getGrass()[i][j]--;
        lifetime = fulLifetime + 1;
    }

    public boolean isHungry() {
        return lifetime <= 5;
    }

    public boolean isLessThan(Domestic that) {
        return this.lifetime < that.lifetime;
    }

    @Override
    public String toString() {
        return (lifetime > 5 ? "" : "* ") + this.getClass().getSimpleName() + " " + lifetime + " " + "[" + (i + 1) + " " + (j + 1) + "]";
    }
}
