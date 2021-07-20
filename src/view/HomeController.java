package view;

import controller.Logger;
import controller.UserManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class HomeController {
    public Button login;
    public Button signup;
    public Button delete;
    public Button exit;
    public Button setting;
    public TextField username;
    public TextField password;
    public Label result;
    public ImageView image;
    public VBox vbox;
    public HBox hbox;

    private FadeTransition fadeTransition;

    public void initiate(MediaPlayer homeMedia, MediaPlayer gameMedia, MediaPlayer winMedia, double width, double height) {

        double s = Math.min(height / image.getImage().getHeight(), width / image.getImage().getWidth());
        image.setFitHeight(s * image.getImage().getHeight());
        image.setFitWidth(s * image.getImage().getWidth());
        image.setX((width - image.getFitWidth()) / 2);
        image.setY((height - image.getFitHeight()) / 2);

        vbox.setLayoutX((width - vbox.getPrefWidth()) / 2);
        vbox.setLayoutY((height - vbox.getPrefHeight()) / 2);

        hbox.setLayoutX((width - image.getFitWidth()) / 2 + 5);

        UserManager userManager = UserManager.getInstance();

        result.setVisible(false);
        result.setPrefWidth(s * result.getPrefWidth());
        result.setPrefHeight(s * result.getPrefHeight());
        result.setLayoutX(s * (result.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        fadeTransition = new FadeTransition(Duration.seconds(1), result);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        login.setOnMousePressed(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        login.setOnMouseReleased(event -> {
            if (login.isHover()) login.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        login.setOnMouseEntered(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        login.setOnMouseExited(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        login.setOnAction(event -> {
            setResult("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                setResult("Empty Username!");
                return;
            }

            if (!userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username not existing.");
                setResult("No Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (userManager.checkIncorrectPassword(username, password)) {
                Logger.log("error", "The user entered an incorrect password.");
                setResult("Incorrect Password!");
                return;
            }

            Logger.log("info", username + " entered the game menu.");
            result.setVisible(false);

            Stage stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/menu.fxml"));
            try {
                stage.getScene().setRoot(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((MenuController) (loader.getController())).initiate(username, homeMedia, gameMedia, winMedia, width, height);
        });

        signup.setOnMousePressed(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        signup.setOnMouseReleased(event -> {
            if (signup.isHover()) signup.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        signup.setOnMouseEntered(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        signup.setOnMouseExited(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        signup.setOnAction(event -> {
            setResult("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                setResult("Empty Username!");
                return;
            }

            if (userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username existing.");
                setResult("Duplicate Username!");
                return;
            }

            if (!userManager.checkUsername(username)) {
                Logger.log("error", "The user entered a inappropriate username.");
                setResult("Bad Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (!userManager.checkPassword(password)) {
                Logger.log("error", "The user entered a inappropriate password.");
                setResult("Bad Password!");
                return;
            }

            Logger.log("info", username + " was added to the user list.");
            userManager.addUser(username, password);
            setResult("Successful Sign Up!");
        });

        delete.setOnMousePressed(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        delete.setOnMouseReleased(event -> {
            if (delete.isHover()) delete.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: gold;
                    -fx-border-color:  darkgoldenrod;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        delete.setOnMouseEntered(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        delete.setOnMouseExited(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        delete.setOnAction(event -> {
            setResult("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                setResult("Empty Username!");
                return;
            }

            if (!userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username not existing.");
                setResult("No Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (userManager.checkIncorrectPassword(username, password)) {
                Logger.log("error", "The user entered an incorrect password.");
                setResult("Incorrect Password!");
                return;
            }

            Logger.log("info", username + " deleted his/her account.");
            userManager.removeUser(username);
            setResult("Successful Delete!");
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

        setting.setOnMousePressed(event -> setting.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lavender;
                -fx-border-color:  indigo;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        setting.setOnMouseReleased(event -> {
            if (setting.isHover()) setting.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: medium purple;
                    -fx-border-color:  indigo;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        setting.setOnMouseEntered(event -> setting.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: mediumpurple;
                -fx-border-color:  indigo;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        setting.setOnMouseExited(event -> setting.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color:  plum;
                -fx-border-color:  indigo;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        setting.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/option_dialog.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            ((OptionController) (loader.getController())).initiate(homeMedia, gameMedia, winMedia);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });
    }

    public void setResult(String s) {
        result.setVisible(true);
        result.setText(s);
        fadeTransition.playFromStart();
    }
}
