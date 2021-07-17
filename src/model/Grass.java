package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.animal.AnimalAnimation;

import java.io.File;

public class Grass {
    private double i;
    private double j;
    private int value;

    protected ImageView imageView;
    protected AnimalAnimation animalAnimation;
    protected Rectangle2D[] cells;

    public Grass(double i, double j) {
        imageView = new ImageView(new Image(new File("src/resource/Grass/grass/png").toURI().toString()));
        this.i = i;
        this.j = j;
        this.value = 3;
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public void decreaseValue() {
        value--;
    }
}
