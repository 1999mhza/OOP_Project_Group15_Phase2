package view;

import controller.Logger;
import controller.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MenuController {

    public ImageView image;

    public Button exit;
    public Button logout;
    public Label coin;

    private String username;
    private MediaPlayer homeMedia;
    private MediaPlayer gameMedia;
    private double width;
    private double height;

    public void initiate(String username, MediaPlayer homeMedia, MediaPlayer gameMedia, double width, double height) {

        this.username = username;
        this.homeMedia = homeMedia;
        this.gameMedia = gameMedia;
        this.width = width;
        this.height = height;

        double s = Math.min(height / image.getImage().getHeight(), width / image.getImage().getWidth());
        image.setFitHeight(s * image.getImage().getHeight());
        image.setFitWidth(s * image.getImage().getWidth());
        image.setX((width - image.getFitWidth()) / 2);
        image.setY((height - image.getFitHeight()) / 2);

        VBox vBox = (VBox) coin.getParent();
        vBox.setLayoutX((width + image.getFitWidth()) / 2 - 20 - vBox.getPrefWidth());
        coin.setText(String.valueOf(UserManager.getInstance().getCollectedCoin(username)));

        logout.setOnMousePressed(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        logout.setOnMouseReleased(event -> {
            if (logout.isHover()) logout.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: gold;
                        -fx-border-color:  darkgoldenrod;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        logout.setOnMouseEntered(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        logout.setOnMouseExited(event -> logout.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        logout.setOnAction(event -> {
            Logger.log("info", "The user logged out.");
            Stage stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
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
            YesNoController controller = (YesNoController) (loader.getController());
            controller.initiate();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });

        int numberOfLevels = 20;
        int numberOfRows = 4;
        int numberOfRowButton = numberOfLevels / numberOfRows;
        double bias = 200;

        AnchorPane parent = (AnchorPane) image.getParent();
        HBox[] hBoxes = new HBox[numberOfRows];
        Button[] levels = new Button[numberOfLevels];
        ImageView[] arrows = new ImageView[numberOfLevels - 1];

        for (int i = 0; i < numberOfRows; i++) {
            hBoxes[i] = new HBox();
            parent.getChildren().add(hBoxes[i]);
            hBoxes[i].setLayoutX((width - (2 * numberOfRowButton - 1) * 50.0) / 2);
            hBoxes[i].setLayoutY(bias * s + i * 100.0);
            hBoxes[i].setAlignment(Pos.CENTER);

            for (int j = i * numberOfRowButton; j < (i + 1) * numberOfRowButton; j++) {
                int k = (i % 2 == 0) ? j : ((2 * i + 1) * numberOfRowButton - j - 1);
                levels[k] = new Button(String.valueOf(k + 1));
                levels[k].setVisible(false);
                setButton(levels[k], 0);
                hBoxes[i].getChildren().add(levels[k]);

                if (j < (i + 1) * numberOfRowButton - 1) {
                    if (i % 2 != 0) k--;
                    arrows[k] = new ImageView(new Image(new File("src/resource/arrow.png").toURI().toString()));
                    arrows[k].setVisible(false);
                    if (i % 2 != 0) arrows[k].setRotate(180);
                    hBoxes[i].getChildren().add(arrows[k]);
                }
            }

            if (i < numberOfRows - 1) {
                int k = (i + 1) * numberOfRowButton - 1;
                arrows[k] = new ImageView(new Image(new File("src/resource/arrow.png").toURI().toString()));
                arrows[k].setLayoutX(hBoxes[i].getLayoutX() + ((i % 2 == 0) ? (2 * numberOfRowButton - 2) * 50.0 : 0));
                arrows[k].setLayoutY(bias * s + 100.0 * i + 50.0 + (50.0 - 38.0) / 2);
                arrows[k].setRotate(90);
                arrows[k].setVisible(false);
                parent.getChildren().add(arrows[k]);
            }
        }


        HashMap<Integer, Integer> missionInfo = UserManager.getInstance().getMissionInformation(username);
        for (int i = 0; i < missionInfo.size(); i++) {
            setButton(levels[i], missionInfo.get(i + 1));
            levels[i].setVisible(true);
            int finalI = i;
            levels[i].setOnAction(event -> setAction(event, finalI + 1, missionInfo.get(finalI + 1) == 0));
            if (i < missionInfo.size() - 1)
                arrows[i].setVisible(true);
        }
    }

    private void setAction(ActionEvent event, int level, boolean isLock) {
        if (isLock) return;
        Stage stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        try {
            stage.getScene().setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeMedia.stop();
        homeMedia.seek(Duration.ZERO);
        gameMedia.play();
        ((GameController) (loader.getController())).initiate(username, level, homeMedia, gameMedia, width, height);
    }

    private void setButton(Button button, int type) {
        button.setPrefWidth(50);
        button.setPrefHeight(50);
        button.setAlignment(Pos.CENTER);

        switch (type) {
            case 0:
                button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: tomato;
                        -fx-border-color: darkred;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;""");
                button.setOnMousePressed(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lightpink;
                        -fx-border-color: darkred;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                            -fx-background-radius: 50;
                            -fx-background-color: indianred;
                            -fx-border-color: darkred;
                            -fx-border-width: 5;
                            -fx-border-radius: 50;
                            -fx-font-size: 15;
                            -fx-font-weight: bold;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: indianred;
                        -fx-border-color: darkred;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: tomato;
                        -fx-border-color: darkred;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                break;

            case 1:
                button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: deepskyblue;
                        -fx-border-color: darkblue;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;""");
                button.setOnMousePressed(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lightskyblue;
                        -fx-border-color: darkblue;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                            -fx-background-radius: 50;
                            -fx-background-color: dodgerblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 5;
                            -fx-border-radius: 50;
                            -fx-font-size: 15;
                            -fx-font-weight: bold;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: dodgerblue;
                        -fx-border-color: darkblue;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: deepskyblue;
                        -fx-border-color: darkblue;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                break;

            case 2:
                button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lawngreen;
                        -fx-border-color: darkgreen;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;""");
                button.setOnMousePressed(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lightgreen;
                        -fx-border-color: darkgreen;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                            -fx-background-radius: 50;
                            -fx-background-color: limegreen;
                            -fx-border-color: darkgreen;
                            -fx-border-width: 5;
                            -fx-border-radius: 50;
                            -fx-font-size: 15;
                            -fx-font-weight: bold;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: limegreen;
                        -fx-border-color: darkgreen;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lawngreen;
                        -fx-border-color: darkgreen;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                break;

            case 3:
                button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: yellow;
                        -fx-border-color:  darkgoldenrod;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;""");
                button.setOnMousePressed(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: lightgoldenrodyellow;
                        -fx-border-color:  darkgoldenrod;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseReleased(event -> {
                    if (button.isHover()) button.setStyle("""
                            -fx-background-radius: 50;
                            -fx-background-color: gold;
                            -fx-border-color:  darkgoldenrod;
                            -fx-border-width: 5;
                            -fx-border-radius: 50;
                            -fx-font-size: 15;
                            -fx-font-weight: bold;""");
                });
                button.setOnMouseEntered(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: gold;
                        -fx-border-color:  darkgoldenrod;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                button.setOnMouseExited(event -> button.setStyle("""
                        -fx-background-radius: 50;
                        -fx-background-color: yellow;
                        -fx-border-color:  darkgoldenrod;
                        -fx-border-width: 5;
                        -fx-border-radius: 50;
                        -fx-font-size: 15;
                        -fx-font-weight: bold;"""));
                break;
        }

    }
}
