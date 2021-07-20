package view;

import controller.MissionManager;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
import model.*;
import model.animal.collector.Collector;
import model.animal.collector.CollectorList;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.protective.Protective;
import model.animal.protective.ProtectiveList;
import model.animal.wild.WildList;
import model.factory.Factory;
import model.factory.FactoryList;

import java.io.File;
import java.io.IOException;

public class GameController {

    public ImageView image;
    public Button menu;
    public AnchorPane parent;
    public ImageView road;
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

    public HBox hBuy;
    public Button buyChicken;
    public Button buySheep;
    public Button buyCat;
    public Button buyDog;
    public Label priceChicken;
    public Label priceSheep;
    public Label priceCat;
    public Label priceDog;

    public VBox vFactory;
    public ImageView factoryMill;
    public ImageView factoryCookie;
    public ImageView factoryCake;
    public ImageView factorySpin;
    public ImageView factoryWeave;
    public ImageView factorySew;
    public ImageView factoryIncubate;
    public Button buildMill;
    public Button buildCookie;
    public Button buildCake;
    public Button buildSpin;
    public Button buildWeave;
    public Button buildSew;
    public Button buildIncubate;
    public Label labelMill;
    public Label labelCookie;
    public Label labelCake;
    public Label labelSpin;
    public Label labelWeave;
    public Label labelSew;
    public Label labelIncubate;

    public VBox vUpgrade;
    public Button upWell;
    public ImageView imageWell;
    public Label labelWell;
    public Button upWarehouse;
    public ImageView imageWarehouse;
    public Label labelWarehouse;
    public Button upTruck;
    public ImageView imageTruck;
    public Label labelTruck;
    public Button upCage;
    public ImageView imageCage;
    public Label labelCage;

    private String username;
    private int level;
    private MediaPlayer homeMedia;
    private MediaPlayer gameMedia;
    private double width;
    private double height;

    public void initiate(String username, int level, MediaPlayer homeMedia, MediaPlayer gameMedia, MediaPlayer winMedia, double width, double height) {

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
            if (mouseEvent.getPickResult().getIntersectedNode() instanceof AnchorPane) {
                plant(mouseEvent.getX(), mouseEvent.getY());
            } else {
                if (mouseEvent.getPickResult().getIntersectedNode() instanceof ImageView) {
                    ImageView view = (ImageView) mouseEvent.getPickResult().getIntersectedNode();
                    String url = view.getImage().getUrl();
                    String[] split = url.split("/");
                    String name = split[split.length - 2];

                    if (!name.equals("Product") && !name.equals("Cage") && WildList.getWild(name) == null)
                        plant(mouseEvent.getX(), mouseEvent.getY());
                }
            }
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
        result.setPrefWidth(s * result.getPrefWidth());
        result.setPrefHeight(s * result.getPrefHeight());
        result.setVisible(false);

        Label[] labels = new Label[]{labelTask1, labelTask2, labelTask3, labelTask4};
        Label[] labels1 = new Label[]{labelTask11, labelTask21, labelTask31, labelTask41};
        ImageView[] imageViews = new ImageView[]{imageTask1, imageTask2, imageTask3, imageTask4};

        road.setLayoutX((width + image.getFitWidth()) / 2 - 5 - road.getFitWidth());
        h.setLayoutX((width + image.getFitWidth()) / 2 - 5 - h.getPrefWidth());

        HBox hBox = (HBox) time.getParent();
        hBox.setLayoutX((width + image.getFitWidth()) / 2 - 5 - hBox.getPrefWidth());

        Game.initiateGame(width, height, homeMedia, gameMedia, winMedia, level, username, parent.getPrefWidth(), parent.getPrefHeight(), parent, (AnchorPane) (parent.getParent()), s, image.getImage().getWidth() / 2, image.getImage().getHeight() / 2, width / 2, height / 2, result, coin, time, labels, labels1, imageViews, road);
        Game game = Game.getInstance();
        game.initiate();

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
            game.pause();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/pause_dialog.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            ((PauseController) (loader.getController())).initiate(username, homeMedia, gameMedia, winMedia, width, height);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        });

        int maxTime = (int) Math.round(MissionManager.getInstance().getMaxPrizeTime(level));
        prizeTime.setText(String.format("%02d:%02d", maxTime / 60, maxTime % 60));

        hBuy.setLayoutX((width - image.getFitWidth()) / 2 + 5);

        DomesticList domesticListChicken = DomesticList.getDomestic("Chicken");
        priceChicken.setText(String.valueOf(domesticListChicken.getPrice()));
        buyChicken.setOnMousePressed(event -> buyChicken.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lightgreen;"""));
        buyChicken.setOnMouseReleased(event -> {
            if (buyChicken.isHover()) buyChicken.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: limegreen;""");
        });
        buyChicken.setOnMouseEntered(event -> buyChicken.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: limegreen;"""));
        buyChicken.setOnMouseExited(event -> buyChicken.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lawngreen;"""));
        buyChicken.setOnAction(event -> {
            if (domesticListChicken.getPrice() > game.getCoin()) {
                game.setResult("Not Enough Coin!");
                return;
            }
            try {
                game.decreaseCoin(domesticListChicken.getPrice());
                Domestic domestic = (Domestic) Class.forName(domesticListChicken.getPackageName()).newInstance();
                domestic.setAnimation();
                domestic.play();
                Game.getInstance().getDomesticAnimals().add(domestic);
                Game.getInstance().updateTask(domestic.getClass().getSimpleName(), true);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        });

        DomesticList domesticListSheep = DomesticList.getDomestic("Sheep");
        priceSheep.setText(String.valueOf(domesticListSheep.getPrice()));
        buySheep.setOnMousePressed(event -> buySheep.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lightgreen;"""));
        buySheep.setOnMouseReleased(event -> {
            if (buySheep.isHover()) buySheep.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: limegreen;""");
        });
        buySheep.setOnMouseEntered(event -> buySheep.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: limegreen;"""));
        buySheep.setOnMouseExited(event -> buySheep.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lawngreen;"""));
        buySheep.setOnAction(event -> {
            if (domesticListSheep.getPrice() > game.getCoin()) {
                game.setResult("Not Enough Coin!");
                return;
            }
            try {
                game.decreaseCoin(domesticListSheep.getPrice());
                Domestic domestic = (Domestic) Class.forName(domesticListSheep.getPackageName()).newInstance();
                domestic.setAnimation();
                domestic.play();
                Game.getInstance().getDomesticAnimals().add(domestic);
                Game.getInstance().updateTask(domestic.getClass().getSimpleName(), true);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        });

        CollectorList collectorList = CollectorList.getCollector("Cat");
        priceCat.setText(String.valueOf(collectorList.getPrice()));
        buyCat.setOnMousePressed(event -> buyCat.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lightgreen;"""));
        buyCat.setOnMouseReleased(event -> {
            if (buyCat.isHover()) buyCat.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: limegreen;""");
        });
        buyCat.setOnMouseEntered(event -> buyCat.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: limegreen;"""));
        buyCat.setOnMouseExited(event -> buyCat.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lawngreen;"""));
        buyCat.setOnAction(event -> {
            if (collectorList.getPrice() > game.getCoin()) {
                game.setResult("Not Enough Coin!");
                return;
            }
            try {
                game.decreaseCoin(collectorList.getPrice());
                Collector collector = (Collector) Class.forName(collectorList.getPackageName()).newInstance();
                collector.setAnimation();
                collector.play();
                Game.getInstance().getCollectorAnimals().add(collector);
                Game.getInstance().updateTask(collector.getClass().getSimpleName(), true);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        });

        ProtectiveList protectiveList = ProtectiveList.getProtective("Dog");
        priceDog.setText(String.valueOf(protectiveList.getPrice()));
        buyDog.setOnMousePressed(event -> buyDog.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lightgreen;"""));
        buyDog.setOnMouseReleased(event -> {
            if (buyDog.isHover()) buyDog.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: limegreen;""");
        });
        buyDog.setOnMouseEntered(event -> buyDog.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: limegreen;"""));
        buyDog.setOnMouseExited(event -> buyDog.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lawngreen;"""));
        buyDog.setOnAction(event -> {
            if (protectiveList.getPrice() > game.getCoin()) {
                game.setResult("Not Enough Coin!");
                return;
            }
            try {
                game.decreaseCoin(protectiveList.getPrice());
                Protective protective = (Protective) Class.forName(protectiveList.getPackageName()).newInstance();
                protective.setAnimation();
                protective.play();
                Game.getInstance().getProtectiveAnimals().add(protective);
                Game.getInstance().updateTask(protective.getClass().getSimpleName(), true);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        });

        vUpgrade.setLayoutX((width + image.getFitWidth()) / 2 - vUpgrade.getPrefWidth() - 5);
        setWell();
        setWarehouse();
        setTruck();
        setCage();

        vFactory.setLayoutX((width - image.getFitWidth()) / 2 + 5);
        setFactory("Mill", buildMill, labelMill, factoryMill);
        setFactory("CookieBakery", buildCookie, labelCookie, factoryCookie);
        setFactory("CakeBakery", buildCake, labelCake, factoryCake);
        setFactory("Spinnery", buildSpin, labelSpin, factorySpin);
        setFactory("WeavingFactory", buildWeave, labelWeave, factoryWeave);
        setFactory("SewingFactory", buildSew, labelSew, factorySew);
        setFactory("Incubator", buildIncubate, labelIncubate, factoryIncubate);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/task_list.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        ((TaskController) (loader.getController())).initiate();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(parent.getScene().

                getWindow());
        stage.show();
    }

    private void setFactory(String name, Button button, Label price, ImageView imageView) {

        Game game = Game.getInstance();

        button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lightpink;"""));
        button.setOnMouseReleased(event -> {
            if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: indianred;""");
        });
        button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: indianred;"""));
        button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: tomato;"""));
        button.setOnAction(event -> {
            FactoryList factoryList = FactoryList.getFactory(name);
            for (Factory factory : game.getFactories()) {
                if (factory.getClass().getSimpleName().equalsIgnoreCase(factoryList.getClassName())) {
                    if (factory.isWorking()) {
                        game.setResult("Factory Is Working!");
                        return;
                    } else if (factory.getUpgradePrice() > game.getCoin()) {
                        game.setResult("Not Enough Coin");
                        return;
                    } else {
                        game.decreaseCoin(factory.getUpgradePrice());
                        factory.upgrade();
                        if (!factory.checkFinalLevel()) {
                            price.setText(String.valueOf(factory.getUpgradePrice()));
                            Image image = new Image(new File("src/resource/" + factoryList.getClassName() + "/" + (factory.getLevel() + 1) + ".png").toURI().toString());
                            imageView.setImage(image);
                            imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));
                        } else {
                            ((VBox) price.getParent()).setVisible(false);
                            game.setResult("Last Level Factory!");
                        }
                        return;
                    }
                }
            }
            if (factoryList.getPrice() > game.getCoin()) {
                game.setResult("Not Enough Coin");
            } else {
                try {
                    Factory factory = (Factory) Class.forName(factoryList.getPackageName()).newInstance();
                    game.getFactories().add(factory);
                    price.setText(String.valueOf(factory.getUpgradePrice()));
                    Image image = new Image(new File("src/resource/" + factoryList.getClassName() + "/" + (factory.getLevel() + 1) + ".png").toURI().toString());
                    imageView.setImage(image);
                    imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));

                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        });

        FactoryList factoryList = FactoryList.getFactory(name);
        for (Factory factory : game.getFactories()) {
            if (factory.getClass().getSimpleName().equalsIgnoreCase(factoryList.getClassName())) {
                price.setText(String.valueOf(factory.getUpgradePrice()));
                Image image = new Image(new File("src/resource/" + factoryList.getClassName() + "/" + (factory.getLevel() + 1) + ".png").toURI().toString());
                imageView.setImage(image);
                imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));
                return;
            }
        }
        price.setText(String.valueOf(factoryList.getPrice()));
        Image image = new Image(new File("src/resource/" + factoryList.getClassName() + "/1.png").toURI().toString());
        imageView.setImage(image);
        imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));
    }

    private void setWell() {
        Button button = upWell;
        Label price = labelWell;
        ImageView imageView = imageWell;

        Game game = Game.getInstance();
        Well well = game.getWell();

        button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lavender;"""));
        button.setOnMouseReleased(event -> {
            if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: mediumpurple;""");
        });
        button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: mediumpurple;"""));
        button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: plum;"""));
        button.setOnAction(event -> {
            if (well.isWorking()) {
                game.setResult("Well Is Working!");
            } else if (well.getUpgradePrice() > game.getCoin()) {
                game.setResult("Not Enough Coin");
            } else {
                game.decreaseCoin(well.getUpgradePrice());
                well.upgrade();
                if (!well.checkFinalLevel()) {
                    price.setText(String.valueOf(well.getUpgradePrice()));
                    Image image = new Image(new File("src/resource/Well/" + (well.getLevel() + 1) + ".png").toURI().toString());
                    imageView.setImage(image);
                    imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));
                } else {
                    ((VBox) price.getParent()).setVisible(false);
                    game.setResult("Last Level Well!");
                }
            }
        });

        price.setText(String.valueOf(well.getUpgradePrice()));
        Image image = new Image(new File("src/resource/Well/" + (well.getLevel() + 1) + ".png").toURI().toString());
        imageView.setImage(image);
        imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 4, image.getHeight() / 4));
    }

    private void setWarehouse() {
        Button button = upWarehouse;
        Label price = labelWarehouse;
        ImageView imageView = imageWarehouse;

        Game game = Game.getInstance();
        Warehouse warehouse = game.getWarehouse();

        button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lavender;"""));
        button.setOnMouseReleased(event -> {
            if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: mediumpurple;""");
        });
        button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: mediumpurple;"""));
        button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: plum;"""));
        button.setOnAction(event -> {
            if (warehouse.getUpgradePrice() > game.getCoin()) {
                game.setResult("Not Enough Coin");
            } else {
                game.decreaseCoin(warehouse.getUpgradePrice());
                warehouse.upgrade();
                if (!warehouse.checkFinalLevel()) {
                    price.setText(String.valueOf(warehouse.getUpgradePrice()));
                    Image image = new Image(new File("src/resource/Warehouse/" + (warehouse.getLevel() + 1) + ".png").toURI().toString());
                    imageView.setImage(image);
                } else {
                    ((VBox) price.getParent()).setVisible(false);
                    game.setResult("Last Level Warehouse!");
                }
            }
        });

        price.setText(String.valueOf(warehouse.getUpgradePrice()));
        Image image = new Image(new File("src/resource/Warehouse/" + (warehouse.getLevel() + 1) + ".png").toURI().toString());
        imageView.setImage(image);
    }

    private void setTruck() {
        Button button = upTruck;
        Label price = labelTruck;
        ImageView imageView = imageTruck;

        Game game = Game.getInstance();
        Truck truck = game.getTruck();

        button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lavender;"""));
        button.setOnMouseReleased(event -> {
            if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: mediumpurple;""");
        });
        button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: mediumpurple;"""));
        button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: plum;"""));
        button.setOnAction(event -> {
            if (truck.isWorking()) {
                game.setResult("Truck Is Working!");
            } else if (truck.getUpgradePrice() > game.getCoin()) {
                game.setResult("Not Enough Coin");
            } else {
                game.decreaseCoin(truck.getUpgradePrice());
                truck.upgrade();
                if (!truck.checkFinalLevel()) {
                    price.setText(String.valueOf(truck.getUpgradePrice()));
                    Image image = new Image(new File("src/resource/Truck/" + (truck.getLevel() + 1) + "_front.png").toURI().toString());
                    imageView.setImage(image);
                } else {
                    ((VBox) price.getParent()).setVisible(false);
                    game.setResult("Last Level Truck!");
                }
            }
        });

        price.setText(String.valueOf(truck.getUpgradePrice()));
        Image image = new Image(new File("src/resource/Truck/" + (truck.getLevel() + 1) + "_front.png").toURI().toString());
        imageView.setImage(image);
    }

    private void setCage() {
        Button button = upCage;
        Label price = labelCage;
        ImageView imageView = imageCage;

        Game game = Game.getInstance();
        Cage cage = game.getCage();

        button.setOnMousePressed(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: lavender;"""));
        button.setOnMouseReleased(event -> {
            if (button.isHover()) button.setStyle("""
                    -fx-background-radius: 20;
                    -fx-background-color: mediumpurple;""");
        });
        button.setOnMouseEntered(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: mediumpurple;"""));
        button.setOnMouseExited(event -> button.setStyle("""
                -fx-background-radius: 20;
                -fx-background-color: plum;"""));
        button.setOnAction(event -> {
            if (cage.getUpgradePrice() > game.getCoin()) {
                game.setResult("Not Enough Coin");
            } else {
                game.decreaseCoin(cage.getUpgradePrice());
                cage.upgrade();
                if (!cage.checkFinalLevel()) {
                    price.setText(String.valueOf(cage.getUpgradePrice()));
                    Image image = new Image(new File("src/resource/Cage/" + (cage.getLevel() + 1) + "_break.png").toURI().toString());
                    imageView.setImage(image);
                    imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 5, image.getHeight() / 5));
                } else {
                    ((VBox) price.getParent()).setVisible(false);
                    game.setResult("Last Level Cage!");
                }
            }
        });

        price.setText(String.valueOf(cage.getUpgradePrice()));
        Image image = new Image(new File("src/resource/Cage/" + (cage.getLevel() + 1) + "_break.png").toURI().toString());
        imageView.setImage(image);
        imageView.setViewport(new Rectangle2D(0, 0, image.getWidth() / 5, image.getHeight() / 5));
    }

    private void plant(double x, double y) {
        Game game = Game.getInstance();
        if (x < 0 || y < 0 || x > game.getWidth() || y > game.getHeight()) {
            game.setResult("Out Of Bounds!");
        } else if (game.getWell().isWorking()) {
            game.setResult("Well Is Working!");
        } else if (game.getWell().getWater() <= 0.9) {
            game.setResult("No Water!");
        } else {
            game.getWell().decreaseWater();
            game.plant(x, y);
        }
    }
}
