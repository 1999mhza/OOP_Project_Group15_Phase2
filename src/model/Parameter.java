package model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

public class Parameter {

    private static Parameter parameterInstance = null;

    public static Parameter getInstance() {
        return parameterInstance;
    }

    public static void initiate() {
        parameterInstance = new Parameter();
    }

    private Parameter() {
    }

    private MediaPlayer homeMedia, gameMedia, winMedia;

    public void setMedia() {
        homeMedia = new MediaPlayer(new Media(new File("src/resource/Sounds/Home.m4a").toURI().toString()));
        homeMedia.setOnEndOfMedia(() -> homeMedia.seek(Duration.ZERO));

        gameMedia = new MediaPlayer(new Media(new File("src/resource/Sounds/Game.m4a").toURI().toString()));
        gameMedia.setOnEndOfMedia(() -> gameMedia.seek(Duration.ZERO));

        winMedia = new MediaPlayer(new Media(new File("src/resource/Sounds/Win.mp3").toURI().toString()));
        winMedia.setOnEndOfMedia(() -> winMedia.seek(Duration.ZERO));

        homeMedia.setVolume(0.5);
        gameMedia.setVolume(0.5);
        winMedia.setVolume(0.5);
    }

    public MediaPlayer getHomeMedia() {
        return homeMedia;
    }

    public MediaPlayer getGameMedia() {
        return gameMedia;
    }

    public MediaPlayer getWinMedia() {
        return winMedia;
    }

    private double width, height;

    public void setScreenSize() {
        height = Screen.getPrimary().getBounds().getHeight();
        width = Screen.getPrimary().getBounds().getWidth();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    private int level;

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
