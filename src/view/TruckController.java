package view;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game;
import model.Truck;
import model.Warehouse;
import model.animal.Animal;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.wild.Wild;
import model.animal.wild.WildList;
import model.good.Good;
import model.good.GoodList;

import java.io.File;

public class TruckController {
    private Truck truck;
    private Warehouse warehouse;
    private ProgressBar progressBar;

    public Label title1, title2, title3;

    public ImageView image;
    public Label result;

    public HBox hd1, hd2;
    public HBox hw1, hw2;
    public HBox hg1, hg2, hg3, hg4, hg5, hg6, hg7, hg8;

    public HBox hd11, hd21;
    public HBox hw11, hw21;
    public HBox hg11, hg21, hg31, hg41, hg51, hg61, hg71, hg81;

    public HBox[] hds, hgs, hws;
    public HBox[] hds1, hgs1, hws1;

    public Button cancel;
    public Button go;

    public Label hg1n, hg2n, hg3n, hg4n, hg5n, hg6n, hg7n, hg8n;
    public Label hg1p, hg2p, hg3p, hg4p, hg5p, hg6p, hg7p, hg8p;
    public Label hw1n, hw2n;
    public Label hw1p, hw2p;
    public Label hd1n, hd2n;
    public Label hd1p, hd2p;
    public Label[] hgns, hgps, hwns, hwps, hdps, hdns;

    public Label hg1n1, hg2n1, hg3n1, hg4n1, hg5n1, hg6n1, hg7n1, hg8n1;
    public Label hg1p1, hg2p1, hg3p1, hg4p1, hg5p1, hg6p1, hg7p1, hg8p1;
    public Label hw1n1, hw2n1;
    public Label hw1p1, hw2p1;
    public Label hd1n1, hd2n1;
    public Label hd1p1, hd2p1;
    public Label[] hgns1, hgps1, hwns1, hwps1, hdps1, hdns1;

    public String[] gns, dns, wns;
    public Label total;
    public HBox hTotal;

    public ImageView back;

    private int row, column;
    private double length;
    public ImageView[][] imageViews;

    private FadeTransition fadeTransition;

    public void initiate(double width, double height) {
        truck = Game.getInstance().getTruck();
        warehouse = Game.getInstance().getWarehouse();

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

        back.setLayoutX(s * (back.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        back.setLayoutY(s * (back.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        back.setFitWidth(s * back.getFitWidth());
        back.setFitHeight(s * back.getFitHeight());

        progressBar = new ProgressBar();

        progressBar.setPrefWidth(100);
        progressBar.setPrefHeight(10);

        progressBar.setLayoutX(back.getLayoutX() + back.getFitWidth() / 2 - progressBar.getPrefWidth() / 2);
        progressBar.setLayoutY(back.getLayoutY() + back.getFitHeight() - 50);

        double p = 1 - 1.0 * truck.getCapacity() / truck.getFullCapacity();
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        progressBar.setProgress(p);

        ((AnchorPane) (back.getParent())).getChildren().add(progressBar);

        title1.setLayoutX(s * (title1.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        title1.setLayoutY(s * (title1.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        title1.setPrefWidth(s * title1.getPrefWidth());
        title1.setPrefHeight(s * title1.getPrefHeight());

        title2.setLayoutX(s * (title2.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        title2.setLayoutY(s * (title2.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        title2.setPrefWidth(s * title2.getPrefWidth());
        title2.setPrefHeight(s * title2.getPrefHeight());

        title3.setLayoutX(s * (title3.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        title3.setLayoutY(s * (title3.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        title3.setPrefWidth(s * title3.getPrefWidth());
        title3.setPrefHeight(s * title3.getPrefHeight());

        fadeTransition = new FadeTransition(Duration.seconds(1), result);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        go.setLayoutX(width / 2 - 110);

        go.setOnMousePressed(event -> go.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: lightskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        go.setOnMouseReleased(event -> {
            if (go.isHover()) go.setStyle("""
                    -fx-background-radius: 50;
                    -fx-background-color: dodgerblue;
                    -fx-border-color: darkblue;
                    -fx-border-width: 5;
                    -fx-border-radius: 50;
                    -fx-font-size: 19;
                    -fx-font-weight: bold""");
        });
        go.setOnMouseEntered(event -> go.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: dodgerblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        go.setOnMouseExited(event -> go.setStyle("""
                -fx-background-radius: 50;
                -fx-background-color: deepskyblue;
                -fx-border-color: darkblue;
                -fx-border-width: 5;
                -fx-border-radius: 50;
                -fx-font-size: 19;
                -fx-font-weight: bold"""));
        go.setOnAction(event -> {
            if (truck.isEmpty()) {
                setResult("Truck Is Empty!");
                return;
            }
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setResizable(false);
            ((Node) (event.getSource())).getScene().getWindow().hide();
            truck.go();
            Game.getInstance().play();
        });

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

            for (Animal animal : truck.getAnimals()) {
                if (animal instanceof Wild) {
                    warehouse.addWild((Wild) animal);
                } else if (animal instanceof Domestic) {
                    game.addDomestic((Domestic) animal);
                }
            }
            for (Good good : truck.getGoods()) {
                game.getWarehouse().addGood(good);
            }
            truck.clear();
            game.play();
            Stage stage = (Stage) (((Stage) (((Node) (event.getSource())).getScene().getWindow())).getOwner());
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setResizable(false);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

        hTotal.setLayoutX(s * (hTotal.getLayoutX() - image.getImage().getWidth() / 2) + width / 2);
        hTotal.setLayoutY(s * (hTotal.getLayoutY() - image.getImage().getHeight() / 2) + height / 2);
        hTotal.setPrefWidth(s * hTotal.getPrefWidth());
        hTotal.setPrefHeight(s * hTotal.getPrefHeight());

        for (Node child : hTotal.getChildren()) {
            if (child instanceof ImageView) {
                ((ImageView) child).setFitWidth(s * ((ImageView) child).getFitWidth());
                ((ImageView) child).setFitHeight(s * ((ImageView) child).getFitHeight());
            } else if (child instanceof Label) {
                ((Label) child).setPrefWidth(s * ((Label) child).getPrefWidth());
                ((Label) child).setPrefHeight(s * ((Label) child).getPrefHeight());
            }
        }

        hds = new HBox[]{hd1, hd2};
        hws = new HBox[]{hw1, hw2};
        hgs = new HBox[]{hg1, hg2, hg3, hg4, hg5, hg6, hg7, hg8};

        hds1 = new HBox[]{hd11, hd21};
        hws1 = new HBox[]{hw11, hw21};
        hgs1 = new HBox[]{hg11, hg21, hg31, hg41, hg51, hg61, hg71, hg81};

        hgps = new Label[]{hg1p, hg2p, hg3p, hg4p, hg5p, hg6p, hg7p, hg8p};
        hgns = new Label[]{hg1n, hg2n, hg3n, hg4n, hg5n, hg6n, hg7n, hg8n};
        hwps = new Label[]{hw1p, hw2p};
        hwns = new Label[]{hw1n, hw2n};
        hdps = new Label[]{hd1p, hd2p};
        hdns = new Label[]{hd1n, hd2n};

        hgps1 = new Label[]{hg1p1, hg2p1, hg3p1, hg4p1, hg5p1, hg6p1, hg7p1, hg8p1};
        hgns1 = new Label[]{hg1n1, hg2n1, hg3n1, hg4n1, hg5n1, hg6n1, hg7n1, hg8n1};
        hwps1 = new Label[]{hw1p1, hw2p1};
        hwns1 = new Label[]{hw1n1, hw2n1};
        hdps1 = new Label[]{hd1p1, hd2p1};
        hdns1 = new Label[]{hd1n1, hd2n1};

        gns = new String[]{"Egg", "Flour", "Cookie", "Cake", "Wool", "Fiber", "Fabric", "Dress"};
        dns = new String[]{"Chicken", "Sheep"};
        wns = new String[]{"Lion", "Bear"};

        setHBox(hds, s, width, height);
        setHBox(hds1, s, width, height);
        setHBox(hws, s, width, height);
        setHBox(hws1, s, width, height);
        setHBox(hgs, s, width, height);
        setHBox(hgs1, s, width, height);

        Game game = Game.getInstance();

        row = 5;
        column = 10;
        length = 12;
        length *= 0.9 * s;

        imageViews = new ImageView[row][column];

        for (int i = 0; i < row; i++) {
            imageViews[i] = new ImageView[column];
            for (int j = 0; j < column; j++) {
                imageViews[i][j] = new ImageView();
                imageViews[i][j].setFitWidth(length);
                imageViews[i][j].setFitHeight(length);
            }
        }

        double biasX = (back.getFitWidth() - column * length) / 2 - 30;
        double biasY = 135;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                ((AnchorPane) (back.getParent())).getChildren().add(imageViews[i][j]);
                imageViews[i][j].setLayoutX(back.getLayoutX() + biasX + j * length);
                imageViews[i][j].setLayoutY(back.getLayoutY() + biasY + (row - 1 - i) * length);
            }
        }

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
                        if (truck.getCapacity() < space) {
                            setResult("Not Enough Capacity!");
                        } else {
                            truck.addAnimal(game.getDomestic(domesticList.getClassName()));
                            game.updateTask(domesticList.getClassName(), false);
                            format();
                        }
                    });
                }
            }
        }
        for (int i = 0; i < hds1.length; i++) {
            for (Node child : hds1[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        DomesticList domesticList = DomesticList.getDomestic(dns[finalI]);
                        int space = truck.getAnimalSpace(domesticList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        game.addDomestic((Domestic) truck.getAnimal(domesticList.getClassName()));
                        format();
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
                        int space = warehouse.getWildSpace(wildList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (truck.getCapacity() < space) {
                            setResult("Not Enough Capacity!");
                        } else {
                            truck.addAnimal(warehouse.getWild(wildList.getClassName()));
                            format();
                        }
                    });
                }
            }
        }
        for (int i = 0; i < hws1.length; i++) {
            for (Node child : hws1[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        WildList wildList = WildList.getWild(wns[finalI]);
                        int space = truck.getAnimalSpace(wildList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (warehouse.getCapacity() < space) {
                            setResult("Not Enough Capacity!");
                        } else {
                            warehouse.addWild((Wild) truck.getAnimal(wildList.getClassName()));
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
                        int space = warehouse.getGoodSpace(goodList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (truck.getCapacity() < space) {
                            setResult("Not Enough Capacity!");
                        } else {
                            truck.addGood(warehouse.getGood(goodList.getClassName()));
                            format();
                        }
                    });
                }
            }
        }
        for (int i = 0; i < hgs1.length; i++) {
            for (Node child : hgs1[i].getChildren()) {
                if (child instanceof Button) {
                    int finalI = i;
                    ((Button) child).setOnAction(event -> {
                        GoodList goodList = GoodList.getGood(gns[finalI]);
                        int space = truck.getGoodSpace(goodList.getClassName());
                        if (space == -1) {
                            setResult("Not Enough Object!");
                            return;
                        }
                        if (warehouse.getCapacity() < space) {
                            setResult("Not Enough Capacity!");
                        } else {
                            warehouse.addGood(truck.getGood(goodList.getClassName()));
                            format();
                        }
                    });
                }
            }
        }

        back.setImage(new Image(new File("src/resource/Truck/" + truck.getLevel() + "_back.png").toURI().toString()));

        format();
    }

    public void format() {

        Game game = Game.getInstance();
        for (int i = 0; i < hdps.length; i++) {
            hdps[i].setText(String.valueOf(DomesticList.getDomestic(dns[i]).getPrice() / 2));
            hdns[i].setText("# " + game.getDomesticNumber(dns[i]));
        }

        for (int i = 0; i < hwps.length; i++) {
            hwps[i].setText(String.valueOf(WildList.getWild(wns[i]).getPrice()));
            hwns[i].setText("# " + game.getWildNumber(wns[i]));
        }

        for (int i = 0; i < hgps.length; i++) {
            hgps[i].setText(String.valueOf(GoodList.getGood(gns[i]).getPrice()));
            hgns[i].setText("# " + game.getGoodNumber(gns[i]));
        }

        for (int i = 0; i < hdps1.length; i++) {
            hdps1[i].setText(String.valueOf(DomesticList.getDomestic(dns[i]).getPrice() / 2));
            hdns1[i].setText("# " + truck.getDomesticNumber(dns[i]));
        }

        for (int i = 0; i < hwps1.length; i++) {
            hwps1[i].setText(String.valueOf(WildList.getWild(wns[i]).getPrice()));
            hwns1[i].setText("# " + truck.getWildNumber(wns[i]));
        }

        for (int i = 0; i < hgps1.length; i++) {
            hgps1[i].setText(String.valueOf(GoodList.getGood(gns[i]).getPrice()));
            hgns1[i].setText("# " + truck.getGoodNumber(gns[i]));
        }

        total.setText(String.valueOf(truck.calculatePrice()));

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                imageViews[i][j].setImage(null);
            }
        }

        int num = 0;
        for (Animal animal : truck.getAnimals()) {
            Image image = new Image(new File("src/resource/Product/" + animal.getClass().getSimpleName() + ".png").toURI().toString());
            for (int i = 0; i < animal.getSpace(); i++) {
                imageViews[num / column][num % column].setImage(image);
                num++;
            }
        }
        for (Good good : truck.getGoods()) {
            Image image = new Image(new File("src/resource/Product/" + good.getClass().getSimpleName() + ".png").toURI().toString());
            for (int i = 0; i < good.getSpace(); i++) {
                imageViews[num / column][num % column].setImage(image);
                num++;
            }
        }

        double p = 1 - 1.0 * truck.getCapacity() / truck.getFullCapacity();
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        progressBar.setProgress(p);
    }

    public void setResult(String s) {
        result.setVisible(true);
        result.setText(s);
        fadeTransition.playFromStart();
    }

    private void setHBox(HBox[] hds1, double s, double width, double height) {
        for (HBox hd : hds1) {
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
    }
}
