package view.game;

import controller.GameManager;
import controller.Logger;
import controller.ResultType;
import model.Game;
import view.AbstractMenu;

import java.util.regex.Matcher;

public class Start extends AbstractMenu {

    private final GameManager gameManager;

    public Start(GameManager gameManager, int level) {
        super();
        this.gameManager = gameManager;
        this.gameManager.setGame(level);
        Logger.log("info", "The game level was set!");
        Logger.log("info", "The game started!");
        System.out.println("Game started!");
        System.out.println(gameManager.inquiry());
        System.out.println();
    }

    private void well() {
        Logger.log("info", "The user entered the well command!");
        ResultType result = gameManager.well();
        switch (result) {
            case EXISTED:
                Logger.log("alarm", "Well was working!");
                System.out.println("Well is working now!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There was not enough coin!");
                System.out.println("There is not enough coin!");
                break;

            case BAD_CONDITION:
                Logger.log("alarm", "The water bucket was not empty!");
                System.out.println("The water bucket is not empty!");
                break;

            case SUCCESS:
                Logger.log("info", "The well started working!");
                System.out.println("The well started working!");
                break;
        }
    }

    private void plant(Matcher matcher) {
        Logger.log("info", "The user entered the plant command!");
        ResultType result = gameManager.plant(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                Logger.log("alarm", "The coordinates were invalid!");
                System.out.println("The coordinates are invalid!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There was no water!");
                System.out.println("There is no water!");
                break;

            case SUCCESS:
                Logger.log("info", "Plant was successful!");
                System.out.println("Plant was successful!");
                break;
        }
    }

    private void pickup(Matcher matcher) {
        Logger.log("info", "The user entered the pickup command!");
        ResultType result = gameManager.pickup(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                Logger.log("alarm", "The coordinates were invalid!");
                System.out.println("The coordinates are invalid!");
                break;

            case SUCCESS:
                Logger.log("info", "All objects was picked up!");
                System.out.println("All objects was picked up!");
                break;

            case NOT_EXISTED:
                Logger.log("alarm", "There was nothing to pickup!");
                System.out.println("There is nothing to pickup!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "The warehouse was full! Some of objects were not picked up!");
                System.out.println("The warehouse is full! Some of objects have not been picked up!");
                break;

            case FINISH:
                Logger.log("info", "The game finished!");
                System.out.println(result.getValue());
                break;
        }
    }

    private void buy(Matcher matcher) {
        Logger.log("info", "The user entered the buy command!");
        ResultType result = gameManager.buy(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                Logger.log("alarm", "There was not enough coin!");
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                Logger.log("info", "The animal was bought successfully!");
                System.out.println("The animal was bought successfully!");
                break;

            case NOT_EXISTED:
                Logger.log("alarm", "There was no such animal that can be bought!");
                System.out.println("There is no such animal that can be bought!");
                break;

            case FINISH:
                Logger.log("info", "The game finished!");
                System.out.println(result.getValue());
                break;
        }
    }

    private void build(Matcher matcher) {
        Logger.log("info", "The user entered the build command!");
        ResultType result = gameManager.build(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                Logger.log("alarm", "There was not enough coin!");
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                Logger.log("info", "The factory was built successfully!");
                System.out.println("The factory was built successfully!");
                break;

            case NOT_EXISTED:
                Logger.log("alarm", "There was no such factory that can be built!");
                System.out.println("There is no such factory that can be built!");
                break;

            case EXISTED:
                Logger.log("alarm", "The factory existed!");
                System.out.println("The factory already exists!");
                break;
        }
    }

    private void work(Matcher matcher) {
        Logger.log("info", "The user entered the work command!");
        ResultType result = gameManager.work(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                Logger.log("alarm", "There was no such factory!");
                System.out.println("There is no such factory!");
                break;

            case EXISTED:
                Logger.log("alarm", "The factory was working!");
                System.out.println("The factory is working!");
                break;

            case INVALID_NUMBER:
                Logger.log("alarm", "The number was invalid!");
                System.out.println("The number you entered is invalid!");
                break;

            case SUCCESS:
                Logger.log("info", "The factory started successfully!");
                System.out.println("The factory started successfully!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There were no enough goods in the warehouse!");
                System.out.println("There are no enough goods in the warehouse!");
                break;
        }
    }

    private void cage(Matcher matcher) {
        Logger.log("info", "The user entered the cage command!");
        ResultType result = gameManager.cage(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                Logger.log("alarm", "The coordinates were invalid!");
                System.out.println("The coordinates are invalid!");
                break;

            case SUCCESS:
                Logger.log("info", "The cage was added!");
                System.out.println("The cage was added!");
                break;

            case NOT_EXISTED:
                Logger.log("alarm", "There was nothing to add cage!");
                System.out.println("There is nothing to add cage!");
                break;
        }
    }

    private void truckLoad(Matcher matcher) {
        Logger.log("info", "The user entered the truck load command!");
        ResultType result = gameManager.truckLoad(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                Logger.log("alarm", "There was no such object!");
                System.out.println("There is no such object!");
                break;

            case INVALID_NUMBER:
                Logger.log("alarm", "The capacity of truck was not enough!");
                System.out.println("The capacity of truck is not enough!");
                break;

            case BAD_CONDITION:
                Logger.log("alarm", "The truck was working!");
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                Logger.log("info", "The object was loaded to the truck successfully!");
                System.out.println("The object was loaded to the truck successfully!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There were no enough objects!");
                System.out.println("There are no enough objects!");
                break;
        }
    }

    private void truckUnload(Matcher matcher) {
        Logger.log("info", "The user entered the truck unload command!");
        ResultType result = gameManager.truckUnload(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                Logger.log("alarm", "There was no such object!");
                System.out.println("There is no such object!");
                break;

            case INVALID_NUMBER:
                Logger.log("alarm", "The capacity of warehouse was not enough!");
                System.out.println("The capacity of warehouse is not enough!");
                break;

            case BAD_CONDITION:
                Logger.log("alarm", "The truck was working!");
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                Logger.log("info", "The object was unloaded successfully!");
                System.out.println("The object was unloaded successfully!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There were no enough objects!");
                System.out.println("There are no enough objects!");
                break;
        }
    }

    private void truckGo() {
        Logger.log("info", "The user entered the truck go command!");
        ResultType result = gameManager.truckGo();
        switch (result) {
            case EXISTED:
                Logger.log("alarm", "The truck was working!");
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                Logger.log("info", "The truck went successfully!");
                System.out.println("The truck went successfully!");
                break;

            case NOT_ENOUGH:
                Logger.log("alarm", "There were no objects in the truck!");
                System.out.println("There are no objects in the truck!");
                break;
        }
    }

    private void inquiry() {
        Logger.log("info", "The user entered the inquiry command!");
        System.out.println(gameManager.inquiry());
    }

    private void turn(Matcher matcher) {
        Logger.log("info", "The user entered the turn command!");
        ResultType result = gameManager.turn(Integer.parseInt(matcher.group(1)));
        switch (result) {
            case INVALID_NUMBER:
                Logger.log("alarm", "The time was invalid!");
                System.out.println("The time is invalid!");
                break;

            case SUCCESS:
                Logger.log("info", "The time went on!");
                System.out.println(result.getValue());
                break;

            case FINISH:
                Logger.log("info", "The game finished!");
                System.out.println(result.getValue());
                break;
        }
    }

    private void upgrade(Matcher matcher) {
        Logger.log("info", "The user entered the upgrade command!");
        ResultType result = gameManager.upgrade(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                Logger.log("alarm", "There was not enough coin!");
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                Logger.log("info", result.getValue() + " was upgraded successfully!");
                System.out.println(result.getValue() + " was upgraded successfully!");
                break;

            case NOT_EXISTED:
                Logger.log("alarm", "There was no such object that can be upgraded!");
                System.out.println("There is no such object that can be upgraded!");
                break;

            case BAD_CONDITION:
                Logger.log("alarm", "The object was at its last level!");
                System.out.println("The object is at its last level!");
        }
    }

    @Override
    public void run() {
        if (Game.getInstance().checkTaskFinished()) {
            Logger.log("info", "The game finished!");
            System.out.println(gameManager.finish().getValue());
            System.out.println();
        }
        String command;
        Matcher matcher;
        while (!Commands.MENU.getMatcher(command = scanner.nextLine()).matches()) {
            if (gameManager.isFinished()) {
                System.out.println("Game finished! Enter MENU to go to the game menu!");
            } else if ((matcher = Commands.TRUCK_LOAD.getMatcher(command)).matches()) {
                truckLoad(matcher);
            } else if ((matcher = Commands.TRUCK_UNLOAD.getMatcher(command)).matches()) {
                truckUnload(matcher);
            } else if (Commands.TRUCK_GO.getMatcher(command).matches()) {
                truckGo();
            } else if (Commands.INQUIRY.getMatcher(command).matches()) {
                inquiry();
            } else if (gameManager.truckInUse()) {
                Logger.log("alarm", "The user was in the truck and he/she wanted to do a job other than the truck!");
                System.out.println("The truck is not empty! You must finish your work with truck!");
            } else if (Commands.WELL.getMatcher(command).matches()) {
                well();
            } else if ((matcher = Commands.PLANT.getMatcher(command)).matches()) {
                plant(matcher);
            } else if ((matcher = Commands.PICKUP.getMatcher(command)).matches()) {
                pickup(matcher);
            } else if ((matcher = Commands.BUY.getMatcher(command)).matches()) {
                buy(matcher);
            } else if ((matcher = Commands.BUILD.getMatcher(command)).matches()) {
                build(matcher);
            } else if ((matcher = Commands.WORK.getMatcher(command)).matches()) {
                work(matcher);
            } else if ((matcher = Commands.CAGE.getMatcher(command)).matches()) {
                cage(matcher);
            } else if ((matcher = Commands.TURN.getMatcher(command)).matches()) {
                turn(matcher);
            } else if ((matcher = Commands.UPGRADE.getMatcher(command)).matches()) {
                upgrade(matcher);
            } else {
                Logger.log("error", "User entered an illegal commands!");
                System.out.println("Illegal command!");
            }
            System.out.println();
        }
        Logger.log("info", "The user closed the game!");
        System.out.println();
    }
}
