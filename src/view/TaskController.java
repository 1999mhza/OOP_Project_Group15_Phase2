package view;

import controller.MissionManager;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TaskController {
    public Button play;

    public Label prize;
    public Label time;

    public ImageView imageTask1;
    public Label labelTask1;
    public ImageView imageTask2;
    public Label labelTask2;
    public ImageView imageTask3;
    public Label labelTask3;
    public ImageView imageTask4;
    public Label labelTask4;

    public void initiate() {

        Game game = Game.getInstance();

        play.setOnMousePressed(event -> play.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        play.setOnMouseReleased(event -> {
            if (play.isHover()) play.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        play.setOnMouseEntered(event -> play.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        play.setOnMouseExited(event -> play.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        play.setOnAction(event -> {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            game.play();
        });

        HashMap<String, Integer[]> tasks = game.getTasks();

        Label[] labels = new Label[]{labelTask1, labelTask2, labelTask3, labelTask4};
        ImageView[] imageViews = new ImageView[]{imageTask1, imageTask2, imageTask3, imageTask4};

        int i = 0;
        for (Map.Entry<String, Integer[]> entry : tasks.entrySet()) {
            imageViews[i].setImage(new Image(new File("src/resource/Task/" + entry.getKey() + ".png").toURI().toString()));
            labels[i].setText(String.valueOf(entry.getValue()[0]));
            ((HBox) labels[i].getParent()).setVisible(true);
            i++;
        }

        int maxTime = (int) Math.round(MissionManager.getInstance().getMaxPrizeTime(game.getLevel()));
        time.setText(String.format("%02d:%02d", maxTime / 60, maxTime % 60));
        prize.setText("+" + MissionManager.getInstance().getPrize(game.getLevel()));
    }
}
