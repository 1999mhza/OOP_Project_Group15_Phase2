package view;

import controller.GameManager;
import controller.Logger;
import controller.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.game.Start;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class MenuController {

    public Button[] levels;

    public Label locked;
    public Label unlocked;
    public Label completed;
    public Label prized;

    public HBox h1;
    public HBox h2;
    public HBox h3;
    public ImageView arrow1;
    public ImageView arrow2;

    public StackPane background;
    public Button exit;
    public Button logout;

    private MediaPlayer homeMedia;
    private MediaPlayer gameMedia;

    private String username;

    public void initiate(double width, double height) {

        ImageView imageView = new ImageView(new Image(new File("src/resource/back.jpg").toURI().toString()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        background.getChildren().add(imageView);
        imageView.toBack();
    }

    public void initiate(String username, MediaPlayer homeMedia, MediaPlayer gameMedia) {
        this.username = username;
        this.homeMedia = homeMedia;
        this.gameMedia = gameMedia;

        Font font = Font.loadFont("file:src/resource/PressStart2P-Regular.ttf", 20);

        logout.setFont(font);
        logout.setOnMousePressed(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        logout.setOnMouseReleased(event -> {
            if (logout.isHover()) logout.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: gold;
                    -fx-border-color:  darkgoldenrod;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        logout.setOnMouseEntered(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        logout.setOnMouseExited(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        logout.setOnAction(event -> {
            Logger.log("info", "The user logged out.");
            Stage stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/home.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }/*
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setResizable(false);*/
            //stage.initStyle(StageStyle.UNDECORATED);
            HomeController homeController = (HomeController) (loader.getController());
            homeController.initiate(homeMedia, gameMedia);
            homeController.initiate(stage.getWidth(), stage.getHeight());
        });

        exit.setFont(font);
        exit.setOnMousePressed(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightpink;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        exit.setOnMouseReleased(event -> {
            if (exit.isHover()) exit.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: indianred;
                    -fx-border-color: darkred;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        exit.setOnMouseEntered(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: indianred;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        exit.setOnMouseExited(event -> exit.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        exit.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/yes_no_dialog.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            YesNoController controller = (YesNoController) (loader.getController());
            controller.initiate();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });

        Font font2 = Font.loadFont("file:src/resource/PressStart2P-Regular.ttf", 10);

        locked.setFont(font2);
        unlocked.setFont(font2);
        completed.setFont(font2);
        prized.setFont(font2);

        levels = new Button[21];
        ImageView[] arrows = new ImageView[20];
        arrows[6] = arrow1;
        arrows[13] = arrow2;
        arrow1.setVisible(false);
        arrow2.setVisible(false);
        for (int i = 0; i < 7; i++) {
            levels[i] = new Button(String.valueOf(i + 1));
            levels[i].setVisible(false);
            setButton(levels[i], font2, 0);
            h1.getChildren().add(levels[i]);
            if (i < 6) {
                arrows[i] = new ImageView(new Image(new File("src/resource/arrow.png").toURI().toString()));
                arrows[i].setVisible(false);
                h1.getChildren().add(arrows[i]);
            }
        }
        for (int i = 13; i >= 7; i--) {
            levels[i] = new Button(String.valueOf(i + 1));
            levels[i].setVisible(false);
            setButton(levels[i], font2, 0);
            h2.getChildren().add(levels[i]);
            if (i > 7) {
                arrows[i - 1] = new ImageView(new Image(new File("src/resource/arrow.png").toURI().toString()));
                arrows[i - 1].setRotate(180);
                arrows[i - 1].setVisible(false);
                h2.getChildren().add(arrows[i - 1]);
            }
        }
        for (int i = 14; i < 21; i++) {
            levels[i] = new Button(String.valueOf(i + 1));
            levels[i].setVisible(false);
            setButton(levels[i], font2, 0);
            h3.getChildren().add(levels[i]);
            if (i < 20) {
                arrows[i] = new ImageView(new Image(new File("src/resource/arrow.png").toURI().toString()));
                arrows[i].setVisible(false);
                h3.getChildren().add(arrows[i]);
            }
        }

        HashMap<Integer, Integer> missionInfo = UserManager.getInstance().getMissionInformation(username);
        int numberOfLevels = missionInfo.size();
        Random random = new Random();
        for (int i = 0; i < numberOfLevels; i++) {
            setButton(levels[i], font2, missionInfo.get(i + 1));
            levels[i].setVisible(true);
            int finalI = i;
            levels[i].setOnAction(event -> setAction(finalI + 1, missionInfo.get(finalI + 1) == 0));
            if (i < numberOfLevels - 1)
                arrows[i].setVisible(true);
        }
    }

    private void setAction(int level, boolean isLock) {
        if (isLock) return;
        // new Start(new GameManager(username), level).run();
    }

    private void setButton(Button button, Font font, int type) {
        button.setPrefWidth(50);
        button.setPrefHeight(50);
        button.setFont(font);

        switch (type) {
            case 0:
                button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;""");
                button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightpink;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: indianred;
                    -fx-border-color: darkred;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: indianred;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                break;

            case 1:
                button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;""");
                button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                break;

            case 2:
                button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;""");
                button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                break;

            case 3:
                button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;""");
                button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: gold;
                    -fx-border-color:  darkgoldenrod;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
                break;
        }

    }
}
