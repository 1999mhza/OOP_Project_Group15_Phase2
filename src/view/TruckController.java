package view;

import controller.ResultType;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.wild.Wild;
import model.animal.wild.WildList;
import model.good.Good;
import model.good.GoodList;

import java.util.HashSet;

public class TruckController {
    public ImageView image;
    public Label result;

    public HBox hd1, hd2;
    public HBox hw1, hw2;
    public HBox hg1, hg2, hg3, hg4, hg5, hg6, hg7, hg8;

    public HBox[] hds, hgs, hws;
    public Button cancel;
    public Button ok;

    public Label hg1n, hg2n, hg3n, hg4n, hg5n, hg6n, hg7n, hg8n;
    public Label hg1p, hg2p, hg3p, hg4p, hg5p, hg6p, hg7p, hg8p;
    public Label hw1n, hw2n;
    public Label hw1p, hw2p;
    public Label hd1n, hd2n;
    public Label hd1p, hd2p;
    public Label[] hgns, hgps, hwns, hwps, hdps, hdns;

    public String[] gns, dns, wns;

    private FadeTransition fadeTransition;

    public void initiate(double width, double height) {
        double s = Math.min(height / image.getImage().getHeight(), width / image.getImage().getWidth());
        image.setFitHeight(s * image.getImage().getHeight());
        image.setFitWidth(s * image.getImage().getWidth());
        image.setX((width - image.getFitWidth()) / 2);
        image.setY((height - image.getFitHeight()) / 2);

        ImageView labelBack = (ImageView) result.getGraphic();
        labelBack.setFitWidth(s * labelBack.getFitWidth());
        labelBack.setFitHeight(s * labelBack.getFitHeight());

        result.setLayoutX(s * (result.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        result.setLayoutY(s * (result.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        result.setPrefWidth(s * result.getPrefWidth());
        result.setPrefHeight(s * result.getPrefHeight());
        result.setVisible(false);

        fadeTransition = new FadeTransition(Duration.seconds(1), result);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        ok.setLayoutX(width / 2 - 110);

        ok.setOnMousePressed(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        ok.setOnMouseReleased(event -> {
            if (ok.isHover()) ok.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        ok.setOnMouseEntered(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        ok.setOnMouseExited(event -> ok.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));

        cancel.setLayoutX(width / 2 + 110);

        cancel.setOnMousePressed(event -> cancel.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        cancel.setOnMouseReleased(event -> {
            if (cancel.isHover()) cancel.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        cancel.setOnMouseEntered(event -> cancel.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        cancel.setOnMouseExited(event -> cancel.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        cancel.setOnAction(event -> {
            Game game = Game.getInstance();

            for (Animal animal : game.getTruck().getAnimals()) {
                if (animal instanceof Wild) {
                    game.getWarehouse().addWild((Wild) animal);
                } else if (animal instanceof Domestic) {
                    game.addDomestic((Domestic) animal);
                }
            }
            for (Good good : game.getTruck().getGoods()) {
                game.getWarehouse().addGood(good);
            }
            game.getTruck().clear();
            game.play();
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setResizable(false);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

        hds = new HBox[]{hd1, hd2};
        hws = new HBox[]{hw1, hw2};
        hgs = new HBox[]{hg1, hg2, hg3, hg4, hg5, hg6, hg7, hg8};

        hgps = new Label[]{hg1p, hg2p, hg3p, hg4p, hg5p, hg6p, hg7p, hg8p};
        hgns = new Label[]{hg1n, hg2n, hg3n, hg4n, hg5n, hg6n, hg7n, hg8n};
        hwps = new Label[]{hw1p, hw2p};
        hwns = new Label[]{hw1n, hw2n};
        hdps = new Label[]{hd1p, hd2p};
        hdns = new Label[]{hd1n, hd2n};

        gns = new String[]{"Egg", "Flour", "Cookie", "Cake", "Wool", "Fiber", "Fabric", "Dress"};
        dns = new String[]{"Chicken", "Sheep"};
        wns = new String[]{"Lion", "Bear"};

        for (HBox hd : hds) {
            hd.setLayoutX(s * (hd.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
            hd.setLayoutY(s * (hd.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
            hd.setPrefWidth(s * hd.getPrefWidth());
            hd.setPrefHeight(s * hd.getPrefHeight());
            for (Node node : hd.getChildren()) {
                node.setLayoutX(s * node.getLayoutX());
                node.setLayoutY(s * node.getLayoutY());
                if (node instanceof Button) {
                    node.setOnMousePressed(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: lightskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseReleased(event -> {
                        if (node.isHover()) node.setStyle("""
                                -fx-background-radius: 5;
                                -fx-background-color: dodgerblue;
                                -fx-border-color: darkblue;
                                -fx-border-width: 1;
                                -fx-border-radius: 5;
                                -fx-font-size: 15;
                                -fx-font-weight: bold""");
                    });
                    node.setOnMouseEntered(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: dodgerblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseExited(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: deepskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    ((Button) node).setPrefWidth(s * ((Button) node).getPrefWidth());
                    ((Button) node).setPrefHeight(s * ((Button) node).getPrefHeight());
                } else if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(s * ((ImageView) node).getFitWidth());
                    ((ImageView) node).setFitHeight(s * ((ImageView) node).getFitHeight());
                } else if (node instanceof Label) {
                    ((Label) node).setPrefWidth(s * ((Label) node).getPrefWidth());
                    ((Label) node).setPrefHeight(s * ((Label) node).getPrefHeight());
                }
            }
        }
        for (HBox hw : hws) {
            hw.setLayoutX(s * (hw.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
            hw.setLayoutY(s * (hw.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
            hw.setPrefWidth(s * hw.getPrefWidth());
            hw.setPrefHeight(s * hw.getPrefHeight());
            for (Node node : hw.getChildren()) {
                node.setLayoutX(s * node.getLayoutX());
                node.setLayoutY(s * node.getLayoutY());
                if (node instanceof Button) {
                    node.setOnMousePressed(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: lightskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseReleased(event -> {
                        if (node.isHover()) node.setStyle("""
                                -fx-background-radius: 5;
                                -fx-background-color: dodgerblue;
                                -fx-border-color: darkblue;
                                -fx-border-width: 1;
                                -fx-border-radius: 5;
                                -fx-font-size: 15;
                                -fx-font-weight: bold""");
                    });
                    node.setOnMouseEntered(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: dodgerblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseExited(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: deepskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    ((Button) node).setPrefWidth(s * ((Button) node).getPrefWidth());
                    ((Button) node).setPrefHeight(s * ((Button) node).getPrefHeight());
                } else if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(s * ((ImageView) node).getFitWidth());
                    ((ImageView) node).setFitHeight(s * ((ImageView) node).getFitHeight());
                } else if (node instanceof Label) {
                    ((Label) node).setPrefWidth(s * ((Label) node).getPrefWidth());
                    ((Label) node).setPrefHeight(s * ((Label) node).getPrefHeight());
                }
            }
        }
        for (HBox hg : hgs) {
            hg.setLayoutX(s * (hg.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
            hg.setLayoutY(s * (hg.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
            hg.setPrefWidth(s * hg.getPrefWidth());
            hg.setPrefHeight(s * hg.getPrefHeight());
            for (Node node : hg.getChildren()) {
                node.setLayoutX(s * node.getLayoutX());
                node.setLayoutY(s * node.getLayoutY());
                if (node instanceof Button) {
                    node.setOnMousePressed(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: lightskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseReleased(event -> {
                        if (node.isHover()) node.setStyle("""
                                -fx-background-radius: 5;
                                -fx-background-color: dodgerblue;
                                -fx-border-color: darkblue;
                                -fx-border-width: 1;
                                -fx-border-radius: 5;
                                -fx-font-size: 15;
                                -fx-font-weight: bold""");
                    });
                    node.setOnMouseEntered(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: dodgerblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setOnMouseExited(event -> node.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: deepskyblue;
                            -fx-border-color: darkblue;
                            -fx-border-width: 1;
                            -fx-border-radius: 5;
                            -fx-font-size: 15;
                            -fx-font-weight: bold"""));
                    node.setLayoutX(s * node.getLayoutX());
                    node.setLayoutY(s * node.getLayoutY());
                    ((Button) node).setPrefWidth(s * ((Button) node).getPrefWidth());
                    ((Button) node).setPrefHeight(s * ((Button) node).getPrefHeight());
                } else if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(s * ((ImageView) node).getFitWidth());
                    ((ImageView) node).setFitHeight(s * ((ImageView) node).getFitHeight());
                } else if (node instanceof Label) {
                    ((Label) node).setPrefWidth(s * ((Label) node).getPrefWidth());
                    ((Label) node).setPrefHeight(s * ((Label) node).getPrefHeight());
                }
            }
        }

        format();

        Game game = Game.getInstance();
        for (int i = 0; i < hds.length; i++) {
            for (Node child : hds[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        DomesticList domesticList = DomesticList.getDomestic(dns[finalI]);
                        int space = game.getDomesticSpace(domesticList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (game.getTruck().getCapacity() < space) {
                            setResult("Not Enough Space In Truck!");
                        } else {
                            game.getTruck().addAnimal(game.getDomestic(domesticList.getClassName()));
                            game.updateTask(domesticList.getClassName(), false);
                            format();
                        }
                    });
                }
            }
        }

        for (int i = 0; i < hws.length; i++) {
            for (Node child : hws[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        WildList wildList = WildList.getWild(wns[finalI]);
                        int space = game.getWarehouse().getWildSpace(wildList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (game.getTruck().getCapacity() < space) {
                            setResult("Not Enough Space In Truck!");
                        } else {
                            game.getTruck().addAnimal(game.getWarehouse().getWild(wildList.getClassName()));
                            format();
                        }
                    });
                }
            }
        }

        for (int i = 0; i < hgs.length; i++) {
            for (Node child : hgs[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        GoodList goodList = GoodList.getGood(gns[finalI]);
                        int space = game.getWarehouse().getGoodSpace(goodList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (game.getTruck().getCapacity() < space) {
                            setResult("Not Enough Space In Truck!");
                        } else {
                            game.getTruck().addGood(game.getWarehouse().getGood(goodList.getClassName()));
                            format();
                        }
                    });
                }
            }
        }
    }

    public void format() {

        Game game = Game.getInstance();
        for (int i = 0; i < hdps.length; i++) {
            hdps[i].setText(String.valueOf(DomesticList.getDomestic(dns[i]).getPrice() / 2));
            hdns[i].setText("*" + game.getDomesticNumber(dns[i]));
        }

        for (int i = 0; i < hwps.length; i++) {
            hwps[i].setText(String.valueOf(WildList.getWild(wns[i]).getPrice()));
            hwns[i].setText("*" + game.getWildNumber(wns[i]));
        }

        for (int i = 0; i < hgps.length; i++) {
            hgps[i].setText(String.valueOf(GoodList.getGood(gns[i]).getPrice()));
            hgns[i].setText("*" + game.getGoodNumber(gns[i]));
        }
    }

    public void setResult(String s) {
        result.setVisible(true);
        result.setText(s);
        fadeTransition.playFromStart();
    }
}
