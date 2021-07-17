package model.animal.protective;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;

public class Dog extends Protective {

    public Dog() {
        super(100);
    }

    @Override
    public void setAnimation() {
        Image[] images = new Image[8];
        images[0] = new Image(new File("src/resource/Dog/right.png").toURI().toString());
        images[1] = new Image(new File("src/resource/Dog/down_right.png").toURI().toString());
        images[2] = new Image(new File("src/resource/Dog/down.png").toURI().toString());
        images[3] = new Image(new File("src/resource/Dog/down_left.png").toURI().toString());
        images[4] = new Image(new File("src/resource/Dog/left.png").toURI().toString());
        images[5] = new Image(new File("src/resource/Dog/up_left.png").toURI().toString());
        images[6] = new Image(new File("src/resource/Dog/up.png").toURI().toString());
        images[7] = new Image(new File("src/resource/Dog/up_right.png").toURI().toString());

        int[] numberOfRows = {4, 5, 4, 5, 4, 5, 4, 5};
        int[] numberOfColumns = {6, 5, 6, 5, 6, 5, 6, 5};
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

        super.setAnimation(images, cells);
    }
}
