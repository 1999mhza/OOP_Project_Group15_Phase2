package controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import model.Game;
import model.animal.Animal;
import model.animal.collector.Collector;
import model.animal.collector.CollectorList;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.protective.Protective;
import model.animal.protective.ProtectiveList;
import model.animal.wild.Wild;
import model.animal.wild.WildList;
import model.factory.Factory;
import model.factory.FactoryList;
import model.good.Good;
import model.good.GoodList;

import java.util.*;

public class GameManager {

    private final String username;
    private Game game;

    public GameManager(String username) {
        this.username = username;
    }

    public ResultType truckLoad(String name, int number) {
        if (game.getTruck().isWorking()) return ResultType.BAD_CONDITION;
/*
        WildList wildList = WildList.getWild(name);
        if (wildList != null) {
            int space = game.getWarehouse().getWildSpace(wildList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addAnimal(new HashSet<>(game.getWarehouse().getWild(wildList.getClassName(), number)));
                return ResultType.SUCCESS;
            }
        }

        DomesticList domesticList = DomesticList.getDomestic(name);
        if (domesticList != null) {
            int space = game.getDomesticSpace(domesticList.getClassName());
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addAnimal(game.getDomestic(domesticList.getClassName()));
                for (int i = 0; i < number; i++) {
                    game.updateTask(domesticList.getClassName(), false);
                }
                return ResultType.SUCCESS;
            }
        }
*/
        GoodList goodList = GoodList.getGood(name);
        if (goodList != null) {
            int space = game.getWarehouse().getGoodSpace(goodList.getClassName());
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addGood(game.getWarehouse().getGood(goodList.getClassName()));
                return ResultType.SUCCESS;
            }
        }

        return ResultType.NOT_EXISTED;
    }

    public ResultType truckGo() {
        return game.getTruck().go();
    }

    public boolean truckInUse() {
        return !game.getTruck().isEmpty() && !game.getTruck().isWorking();
    }


}
