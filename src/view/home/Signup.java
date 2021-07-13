package view.home;

import controller.UserManager;
import controller.Logger;
import view.AbstractMenu;
import view.game.Menu;

public class Signup extends AbstractMenu {
    public Signup() {
        super();
    }

    @Override
    public void run() {
        UserManager userManager = UserManager.getInstance();
        String username, password;

        System.out.println("Enter your username:");
        if (userManager.checkUserExists((username = scanner.nextLine()))) {
            Logger.log("error", "The user entered a username existing.");
            System.out.println("The username is already taken!");
            System.out.println();
            return;
        }

        if (!userManager.checkUsername(username)) {
            Logger.log("error", "The user entered a inappropriate username.");
            System.out.println("Your username is inappropriate!");
            System.out.println();
            return;
        }

        Logger.log("info", "User " + username);
        System.out.println("Enter your password:");
        if (!userManager.checkPassword((password = scanner.nextLine()))) {
            Logger.log("error", "The user entered a inappropriate password.");
            System.out.println("Your password is inappropriate!");
            System.out.println();
            return;
        }

        System.out.println("Re-enter your password for verification:");
        if (!password.equals(scanner.nextLine())) {
            Logger.log("error", "The user entered repeat password incorrectly.");
            System.out.println("Passwords don't match!");
            System.out.println();
            return;
        }

        Logger.log("info", username + " was added to the user list.");
        userManager.addUser(username, password);

        System.out.println();
        Logger.log("info", username + " entered the game menu.");
        new Menu(userManager.findUser(username)).run();
    }
}
