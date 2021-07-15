package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import view.HomeController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        MediaPlayer homeMedia = new MediaPlayer(new Media(new File("src/resource/Home.m4a").toURI().toString()));
        homeMedia.setOnEndOfMedia(() -> homeMedia.seek(Duration.ZERO));

        MediaPlayer gameMedia = new MediaPlayer(new Media(new File("src/resource/Game.m4a").toURI().toString()));
        gameMedia.setOnEndOfMedia(() -> gameMedia.seek(Duration.ZERO));

        homeMedia.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/home.fxml"));
        stage.setScene(new Scene(loader.load()));/*
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);*/
        stage.initStyle(StageStyle.UNDECORATED);
        HomeController homeController = (HomeController) (loader.getController());
        homeController.initiate(homeMedia, gameMedia);
        stage.show();
        homeController.initiate(stage.getWidth(), stage.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
