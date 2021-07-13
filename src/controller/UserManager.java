package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class UserManager {

    private final ArrayList<User> users;

    private UserManager(ArrayList<User> users) {
        this.users = users;
    }

    private static UserManager userManagerInstance;

    public static UserManager getInstance() {
        if (userManagerInstance == null) {
            load();
        }
        return userManagerInstance;
    }

    public void resetUsers() {
        for (User user : users) {
            user.resetMission();
        }
        save();
    }

    public void updateUser(User user, int level, int coin, boolean rewarded) {
        user.updateUser(level, coin, rewarded);
        save();
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void addUser(String username, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            users.add(new User(username, md.digest(password.getBytes(StandardCharsets.UTF_8))));
            save();
        } catch (Exception ignored) {
        }
    }

    public void removeUser(String username) {
        users.remove(findUser(username));
        save();
    }

    public boolean checkUserExists(String newUsername) {
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) return true;
        }
        return false;
    }

    public boolean checkIncorrectPassword(String newUsername, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) {
                MessageDigest md;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    if (Arrays.equals(user.getHashPassword(), md.digest(newPassword.getBytes(StandardCharsets.UTF_8))))
                        return false;
                } catch (Exception ignored) {
                }
                return true;
            }
        }
        return true;
    }

    public boolean checkUsername(String username) {/*
        if (username.length() > 30 || username.length() < 6) return false;
        else if (!username.matches("^[A-Za-z].*")) return false;
        else return username.matches("[\\dA-Za-z_]+");*/
        return true;
    }

    public boolean checkPassword(String password) {/*
        if (password.length() > 20 || password.length() < 8) return false;
        else if (!password.matches(".*\\d.*")) return false;
        else if (!password.matches(".*[A-Z].*")) return false;
        else if (!password.matches(".*[a-z].*")) return false;
        else if (!password.matches(".*[\\^=+\\-()*&%$#@!].*")) return false;
        else return password.matches("\\S*");*/
        return true;
    }

    private static void load() {
        String usersText = FileManager.read("users.json");
        if (!usersText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ArrayList<User> people = gson.fromJson(usersText, new TypeToken<ArrayList<User>>() {
            }.getType());
            userManagerInstance = new UserManager(people);
        } else {
            userManagerInstance = new UserManager(new ArrayList<>());
        }
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String usersText = gson.toJson(users);
        FileManager.write("users.json", usersText);
    }
}