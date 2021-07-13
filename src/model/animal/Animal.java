package model.animal;

import model.Game;

import java.util.Random;

public abstract class Animal {
    protected Random rand;
    protected int i, j;
    protected int preI, preJ;
    protected int price;
    protected int space;
    protected int lifetime;
    protected int step;

    public Animal(int price, int lifetime, int space, int step) {
        this.rand = new Random();
        this.i = rand.nextInt(Game.SIZE);
        this.j = rand.nextInt(Game.SIZE);
        this.preI = -1;
        this.preJ = -1;
        this.price = price;
        this.lifetime = lifetime;
        this.space = space;
        this.step = step;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getPreI() {
        return preI;
    }

    public int getPreJ() {
        return preJ;
    }

    public int getPrice() {
        return price;
    }

    public int getSpace() {
        return space;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + (i + 1) + " " + (j + 1) + "]";
    }

    public void move(boolean vertical, boolean horizontal) {
        if (vertical) {
            if (i < step) {
                i += step;
            } else if (i >= Game.SIZE - step) {
                i -= step;
            } else {
                i += (horizontal ? step : -step);
            }
        } else {
            if (j < step) {
                j += step;
            } else if (j >= Game.SIZE - step) {
                j -= step;
            } else {
                j += (horizontal ? step : -step);
            }
        }
    }

    public boolean encounter(Animal that) {
        if (this.i != this.preI && this.j != this.preJ) return false;
        if (that.j != that.preJ && that.i != that.preI) return false;
        if (this.i == this.preI && that.j == that.preJ && this.j != this.preJ && that.i != that.preI) return false;
        if (this.i != this.preI && that.j != that.preJ && this.j == this.preJ && that.i == that.preI) return false;

        if (this.i == this.preI && this.i == that.i) {
            if ((this.j >= that.j && this.preJ <= that.j && this.j <= that.preJ) || (that.j >= this.j && that.preJ <= this.j && that.j <= this.preJ)) return true;
            if ((this.j <= that.j && this.preJ <= that.j && this.j >= that.preJ && this.preJ >= that.preJ) || (that.j <= this.j && that.preJ <= this.j && that.j >= this.preJ && that.preJ >= this.preJ)) return true;
            if ((this.j <= that.preJ && this.preJ <= that.preJ && this.j >= that.j && this.preJ >= that.j) || (that.j <= this.preJ && that.preJ <= this.preJ && that.j >= this.j && that.preJ >= this.j)) return true;
        }

        if (this.j == this.preJ && this.j == that.j) {
            if ((this.i >= that.i && this.preI <= that.i && this.i <= that.preI) || (that.i >= this.i && that.preI <= this.i && that.i <= this.preI)) return true;
            if ((this.i <= that.i && this.preI <= that.i && this.i >= that.preI && this.preI >= that.preI) || (that.i <= this.i && that.preI <= this.i && that.i >= this.preI && that.preI >= this.preI)) return true;
            if ((this.i <= that.preI && this.preI <= that.preI && this.i >= that.i && this.preI >= that.i) || (that.i <= this.preI && that.preI <= this.preI && that.i >= this.i && that.preI >= this.i)) return true;
        }
        return false;
    }
}
