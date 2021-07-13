package model.good;

import model.Game;

import java.util.Random;

public abstract class Good {
    protected int space;
    protected int price;
    protected int lifetime;
    protected Random rand;
    protected int i, j;

    public Good(int space, int lifetime, int price) {
        this.space = space;
        this.lifetime = lifetime + 1;
        this.price = price;
        this.rand = new Random();
        this.i = rand.nextInt(Game.SIZE);
        this.j = rand.nextInt(Game.SIZE);
    }

    public void setPlace(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getSpace() {
        return space;
    }

    public int getPrice() {
        return price;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void update() {
        if (lifetime > 0) {
            lifetime--;
            if (lifetime == 0) {
                Game.getInstance().getGoods().remove(this);
            }
        }
    }

    @Override
    public String toString() {
        return (lifetime > 1 ? "" : "* ") + this.getClass().getSimpleName() + " " + lifetime + " [" + (i + 1) + " " + (j + 1) + "]";
    }
}
