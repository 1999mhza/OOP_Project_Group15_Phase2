package controller;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String[] missionState = {"locked", "unlocked", "completed", "rewarded"};

    private UserManager() {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            statement.executeUpdate("create table if not exists users (username varchar(50), hashPassword varchar(50), lastUnlockedLevel int, collectedCoins int, missionInfo varchar(50), primary key(username))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static UserManager userManagerInstance;

    public static UserManager getInstance() {
        if (userManagerInstance == null)
            userManagerInstance = new UserManager();
        return userManagerInstance;
    }

    public String getInitialMissionInfo() {
        HashMap<Integer, Integer> missionInfo = new HashMap<>();
        missionInfo.put(1, 1);
        for (int i = 1; i <= MissionManager.getInstance().getNumberOfLevels(); i++)
            if (i != 1) missionInfo.put(i, 0);
        return new GsonBuilder().create().toJson(missionInfo);
    }

    public void resetUsers() {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            statement.executeUpdate("update users set lastUnlockedLevel = 1, collectedCoins = 0, missionInfo = '" + getInitialMissionInfo() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCollectedCoin(String username) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {

            ResultSet resultSet = statement.executeQuery("select collectedCoins from users where username = '" + username + "'");

            if (resultSet.next()) {
                return resultSet.getInt("collectedCoins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateUser(String username, int level, int coin, boolean rewarded) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {

            ResultSet resultSet = statement.executeQuery("select lastUnlockedLevel, collectedCoins, missionInfo from users where username = '" + username + "'");

            if (resultSet.next()) {
                int lastUnlockedLevel = resultSet.getInt("lastUnlockedLevel");
                int collectedCoins = resultSet.getInt("collectedCoins");
                HashMap<Integer, Integer> missionInfo = new GsonBuilder().create().fromJson(resultSet.getString("missionInfo"), new TypeToken<HashMap<Integer, Integer>>() {
                }.getType());

                collectedCoins += coin;
                if (missionInfo.get(level) != 3) {
                    if (missionInfo.get(level) == 2) {
                        missionInfo.replace(level, (rewarded ? 3 : 2));
                    } else {
                        missionInfo.replace(level, (rewarded ? 3 : 2));
                        if (level >= lastUnlockedLevel && level + 1 <= missionInfo.size()) {
                            lastUnlockedLevel = level + 1;
                            missionInfo.replace(level + 1, 1);
                        }
                    }
                }
                statement.executeUpdate(String.format("update users set collectedCoins = %d, lastUnlockedLevel = %d, missionInfo = '%s' where username = '%s'", collectedCoins, lastUnlockedLevel, new GsonBuilder().create().toJson(missionInfo), username));
            }

            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserLevel(String username, int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select lastUnlockedLevel from users where username = '" + username + "'");
            if (resultSet.next()) {
                return level > resultSet.getInt("lastUnlockedLevel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getUserInformation(String username) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select collectedCoins, missionInfo from users where username = '" + username + "'");
            if (resultSet.next()) {
                HashMap<Integer, Integer> missionInfo = new GsonBuilder().create().fromJson(resultSet.getString("missionInfo"), new TypeToken<HashMap<Integer, Integer>>() {
                }.getType());

                StringBuilder sb = new StringBuilder("\tUsername: " + username + "\n\tCollected Coins: " + resultSet.getInt("collectedCoins") + "\n\tLevels:\n");
                for (Map.Entry<Integer, Integer> entry : missionInfo.entrySet()) {
                    sb.append(String.format("\t\t%3d: %s\n", entry.getKey(), missionState[entry.getValue()]));
                }
                return sb.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<Integer, Integer> getMissionInformation(String username) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select collectedCoins, missionInfo from users where username = '" + username + "'");
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("missionInfo"), new TypeToken<HashMap<Integer, Integer>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(String username, String password) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            statement.executeUpdate(String.format("insert into users values ('%s', '%s', 1, 0, '%s')", username, new String(md.digest(password.getBytes(StandardCharsets.UTF_8))), getInitialMissionInfo()));
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String username) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            statement.executeUpdate("delete from users where username = '" + username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserExists(String username) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select username from users where username = '" + username + "'");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkIncorrectPassword(String username, String password) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select hashPassword from users where username = '" + username + "'");
            if (resultSet.next()) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                if (resultSet.getString("hashPassword").equals(new String(md.digest(password.getBytes(StandardCharsets.UTF_8)))))
                    return false;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkUsername(String username) {
        return username.length() > 0;
        /*
        if (username.length() > 30 || username.length() < 6) return false;
        else if (!username.matches("^[A-Za-z].*")) return false;
        else return username.matches("[\\dA-Za-z_]+");
        return true;*/
    }

    public boolean checkPassword(String password) {
        return password.length() > 0;
        /*
        if (password.length() > 20 || password.length() < 8) return false;
        else if (!password.matches(".*\\d.*")) return false;
        else if (!password.matches(".*[A-Z].*")) return false;
        else if (!password.matches(".*[a-z].*")) return false;
        else if (!password.matches(".*[\\^=+\\-()*&%$#@!].*")) return false;
        else return password.matches("\\S*");
        return true;*/
    }
}