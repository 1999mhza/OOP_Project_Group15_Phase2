package view;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import model.Parameter;

import java.io.File;

public class OptionController {
    public ImageView imageView;
    public Label label;
    public Slider slider;
    public Button ok;

    public void initiate() {
        Parameter parameter = Parameter.getInstance();

        Image image = new Image(new File("src/resource/volume.png").toURI().toString());
        imageView.setImage(image);
        Rectangle2D[] cells = new Rectangle2D[4];

        double cellWidth = image.getWidth() / 2;
        double cellHeight = image.getHeight() / 2;

        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) {
                cells[j * 2 + k] = new Rectangle2D(k * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        slider.setValue(parameter.getHomeMedia().getVolume() * 100);
        int volume = (int) Math.round(parameter.getHomeMedia().getVolume() * 100);
        label.setText(String.format("%% %d", volume));

        int num;
        if (volume == 0) num = 3;
        else if (volume < 34) num = 2;
        else if (volume < 68) num = 1;
        else num = 0;
        imageView.setViewport(cells[num]);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            parameter.getHomeMedia().setVolume(newValue.doubleValue() / 100);
            parameter.getGameMedia().setVolume(newValue.doubleValue() / 100);
            parameter.getWinMedia().setVolume(newValue.doubleValue() / 100);
            int volume2 = newValue.intValue();
            label.setText(String.format("%% %d", volume2));
            int num2;
            if (volume2 == 0) num2 = 3;
            else if (volume2 < 34) num2 = 2;
            else if (volume2 < 68) num2 = 1;
            else num2 = 0;
            imageView.setViewport(cells[num2]);
        });

        ok.setOnMousePressed(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        ok.setOnMouseReleased(event -> {
            if (ok.isHover()) ok.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 15;
                    -fx-font-weight: bold""");
        });
        ok.setOnMouseEntered(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        ok.setOnMouseExited(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        ok.setOnAction(event -> ((Node) (event.getSource())).getScene().getWindow().hide());
    }
}
