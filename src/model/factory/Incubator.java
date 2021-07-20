package model.factory;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;

import java.io.File;

public class Incubator extends Factory {
    public Incubator() {
        super(450, 6, "Egg", "Chicken", 438, 120, 250, 50, 120, 0);
    }

    protected void setAnimation() {

        Image image = new Image(new File("src/resource/" + getClass().getSimpleName() + "/" + level + ".png").toURI().toString());
        imageView = new ImageView(image);
        progressBar = new ProgressBar();
        progressBar.setVisible(false);

        factoryAnimation = new FactoryAnimation(this);
        factoryAnimation.setOnFinished(event -> {
            DomesticList domesticList = DomesticList.getDomestic(output);
            try {
                for (int i = 0; i < number; i++) {
                    Domestic domestic = (Domestic) Class.forName(domesticList.getPackageName()).newInstance();
                    domestic.setAnimation();
                    domestic.setPosition(goodX + random.nextDouble() * 20, goodY + random.nextDouble() * 20);
                    domestic.play();
                    Game.getInstance().getDomesticAnimals().add(domestic);
                    Game.getInstance().updateTask(domestic.getClass().getSimpleName(), true);
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
            isWorking = false;
            progressBar.setVisible(false);
            imageView.setViewport(cells[0]);
        });

        cells = new Rectangle2D[16];
        double cellWidth = image.getWidth() / 4;
        double cellHeight = image.getHeight() / 4;

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                cells[j * 4 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        createImage(0);
    }
}
