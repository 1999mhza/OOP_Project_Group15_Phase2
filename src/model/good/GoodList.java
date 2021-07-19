package model.good;

import java.util.regex.Pattern;

public enum GoodList {
    CAKE("model.good.Cake", "Cake", "^(?i)\\s*cake\\s*$", 80),
    EGG("model.good.Egg", "Egg", "^(?i)\\s*egg\\s*$", 15),
    FABRIC("model.good.Fabric", "Fabric", "^(?i)\\s*fabric\\s*$", 70),
    WOOL("model.good.Wool", "Wool", "^(?i)\\s*wool\\s*$", 20),
    FLOUR("model.good.Flour", "Flour", "^(?i)\\s*flour\\s*$", 40),
    FIBER("model.good.Fiber", "Fiber", "^(?i)\\s*fiber\\s*$", 50),
    COOKIE("model.good.Cookie", "Cookie", "^(?i)\\s*cookie\\s*$", 60),
    DRESS("model.good.Dress", "Dress", "^(?i)\\s*dress\\s*$", 100);

    private final String packageName;
    private final String className;
    private final Pattern classPattern;
    private final int price;

    GoodList(String packageName, String className, String classPattern, int price) {
        this.packageName = packageName;
        this.className = className;
        this.classPattern = Pattern.compile(classPattern);
        this.price = price;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public int getPrice() {
        return price;
    }

    public static GoodList getGood(String name) {
        for (GoodList value : GoodList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
