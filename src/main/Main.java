package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Parameter;
import view.HomeController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parameter.initiate();
        Parameter parameter = Parameter.getInstance();

        parameter.setMedia();
        parameter.getHomeMedia().play();

        parameter.setScreenSize();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(), parameter.getWidth(), parameter.getHeight()));
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        ((HomeController) (loader.getController())).initiate();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
