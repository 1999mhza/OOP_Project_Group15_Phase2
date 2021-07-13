package model;

import controller.MissionManager;

import java.util.HashMap;
import java.util.Map;

public class User {
    private static final String[] missionState = {"locked", "unlocked", "completed", "rewarded"};
    private final String username;
    private final byte[] hashPassword;
    private int lastUnlockedLevel;
    private int collectedCoins;
    private final HashMap<Integer, String> missionInfo;

    public User(String username, byte[] hashPassword) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.missionInfo = new HashMap<>();
        resetMission();
    }

    public void resetMission() {
        this.lastUnlockedLevel = 1;
        this.collectedCoins = 0;
        this.missionInfo.clear();
        this.missionInfo.put(1, missionState[1]);
        for (int i = 1; i <= MissionManager.getInstance().getNumberOfLevels(); i++)
            if (i != 1) missionInfo.put(i, missionState[0]);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getHashPassword() {
        return hashPassword;
    }

    public int getLastUnlockedLevel() {
        return lastUnlockedLevel;
    }

    public void updateUser(int level, int coin, boolean rewarded) {
        this.collectedCoins += coin;
        if (this.missionInfo.get(level).equalsIgnoreCase(missionState[3])) return;
        else if (this.missionInfo.get(level).equalsIgnoreCase(missionState[2])) {
            this.missionInfo.replace(level, (rewarded ? missionState[3] : missionState[2]));
        } else {
            this.missionInfo.replace(level, (rewarded ? missionState[3] : missionState[2]));
            if (level + 1 > this.lastUnlockedLevel && level + 1 <= missionInfo.size()) {
                this.lastUnlockedLevel = level + 1;
                this.missionInfo.replace(level + 1, missionState[1]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\tUsername: " + username + "\n\tCollected Coins: " + collectedCoins + "\n\tLevels:\n");
        for (Map.Entry<Integer, String> entry : missionInfo.entrySet()) {
            sb.append(String.format("\t\t%3d: %s\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
