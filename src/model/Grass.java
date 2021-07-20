package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Grass {
    private double value;

    protected ImageView imageView;
    protected GrassAnimation grassAnimation;
    protected Rectangle2D[] cells;

    public Grass(double x, double y, double value) {
        this.value = value;

        imageView = new ImageView();

        Image image = new Image(new File("src/resource/Grass/grass.png").toURI().toString());
        cells = new Rectangle2D[16];

        int numberOfDeathRows = 4;
        int numberOfDeathColumns = 4;
        double cellWidth = image.getWidth() / 4;
        double cellHeight = image.getHeight() / 4;

        for (int j = 0; j < numberOfDeathRows; j++) {
            for (int k = 0; k < numberOfDeathColumns; k++) {
                cells[j * numberOfDeathColumns + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        imageView.setImage(image);
        imageView.setViewport(cells[0]);

        x = x - cellWidth / 2;
        y = y - cellHeight / 2;

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        Game game = Game.getInstance();
        game.getGrasses().add(this);
        game.getRoot().getChildren().add(imageView);
        imageView.toBack();

        grassAnimation = new GrassAnimation(this, value);
        grassAnimation.play();
    }


    public double getY() {
        return imageView.getLayoutY() + cells[0].getHeight() / 2;
    }

    public double getX() {
        return imageView.getLayoutX() + cells[0].getWidth() / 2;
    }

    public boolean isExist() {
        return value > 0;
    }

    public void decreaseValue(double v) {
        value -= 4 * v;
        if (value > 0) {
            int num = (int) Math.ceil(value);
            if (num >= 15) num = 15;
            imageView.setViewport(cells[num]);
        } else {
            Game.getInstance().getRoot().getChildren().remove(imageView);
            Game.getInstance().getGrasses().remove(this);
        }
    }

    public void update(double v) {
        int num = (int) (v * value);
        if (num >= value) num = (int) Math.round(value - 1);
        imageView.setViewport(cells[num]);
    }
}
