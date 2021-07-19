package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;

public class Cage {
    private int level;
    private int finalLevel;
    private double cageTimeDecrease;
    private int upgradePrice;

    private int[] buildCellsNumber;
    private int[] buildCellsRow;
    private int[] buildCellsColumn;

    private Image buildImage;
    private Rectangle2D[] buildCells;

    private Image breakImage;
    private Rectangle2D[] breakCells;

    public Cage() {
        finalLevel = 4;
        level = 1;
        cageTimeDecrease = 0;
        upgradePrice = 300;

        buildCellsNumber = new int[]{9, 8, 9, 4};
        buildCellsRow = new int[]{3, 2, 3, 2};
        buildCellsColumn = new int[]{3, 4, 3, 2};

        double cellWidth, cellHeight;

        buildImage = new Image(new File("src/resource/Cage/1_build.png").toURI().toString());
        buildCells = new Rectangle2D[buildCellsNumber[level - 1]];

        cellWidth = buildImage.getWidth() / buildCellsColumn[level - 1];
        cellHeight = buildImage.getHeight() / buildCellsRow[level - 1];
        for (int j = 0; j < buildCellsRow[level - 1]; j++) {
            for (int k = 0; k < buildCellsColumn[level - 1]; k++) {
                buildCells[j * buildCellsColumn[level - 1] + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        breakImage = new Image(new File("src/resource/Cage/1_break.png").toURI().toString());
        breakCells = new Rectangle2D[24];
        cellWidth = breakImage.getWidth() / 5;
        cellHeight = breakImage.getHeight() / 5;

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < Math.min(5, 24 - j * 5); k++) {
                breakCells[j * 5 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public boolean checkFinalLevel() {
        return level >= finalLevel;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public int getLevel() {
        return level;
    }

    public double getCageTimeDecrease() {
        return cageTimeDecrease;
    }

    public int getBuildCellsNumber() {
        return buildCellsNumber[level - 1];
    }

    public Image getBuildImage() {
        return buildImage;
    }

    public Rectangle2D[] getBuildCells() {
        return buildCells;
    }

    public Image getBreakImage() {
        return breakImage;
    }

    public Rectangle2D[] getBreakCells() {
        return breakCells;
    }

    public void upgrade() {
        level++;
        cageTimeDecrease += 0.5;
        upgradePrice *= 1.2;

        double cellWidth, cellHeight;

        buildImage = new Image(new File("src/resource/Cage/" + level + "_build.png").toURI().toString());
        buildCells = new Rectangle2D[buildCellsNumber[level - 1]];

        cellWidth = buildImage.getWidth() / buildCellsColumn[level - 1];
        cellHeight = buildImage.getHeight() / buildCellsRow[level - 1];
        for (int j = 0; j < buildCellsRow[level - 1]; j++) {
            for (int k = 0; k < buildCellsColumn[level - 1]; k++) {
                buildCells[j * buildCellsColumn[level - 1] + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        breakImage = new Image(new File("src/resource/Cage/" + level + "_break.png").toURI().toString());
        breakCells = new Rectangle2D[24];
        cellWidth = breakImage.getWidth() / 5;
        cellHeight = breakImage.getHeight() / 5;

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < Math.min(5, 24 - j * 5); k++) {
                breakCells[j * 5 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
