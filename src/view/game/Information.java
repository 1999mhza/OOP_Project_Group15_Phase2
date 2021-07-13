package view.game;

import controller.GameManager;
import controller.Logger;
import model.User;
import view.AbstractMenu;

public class Information extends AbstractMenu {

    private final User user;

    public Information(User user) {
        super();
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("Information:");
        System.out.println(user.toString());
        System.out.println("1. Back");

        while (true) {
            try {
                int nextMenuNum = Integer.parseInt(scanner.nextLine());
                if (nextMenuNum == 1) {
                    System.out.println();
                    Logger.log("info", "The user returned to the game menu.");
                    return;

                } else {
                    Logger.log("error", "An invalid number was entered.");
                    System.out.println("Invalid number!");
                }

            } catch (Exception ignored) {
                Logger.log("error", "An invalid number was entered.");
                System.out.println("Invalid number!");
            }
        }
    }
}
