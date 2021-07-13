package view.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    WELL("^(?i)\\s*well\\s*$"),
    PLANT("^(?i)\\s*plant\\s+([0-9]+)\\s+([0-9]+)\\s*$"),
    PICKUP("^(?i)\\s*pick\\s*up\\s+([0-9]+)\\s+([0-9]+)\\s*$"),
    BUY("^(?i)\\s*buy\\s+([a-zA-Z]+)\\s*$"),
    BUILD("^(?i)\\s*build\\s+([a-zA-Z]+)\\s*$"),
    WORK("^(?i)\\s*work\\s+([a-zA-Z]+)\\s+([0-9]+)\\s*$"),
    CAGE("^(?i)\\s*cage\\s+([0-9]+)\\s+([0-9]+)\\s*$"),
    TRUCK_LOAD("^(?i)\\s*truck\\s*load\\s+([a-zA-Z]+)\\s+([0-9]+)\\s*$"),
    TRUCK_UNLOAD("^(?i)\\s*truck\\s*un\\s*load\\s+([a-zA-Z]+)\\s+([0-9]+)\\s*$"),
    TRUCK_GO("^(?i)\\s*truck\\s*go\\s*$"),
    TURN("^(?i)\\s*turn\\s+([0-9]+)\\s*$"),
    INQUIRY("^(?i)\\s*inquiry\\s*$"),
    UPGRADE("^(?i)\\s*upgrade\\s+([a-zA-Z]+)\\s*$"),
    MENU("^(?i)\\s*menu\\s*$");

    private final Pattern commandPattern;

    Commands(String s) {
        this.commandPattern = Pattern.compile(s);
    }

    public Matcher getMatcher(String command) {
        return this.commandPattern.matcher(command);
    }

}
