package view.game;

import controller.Logger;
import controller.UserManager;
import view.AbstractMenu;

public class Information extends AbstractMenu {

    private final String username;

    public Information(String username) {
        super();
        this.username = username;
    }

    @Override
    public void run() {
        System.out.println("Information:");
        System.out.println(UserManager.getInstance().getUserInformation(username));
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
