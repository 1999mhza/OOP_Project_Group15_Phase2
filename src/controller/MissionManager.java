package controller;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.animal.collector.CollectorList;
import model.animal.domestic.DomesticList;
import model.animal.protective.ProtectiveList;
import model.animal.wild.WildList;
import model.factory.FactoryList;
import model.good.GoodList;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MissionManager {

    private static int numberOfLevels;

    private MissionManager() {
        numberOfLevels = 0;
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            statement.executeUpdate("create table if not exists missions (level int, numberOfInitialCoins int, domesticAnimals varchar(50), protectiveAnimals varchar(50), collectorAnimals varchar(50), factories varchar(50), wildAnimalsTime varchar(50), tasks varchar(50), maxPrizeTime int, prize int, primary key(level))");
            ResultSet resultSet = statement.executeQuery("select count(*) from missions");
            if (resultSet.next())
                numberOfLevels = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static MissionManager missionManagerInstance;

    public static MissionManager getInstance() {
        if (missionManagerInstance == null)
            missionManagerInstance = new MissionManager();
        return missionManagerInstance;
    }

    private void processMission(int level, int numberOfInitialCoins, HashMap<DomesticList, Integer> domesticAnimals, HashMap<ProtectiveList, Integer> protectiveAnimals, HashMap<CollectorList, Integer> collectorAnimals, HashSet<FactoryList> factories, HashMap<WildList, Integer[]> wildAnimalsTime, HashMap<String, Integer> tasks, int maxPrizeTime, int prize) {
        if (level <= numberOfLevels && level > 0) {
            try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
                statement.executeUpdate(String.format("update missions set numberOfInitialCoins = %d, domesticAnimals = '%s', protectiveAnimals = '%s', collectorAnimals = '%s', factories = '%s', wildAnimalsTime = '%s', tasks = '%s', maxPrizeTime = %d, prize = %d where level = %d", numberOfInitialCoins, new GsonBuilder().create().toJson(domesticAnimals), new GsonBuilder().create().toJson(protectiveAnimals), new GsonBuilder().create().toJson(collectorAnimals), new GsonBuilder().create().toJson(factories), new GsonBuilder().create().toJson(wildAnimalsTime), new GsonBuilder().create().toJson(tasks), maxPrizeTime, prize, level));

                Logger.log("admin", "The missions of the level " + level + " was changed!");
                System.out.println("Successful change!");
                UserManager.getInstance().resetUsers();
                Logger.log("admin", "The users info was reset!");
                System.out.println("All users have been reset!");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (level == numberOfLevels + 1) {
            try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
                statement.executeUpdate(String.format("insert into missions values (%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', %d, %d)", level, numberOfInitialCoins, new GsonBuilder().create().toJson(domesticAnimals), new GsonBuilder().create().toJson(protectiveAnimals), new GsonBuilder().create().toJson(collectorAnimals), new GsonBuilder().create().toJson(factories), new GsonBuilder().create().toJson(wildAnimalsTime), new GsonBuilder().create().toJson(tasks), maxPrizeTime, prize));
                numberOfLevels++;

                Logger.log("admin", "A new level (" + level + ") was added to levels!");
                System.out.println("Successful add!");
                UserManager.getInstance().resetUsers();
                Logger.log("admin", "The users info was reset!");
                System.out.println("All users have been reset!");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("The level is invalid!");
        }
    }

    public int getNumberOfInitialCoins(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select numberOfInitialCoins from missions where level = " + level);
            if (resultSet.next()) {
                return resultSet.getInt("numberOfInitialCoins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public HashMap<DomesticList, Integer> getDomesticAnimals(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select domesticAnimals from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("domesticAnimals"), new TypeToken<HashMap<DomesticList, Integer>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<ProtectiveList, Integer> getProtectiveAnimals(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select protectiveAnimals from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("protectiveAnimals"), new TypeToken<HashMap<ProtectiveList, Integer>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<CollectorList, Integer> getCollectorAnimals(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select collectorAnimals from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("collectorAnimals"), new TypeToken<HashMap<CollectorList, Integer>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashSet<FactoryList> getFactories(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select factories from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("factories"), new TypeToken<HashSet<FactoryList>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getTasks(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select tasks from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("tasks"), new TypeToken<HashMap<String, Integer>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<WildList, Integer[]> getWildAnimalsTime(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select wildAnimalsTime from missions where level = " + level);
            if (resultSet.next()) {
                return new GsonBuilder().create().fromJson(resultSet.getString("wildAnimalsTime"), new TypeToken<HashMap<WildList, Integer[]>>() {
                }.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMaxPrizeTime(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select maxPrizeTime from missions where level = " + level);
            if (resultSet.next()) {
                return resultSet.getInt("maxPrizeTime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPrize(int level) {
        try (Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm_frenzy", "MHZ", "mhza1999").createStatement()) {
            ResultSet resultSet = statement.executeQuery("select prize from missions where level = " + level);
            if (resultSet.next()) {
                return resultSet.getInt("prize");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public static void main(String[] args) {

        int level;
        int numberOfInitialCoins;
        HashMap<String, Integer> domesticAnimals = new HashMap<>();
        HashMap<String, Integer> protectiveAnimals = new HashMap<>();
        HashMap<String, Integer> collectorAnimals = new HashMap<>();
        HashSet<String> factoriesSet = new HashSet<>();
        HashMap<String, Integer[]> wildAnimalsTime = new HashMap<>();
        HashMap<String, Integer> tasksMap = new HashMap<>();
        int maxPrizeTime;
        int prize;

        // ---------- Enter the Specifications -----------
        level = 1;

        numberOfInitialCoins = 100;

        domesticAnimals.put("Chicken", 1);
        //domesticAnimals.put("Buffalo", 2);

        //protectiveAnimals.put("Hound", 2);

        //collectorAnimals.put("Cat", 5);

        factoriesSet.add("Incubator");
        //factoriesSet.add("MilkPacker");

        wildAnimalsTime.put("Lion", new Integer[]{2, 6, 12, 18});
        //wildAnimalsTime.put("Bear", new Integer[]{20, 21});

        tasksMap.put("Egg", 2);
        //tasksMap.put("Flour", 10);
        //tasksMap.put("Bread", 10);
        //tasksMap.put("Egg", 10);
        //tasksMap.put("Buffalo", 3);
        //tasksMap.put("Coin", 500);
        //tasksMap.put("Lion", 2);

        maxPrizeTime = 30;
        prize = 50;
        // -----------------------------------------------

        if (level < 0 || level > MissionManager.getInstance().getNumberOfLevels() + 1) {
            System.out.println("The level is invalid!");
            return;
        }

        if (numberOfInitialCoins < 0) {
            System.out.println("The number of initial coins is invalid!");
            return;
        }

        if (maxPrizeTime <= 0) {
            System.out.println("Maximum time of getting prize is invalid!");
            return;
        }

        if (prize < 0) {
            System.out.println("The prize is invalid!");
            return;
        }

        HashMap<DomesticList, Integer> domestics = new HashMap<>();
        HashMap<ProtectiveList, Integer> protectives = new HashMap<>();
        HashMap<CollectorList, Integer> collectors = new HashMap<>();
        HashSet<FactoryList> factories = new HashSet<>();
        HashMap<WildList, Integer[]> wildsTime = new HashMap<>();
        HashMap<String, Integer> tasks = new HashMap<>();

        for (String animal : domesticAnimals.keySet()) {
            if (DomesticList.getDomestic(animal) == null || domesticAnimals.get(animal) == 0) {
                System.out.println("Domestic animals have some problems!");
                return;
            }
            domestics.put(DomesticList.getDomestic(animal), domesticAnimals.get(animal));
        }

        for (String animal : protectiveAnimals.keySet()) {
            if (ProtectiveList.getProtective(animal) == null || protectiveAnimals.get(animal) == 0) {
                System.out.println("Protective animals have some problems!");
                return;
            }
            protectives.put(ProtectiveList.getProtective(animal), protectiveAnimals.get(animal));
        }

        for (String animal : collectorAnimals.keySet()) {
            if (CollectorList.getCollector(animal) == null || collectorAnimals.get(animal) == 0) {
                System.out.println("Collector animals have some problems!");
                return;
            }
            collectors.put(CollectorList.getCollector(animal), collectorAnimals.get(animal));
        }

        for (String animal : wildAnimalsTime.keySet()) {
            if (WildList.getWild(animal) == null || Arrays.asList(wildAnimalsTime.get(animal)).contains(0)) {
                System.out.println("Wild animals have some problems!");
                return;
            }
            wildsTime.put(WildList.getWild(animal), wildAnimalsTime.get(animal));
        }

        for (String factory : factoriesSet) {
            if (FactoryList.getFactory(factory) == null || factories.contains(FactoryList.getFactory(factory))) {
                System.out.println("Factories have some problems!");
                return;
            }
            factories.add(FactoryList.getFactory(factory));
        }

        if (tasksMap.isEmpty()) {
            System.out.println("Tasks have some problems!");
            return;
        }
        for (String task : tasksMap.keySet()) {
            if (tasksMap.get(task) == 0) {
                System.out.println("Tasks have some problems!");
                return;
            }
            if (GoodList.getGood(task) != null) {
                tasks.put(GoodList.getGood(task).getClassName(), tasksMap.get(task));
            } else if (DomesticList.getDomestic(task) != null) {
                tasks.put(DomesticList.getDomestic(task).getClassName(), tasksMap.get(task));
            } else if (ProtectiveList.getProtective(task) != null) {
                tasks.put(ProtectiveList.getProtective(task).getClassName(), tasksMap.get(task));
            } else if (CollectorList.getCollector(task) != null) {
                tasks.put(CollectorList.getCollector(task).getClassName(), tasksMap.get(task));
            } else if (WildList.getWild(task) != null) {
                tasks.put(WildList.getWild(task).getClassName(), tasksMap.get(task));
            } else if (task.equalsIgnoreCase("Coin")) {
                tasks.put("Coin", tasksMap.get(task));
            } else {
                System.out.println("Tasks have some problems!");
                return;
            }
        }

        MissionManager.getInstance().processMission(level, numberOfInitialCoins, domestics, protectives, collectors, factories, wildsTime, tasksMap, maxPrizeTime, prize);
    }
}