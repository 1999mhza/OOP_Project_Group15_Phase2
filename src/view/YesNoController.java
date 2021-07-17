package view;

import controller.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class YesNoController {
    public Button yes;
    public Button no;

    public void initiate() {

        yes.setOnMousePressed(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightgreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        yes.setOnMouseReleased(event -> {
            if (yes.isHover()) yes.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: limegreen;
                    -fx-border-color: darkgreen;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 15;
                    -fx-font-weight: bold""");
        });
        yes.setOnMouseEntered(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: limegreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        yes.setOnMouseExited(event -> yes.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lawngreen;
                -fx-border-color: darkgreen;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        yes.setOnAction(event -> {
            Logger.log("info", "The user closed the game.");
            Platform.exit();
        });

        no.setOnMousePressed(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightpink;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        no.setOnMouseReleased(event -> {
            if (no.isHover()) no.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: indianred;
                    -fx-border-color: darkred;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 15;
                    -fx-font-weight: bold""");
        });
        no.setOnMouseEntered(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: indianred;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        no.setOnMouseExited(event -> no.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: tomato;
                -fx-border-color: darkred;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 15;
                -fx-font-weight: bold"""));
        no.setOnAction(event -> {
            Logger.log("info", "The user went to home.");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
    }
}
