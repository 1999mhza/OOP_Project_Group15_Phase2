package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Grass {
    private double value;

    protected ImageView[] imageViews;
    protected GrassAnimation grassAnimation;
    protected Rectangle2D[] cells;

    public Grass(double x, double y) {
        this.value = 16;

        imageViews = new ImageView[9];
        for (int i = 0; i < 9; i++) {
            imageViews[i] = new ImageView();
            imageViews[i].setPickOnBounds(false);
        }

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

        for (int i = 0; i < 9; i++) {
            imageViews[i].setImage(image);
            imageViews[i].setViewport(cells[0]);
        }

        x = x - cellWidth / 2;
        y = y - cellHeight / 2;

        imageViews[0].setLayoutX(x - 16);
        imageViews[0].setLayoutY(y - 16);
        imageViews[1].setLayoutX(x + 16);
        imageViews[1].setLayoutY(y - 16);
        imageViews[2].setLayoutX(x - 16);
        imageViews[2].setLayoutY(y + 16);
        imageViews[3].setLayoutX(x + 16);
        imageViews[3].setLayoutY(y + 16);

        imageViews[4].setLayoutX(x);
        imageViews[4].setLayoutY(y - 24);
        imageViews[5].setLayoutX(x - 24);
        imageViews[5].setLayoutY(y);
        imageViews[6].setLayoutX(x + 24);
        imageViews[6].setLayoutY(y);
        imageViews[7].setLayoutX(x);
        imageViews[7].setLayoutY(y + 24);

        imageViews[8].setLayoutX(x);
        imageViews[8].setLayoutY(y);

        Game game = Game.getInstance();
        game.getGrasses().add(this);
        game.getGrassPane().getChildren().addAll(imageViews);

        grassAnimation = new GrassAnimation(this);
        grassAnimation.play();
    }


    public double getY() {
        return imageViews[8].getLayoutY() + cells[0].getHeight() / 2;
    }

    public double getX() {
        return imageViews[8].getLayoutX() + cells[0].getWidth() / 2;
    }

    public boolean isExist() {
        return value > 0;
    }

    public void decreaseValue(double v) {
        value -= 2.0 / 3.0 * v;
        if (value > 0) {
            for (int i = 0; i < 4; i++) {
                int num = (int) (value * 1 / 2);
                if (num >= value * 1 / 2) num = (int) (value * 1 / 2 - 1);
                imageViews[i].setViewport(cells[num]);
            }
            for (int i = 4; i < 8; i++) {
                int num = (int) (value * 3 / 4);
                if (num >= value * 3 / 4) num = (int) (value * 3 / 4 - 1);
                imageViews[i].setViewport(cells[num]);
            }
            int num = (int) (value);
            if (num >= value) num = (int) (value - 1);
            imageViews[8].setViewport(cells[num]);
        } else {
            Game.getInstance().getGrassPane().getChildren().removeAll(imageViews);
            Game.getInstance().getGrasses().remove(this);
        }
    }

    public void update(double v) {
        for (int i = 0; i < 4; i++) {
            int num = (int) (v * 8);
            if (num >= 8) num = 7;
            imageViews[i].setViewport(cells[num]);
        }
        for (int i = 4; i < 8; i++) {
            int num = (int) (v * 12);
            if (num >= 12) num = 11;
            imageViews[i].setViewport(cells[num]);
        }
        int num = (int) (v * 16);
        if (num >= 16) num = 15;
        imageViews[8].setViewport(cells[num]);
    }
}
