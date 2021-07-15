package view;

import controller.Logger;
import controller.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class HomeController {
    public StackPane background;
    public Button login;
    public Button signup;
    public Button delete;
    public Button exit;
    public TextField username;
    public TextField password;
    public Label l1;
    public Label l2;
    public Label result;

    private MediaPlayer homeMedia;
    private MediaPlayer gameMedia;

    public void initiate(MediaPlayer homeMedia, MediaPlayer gameMedia) {

        this.homeMedia = homeMedia;
        this.gameMedia = gameMedia;

        Font font = Font.loadFont("file:src/resource/PressStart2P-Regular.ttf", 20);
        UserManager userManager = UserManager.getInstance();

        l1.setFont(font);
        l2.setFont(font);
        result.setFont(Font.loadFont("file:src/resource/PressStart2P-Regular.ttf", 10));
        result.setVisible(false);

        login.setFont(font);
        login.setOnMousePressed(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        login.setOnMouseReleased(event -> {
            if (login.isHover()) login.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        login.setOnMouseEntered(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        login.setOnMouseExited(event -> login.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        login.setOnAction(event -> {
            result.setText("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                result.setText("Empty Username!");
                return;
            }

            if (!userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username not existing.");
                result.setText("No Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (userManager.checkIncorrectPassword(username, password)) {
                Logger.log("error", "The user entered an incorrect password.");
                result.setText("Incorrect Password!");
                return;
            }

            Logger.log("info", username + " entered the game menu.");
            result.setVisible(false);

            Stage stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/menu.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }/*
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setResizable(false);*/
            //stage.initStyle(StageStyle.UNDECORATED);
            MenuController menuController = (MenuController) (loader.getController());
            menuController.initiate(username, homeMedia, gameMedia);
            //stage.show();
            menuController.initiate(stage.getWidth(), stage.getHeight());
        });

        signup.setFont(font);
        signup.setOnMousePressed(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        signup.setOnMouseReleased(event -> {
            if (signup.isHover()) signup.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        signup.setOnMouseEntered(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        signup.setOnMouseExited(event -> signup.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        signup.setOnAction(event -> {
            result.setText("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                result.setText("Empty Username!");
                return;
            }

            if (userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username existing.");
                result.setText("Duplicate Username!");
                return;
            }

            if (!userManager.checkUsername(username)) {
                Logger.log("error", "The user entered a inappropriate username.");
                result.setText("Bad Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (!userManager.checkPassword(password)) {
                Logger.log("error", "The user entered a inappropriate password.");
                result.setText("Bad Password!");
                return;
            }

            Logger.log("info", username + " was added to the user list.");
            userManager.addUser(username, password);
            result.setText("Successful Sign Up!");
        });

        delete.setFont(font);
        delete.setOnMousePressed(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgoldenrodyellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        delete.setOnMouseReleased(event -> {
            if (delete.isHover()) delete.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: gold;
                    -fx-border-color:  darkgoldenrod;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        delete.setOnMouseEntered(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: gold;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        delete.setOnMouseExited(event -> delete.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: yellow;
                -fx-border-color:  darkgoldenrod;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        delete.setOnAction(event -> {
            result.setText("");
            result.setVisible(true);

            String username = this.username.getText();
            String password = this.password.getText();

            this.username.setText("");
            this.password.setText("");

            if (username.length() <= 0) {
                Logger.log("error", "The user entered an empty username.");
                result.setText("Empty Username!");
                return;
            }

            if (!userManager.checkUserExists(username)) {
                Logger.log("error", "The user entered a username not existing.");
                result.setText("No Username!");
                return;
            }

            Logger.log("info", "User " + username);
            if (userManager.checkIncorrectPassword(username, password)) {
                Logger.log("error", "The user entered an incorrect password.");
                result.setText("Incorrect Password!");
                return;
            }

            Logger.log("info", username + " deleted his/her account.");
            userManager.removeUser(username);
            result.setText("Successful Delete!");
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
    }

    public void initiate(double width, double height) {

        ImageView imageView = new ImageView(new Image(new File("src/resource/back.jpg").toURI().toString()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        background.getChildren().add(imageView);
        imageView.toBack();
    }
}
