package view.home;

import controller.Logger;
import view.AbstractMenu;

import java.util.Scanner;

public class Home extends AbstractMenu {

    public Home() {
        super();
        AbstractMenu.setScanner(new Scanner(System.in));
    }

    public void run() {

        while (true) {
            System.out.println("Home:");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Delete account");
            System.out.println("4. Exit");

            while (true) {
                try {
                    int nextMenuNum = Integer.parseInt(scanner.nextLine());
                    if (nextMenuNum == 1) {
                        System.out.println();
                        Logger.log("info", "A user wanted to log in.");
                        new Login().run();
                        break;

                    } else if (nextMenuNum == 2) {
                        System.out.println();
                        Logger.log("info", "A user wanted to sign up.");
                        new Signup().run();
                        break;

                    } else if (nextMenuNum == 3) {
                        System.out.println();
                        Logger.log("info", "A user wanted to deleter account.");
                        new Delete().run();
                        break;

                    } else if (nextMenuNum == 4) {
                        Logger.log("info", "A user closed the game.");
                        System.exit(1);

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
}
