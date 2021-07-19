package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.animal.AnimalAnimation;

import java.io.File;

public class Grass {
    private int value;

    protected ImageView imageView;
    protected GrassAnimation grassAnimation;
    protected Rectangle2D[] cells;

    public Grass(double x, double y) {
        this.value = 8;

        imageView = new ImageView();

        Image image = new Image(new File("src/resource/Grass/grass.png").toURI().toString());
        cells = new Rectangle2D[8];

        int numberOfDeathRows = 2;
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

        if (x < cellWidth / 2)
            x = 0;
        else if (x > Game.getInstance().getWidth() - cellWidth / 2)
            x = Game.getInstance().getWidth() - cellWidth;
        else
            x = x - cellWidth / 2;

        if (y < cellHeight / 2)
            y = 0;
        else if (y > Game.getInstance().getHeight() - cellHeight / 2)
            y = Game.getInstance().getHeight() - cellHeight;
        else
            y = y - cellHeight / 2;

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        Game game = Game.getInstance();
        game.getGrasses().add(this);
        game.getRoot().getChildren().add(imageView);
        imageView.toBack();

        grassAnimation = new GrassAnimation(this);
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

    public void decreaseValue() {
        value--;
        if (value == 0) {
            Game.getInstance().getGrasses().remove(this);
        }
    }

    public void decreaseImage() {
        if (value > 0) {
            imageView.setViewport(cells[value - 1]);
        } else {
            Game.getInstance().getRoot().getChildren().remove(imageView);
        }
    }

    public void update(double v) {

        int num = (int) (v * 7.05);
        if (num >= 7) num = 7;
        if (num < 0) num = 0;

        imageView.setViewport(cells[num]);
    }
}
