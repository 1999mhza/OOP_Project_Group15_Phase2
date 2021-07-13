package view.home;

import controller.UserManager;
import controller.Logger;
import view.AbstractMenu;

public class Delete extends AbstractMenu {
    public Delete() {
        super();
    }

    @Override
    public void run() {
        UserManager userManager = UserManager.getInstance();
        String username;

        System.out.println("Enter your username:");
        if (!userManager.checkUserExists((username = scanner.nextLine()))) {
            Logger.log("error", "The user entered a username not existing.");
            System.out.println("The username doesn't exist!");
            System.out.println();
            return;
        }

        Logger.log("info", "User " + username);
        System.out.println("Enter your password:");
        if (userManager.checkIncorrectPassword(username, scanner.nextLine())) {
            Logger.log("error", "The user entered an incorrect password.");
            System.out.println("Your password is incorrect!");
            System.out.println();
            return;
        }

        System.out.println();
        Logger.log("info", username + " deleted his/her account.");
        userManager.removeUser(username);
    }
}
