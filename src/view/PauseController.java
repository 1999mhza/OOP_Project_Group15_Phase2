package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Game;

import java.io.IOException;

public class PauseController {
    public Button continueE;
    public Button menu;
    public Button home;
    public Button exit;

    public void initiate(String username, MediaPlayer homeMedia, MediaPlayer gameMedia, double width, double height) {
        continueE.setOnMousePressed(event -> continueE.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        continueE.setOnMouseReleased(event -> {
            if (continueE.isHover()) continueE.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        continueE.setOnMouseEntered(event -> continueE.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        continueE.setOnMouseExited(event -> continueE.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        continueE.setOnAction(event -> {
            Game.getInstance().play();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

        menu.setOnMousePressed(event -> menu.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        menu.setOnMouseReleased(event -> {
            if (menu.isHover()) menu.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        menu.setOnMouseEntered(event -> menu.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        menu.setOnMouseExited(event -> menu.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        menu.setOnAction(event -> {
            gameMedia.stop();
            gameMedia.seek(Duration.ZERO);
            homeMedia.play();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/menu.fxml"));
            try {
                stage.getScene().setRoot(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((MenuController) (loader.getController())).initiate(username, homeMedia, gameMedia, width, height);
        });

        home.setOnMousePressed(event -> home.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        home.setOnMouseReleased(event -> {
            if (home.isHover()) home.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: gold;
                    -fx-border-color:  darkgoldenrod;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        home.setOnMouseEntered(event -> home.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        home.setOnMouseExited(event -> home.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        home.setOnAction(event -> {
            gameMedia.stop();
            gameMedia.seek(Duration.ZERO);
            homeMedia.play();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/home.fxml"));
            try {
                stage.getScene().setRoot(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((HomeController) (loader.getController())).initiate(homeMedia, gameMedia, width, height);
        });

        exit.setOnMousePressed(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightpink;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        exit.setOnMouseReleased(event -> {
            if (exit.isHover()) exit.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: indianred;
                    -fx-border-color: darkred;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        exit.setOnMouseEntered(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: indianred;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        exit.setOnMouseExited(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        exit.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/yes_no_dialog.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            ((YesNoController) (loader.getController())).initiate();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });
    }
}
