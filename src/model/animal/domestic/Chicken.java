package model.animal.domestic;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;

public class Chicken extends Domestic {

    public Chicken() {
        super(100, 8, "Egg");
    }

    @Override
    public void setAnimation() {

        double cellWidth, cellHeight;

        Image[] images = new Image[8];
        images[0] = new Image(new File("src/resource/Chicken/right.png").toURI().toString());
        images[1] = new Image(new File("src/resource/Chicken/down_right.png").toURI().toString());
        images[2] = new Image(new File("src/resource/Chicken/down.png").toURI().toString());
        images[3] = new Image(new File("src/resource/Chicken/down_left.png").toURI().toString());
        images[4] = new Image(new File("src/resource/Chicken/left.png").toURI().toString());
        images[5] = new Image(new File("src/resource/Chicken/up_left.png").toURI().toString());
        images[6] = new Image(new File("src/resource/Chicken/up.png").toURI().toString());
        images[7] = new Image(new File("src/resource/Chicken/up_right.png").toURI().toString());

        int[] numberOfRows = {5, 5, 5, 5, 5, 5, 5, 5};
        int[] numberOfColumns = {5, 5, 5, 5, 5, 5, 5, 5};
        boolean[] leftStart = {false, false, true, true, true, true, true, false};

        Rectangle2D[][] cells = new Rectangle2D[8][24];
        for (int i = 0; i < 8; i++) {
            cells[i] = new Rectangle2D[24];
            cellWidth = images[i].getWidth() / numberOfColumns[i];
            cellHeight = images[i].getHeight() / numberOfRows[i];

            for (int j = 0; j < numberOfRows[i]; j++) {
                for (int k = 0; k < Math.min(numberOfColumns[i], 24 - j * numberOfColumns[i]); k++) {
                    cells[i][j * numberOfColumns[i] + k] = new Rectangle2D((leftStart[i] ? k : numberOfColumns[i] - 1 - k) * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        Image[] deathImage = new Image[2];
        deathImage[0] = new Image(new File("src/resource/Chicken/death_right.png").toURI().toString());
        deathImage[1] = new Image(new File("src/resource/Chicken/death_left.png").toURI().toString());

        int numberOfDeathRows = 5;
        int numberOfDeathColumns = 5;

        Rectangle2D[][] deathCells = new Rectangle2D[2][24];
        for (int i = 0; i < 2; i++) {
            deathCells[i] = new Rectangle2D[24];
            cellWidth = deathImage[i].getWidth() / numberOfDeathColumns;
            cellHeight = deathImage[i].getHeight() / numberOfDeathRows;

            for (int j = 0; j < numberOfDeathRows; j++) {
                for (int k = 0; k < Math.min(numberOfDeathColumns, 24 - j * numberOfDeathColumns); k++) {
                    deathCells[i][j * numberOfDeathColumns + k] = new Rectangle2D((i % 2 == 1 ? k : numberOfDeathColumns - 1 - k) * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        Image[] eatImage = new Image[2];
        eatImage[0] = new Image(new File("src/resource/Chicken/eat_right.png").toURI().toString());
        eatImage[1] = new Image(new File("src/resource/Chicken/eat_left.png").toURI().toString());

        int numberOfEatRows = 5;
        int numberOfEatColumns = 5;

        Rectangle2D[][] eatCells = new Rectangle2D[2][24];
        for (int i = 0; i < 2; i++) {
            eatCells[i] = new Rectangle2D[24];
            cellWidth = eatImage[i].getWidth() / numberOfEatColumns;
            cellHeight = eatImage[i].getHeight() / numberOfEatRows;

            for (int j = 0; j < numberOfEatRows; j++) {
                for (int k = 0; k < Math.min(numberOfEatColumns, 24 - j * numberOfEatColumns); k++) {
                    eatCells[i][j * numberOfEatColumns + k] = new Rectangle2D((i % 2 == 1 ? k : numberOfEatColumns - 1 - k) * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        super.setAnimation(images, cells, deathImage, deathCells, eatImage, eatCells);
    }
}
