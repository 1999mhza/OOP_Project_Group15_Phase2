package model.animal.domestic;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;

public class Sheep extends Domestic {

    public Sheep() {
        super(200, 4, "Wool");
    }

    @Override
    public void setAnimation() {
        Image[] images = new Image[8];
        images[0] = new Image(new File("src/resource/Sheep/right.png").toURI().toString());
        images[1] = new Image(new File("src/resource/Sheep/down_right.png").toURI().toString());
        images[2] = new Image(new File("src/resource/Sheep/down.png").toURI().toString());
        images[3] = new Image(new File("src/resource/Sheep/down_left.png").toURI().toString());
        images[4] = new Image(new File("src/resource/Sheep/left.png").toURI().toString());
        images[5] = new Image(new File("src/resource/Sheep/up_left.png").toURI().toString());
        images[6] = new Image(new File("src/resource/Sheep/up.png").toURI().toString());
        images[7] = new Image(new File("src/resource/Sheep/up_right.png").toURI().toString());

        int[] numberOfRows = {6, 6, 5, 6, 6, 5, 5, 5};
        int[] numberOfColumns = {4, 4, 5, 4, 4, 5, 5, 5};
        boolean[] leftStart = {false, false, true, true, true, true, true, false};

        Rectangle2D[][] cells = new Rectangle2D[8][24];
        for (int i = 0; i < 8; i++) {
            cells[i] = new Rectangle2D[24];
            double cellWidth = images[i].getWidth() / numberOfColumns[i];
            double cellHeight = images[i].getHeight() / numberOfRows[i];

            for (int j = 0; j < numberOfRows[i]; j++) {
                for (int k = 0; k < Math.min(numberOfColumns[i],24 - j * numberOfColumns[i]); k++) {
                    cells[i][j * numberOfColumns[i] + k] = new Rectangle2D((leftStart[i] ? k : numberOfColumns[i] - 1 - k) * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        Image deathImage = new Image(new File("src/resource/Sheep/death.png").toURI().toString());
        Rectangle2D[] deathCells = new Rectangle2D[24];

        int numberOfDeathRows = 6;
        int numberOfDeathColumns = 4;
        double cellWidth = deathImage.getWidth() / numberOfDeathColumns;
        double cellHeight = deathImage.getHeight() / numberOfDeathRows;

        for (int j = 0; j < numberOfDeathRows; j++) {
            for (int k = 0; k < Math.min(numberOfDeathColumns, 24 - j * numberOfDeathColumns); k++) {
                deathCells[j * numberOfDeathColumns + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        super.setAnimation(images, cells, deathImage, deathCells);
    }
}