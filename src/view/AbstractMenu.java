package view;

import java.util.Scanner;

public abstract class AbstractMenu {
    public static Scanner scanner;

    public AbstractMenu() {
    }

    public static void setScanner(Scanner scanner) {
        AbstractMenu.scanner = scanner;
    }

    public abstract void run();
}
