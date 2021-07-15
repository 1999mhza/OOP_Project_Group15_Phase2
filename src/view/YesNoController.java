package view;

import controller.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class YesNoController {
    public StackPane background;
    public Button yes;
    public Button no;
    public Label l;

    public void initiate() {

        Font font = Font.loadFont("file:src/resource/PressStart2P-Regular.ttf", 10);
        l.setFont(font);

        yes.setFont(font);
        yes.setOnMousePressed(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        yes.setOnMouseReleased(event -> {
            if (yes.isHover()) yes.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        yes.setOnMouseEntered(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        yes.setOnMouseExited(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        yes.setOnAction(event -> {
            Logger.log("info", "The user closed the game.");
            Platform.exit();
        });

        no.setFont(font);
        no.setOnMousePressed(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightpink;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        no.setOnMouseReleased(event -> {
            if (no.isHover()) no.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: indianred;
                    -fx-border-color: darkred;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;""");
        });
        no.setOnMouseEntered(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: indianred;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        no.setOnMouseExited(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;"""));
        no.setOnAction(event -> {
            Logger.log("info", "The user went to home.");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
    }
}
