package view;

import controller.GameManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
import model.Game;
import model.animal.AnimalAnimation;
import model.animal.domestic.Chicken;

import java.io.IOException;

public class GameController {

    public ImageView image;
    public Button menu;
    public AnchorPane parent;
    public Label result;
    public Label coin;
    public Label time;
    public Label prizeTime;

    public HBox h;
    public ImageView imageTask1;
    public Label labelTask1;
    public Label labelTask11;
    public ImageView imageTask2;
    public Label labelTask2;
    public Label labelTask21;
    public ImageView imageTask3;
    public Label labelTask3;
    public Label labelTask31;
    public ImageView imageTask4;
    public Label labelTask4;
    public Label labelTask41;

    private GameManager gameManager;
    private String username;
    private int level;
    private MediaPlayer homeMedia;
    private MediaPlayer gameMedia;
    private double width;
    private double height;

    public void initiate(String username, int level, MediaPlayer homeMedia, MediaPlayer gameMedia, double width, double height) {

        this.username = username;
        this.level = level;
        this.homeMedia = homeMedia;
        this.gameMedia = gameMedia;
        this.width = width;
        this.height = height;

        double s = Math.min(height / image.getImage().getHeight(), width / image.getImage().getWidth());
        image.setFitHeight(s * image.getImage().getHeight());
        image.setFitWidth(s * image.getImage().getWidth());
        image.setX((width - image.getFitWidth()) / 2);
        image.setY((height - image.getFitHeight()) / 2);

        parent.setOnMouseClicked(mouseEvent -> {

            System.out.println(mouseEvent.getPickResult().getIntersectedNode() instanceof ImageView);
            System.out.println(mouseEvent.getPickResult().getIntersectedNode() instanceof AnchorPane);
        });
        parent.setLayoutX(s * (parent.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        parent.setLayoutY(s * (parent.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        parent.setPrefWidth(s * parent.getPrefWidth());
        parent.setPrefHeight(s * parent.getPrefHeight());

        VBox vBox = (VBox) coin.getParent();
        vBox.setLayoutX(s * (vBox.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);

        ImageView labelBack = (ImageView) result.getGraphic();
        labelBack.setFitWidth(s * labelBack.getFitWidth());
        labelBack.setFitHeight(s * labelBack.getFitHeight());

        result.setLayoutX(s * (result.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        result.setLayoutY(s * (result.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        result.setPrefWidth(s * result.getPrefWidth());
        result.setPrefHeight(s * result.getPrefHeight());
        result.setVisible(false);

        Label[] labels = new Label[]{labelTask1, labelTask2, labelTask3, labelTask4};
        Label[] labels1 = new Label[]{labelTask11, labelTask21, labelTask31, labelTask41};
        ImageView[] imageViews = new ImageView[]{imageTask1, imageTask2, imageTask3, imageTask4};

        h.setLayoutX((width + image.getFitWidth()) / 2 - 5 - h.getPrefWidth());

        HBox hBox = (HBox) time.getParent();
        hBox.setLayoutX((width + image.getFitWidth()) / 2 - 5 - hBox.getPrefWidth());

        menu.setLayoutX((width - image.getFitWidth()) / 2 + 5);

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
            gameManager.pause();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/pause_dialog.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            ((PauseController) (loader.getController())).initiate(username, homeMedia, gameMedia, width, height, gameManager);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });

        gameManager = new GameManager(username);
        gameManager.setGame(level, parent.getPrefWidth(), parent.getPrefHeight(), parent, (AnchorPane) (parent.getParent()), s, image.getImage().getWidth() / 2, image.getImage().getHeight() / 2, width / 2, height / 2, result, coin, time, labels, labels1, imageViews);

        int maxTime = gameManager.getMaxPrizeTime();
        prizeTime.setText(String.format("%02d:%02d", maxTime / 60, maxTime % 60));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/task_list.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        ((TaskController) (loader.getController())).initiate(gameManager);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(parent.getScene().getWindow());
        stage.show();
        //run();
    }

    private void run() {
        gameManager.play();

    }

}
