package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class FinishController {

    public Button ok;
    public Label coin;
    public Label time;
    public Label reward;
    public Label bank;

    public void initiate(String username, MediaPlayer homeMedia, MediaPlayer gameMedia, MediaPlayer winMedia, double width, double height, int coinNumber, int timeSecond, int rewardValue, int bankValue) {

        ok.setOnMousePressed(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        ok.setOnMouseReleased(event -> {
            if (ok.isHover()) ok.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        ok.setOnMouseEntered(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        ok.setOnMouseExited(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        ok.setOnAction(event -> {
            winMedia.stop();
            winMedia.seek(Duration.ZERO);
            homeMedia.play();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/menu.fxml"));
            try {
                stage.getScene().setRoot(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((MenuController) (loader.getController())).initiate(username, homeMedia, gameMedia, winMedia, width, height);
        });

        coin.setText(String.valueOf(coinNumber));
        time.setText(String.format("%02d:%02d", timeSecond / 60, timeSecond % 60));
        reward.setText(String.valueOf(rewardValue));
        bank.setText(String.valueOf(bankValue));
    }
}
