package model.good;

import java.util.regex.Pattern;

public enum GoodList {
    CAKE("model.good.Cake", "Cake", "^(?i)\\s*cake\\s*$"),
    EGG("model.good.Egg", "Egg", "^(?i)\\s*egg\\s*$"),
    FABRIC("model.good.Fabric", "Fabric", "^(?i)\\s*fabric\\s*$"),
    WOOL("model.good.Wool", "Wool", "^(?i)\\s*wool\\s*$"),
    FLOUR("model.good.Flour", "Flour", "^(?i)\\s*flour\\s*$"),
    FIBER("model.good.Fiber", "Fiber", "^(?i)\\s*fiber\\s*$"),
    COOKIE("model.good.Cookie", "Cookie", "^(?i)\\s*cookie\\s*$"),
    DRESS("model.good.Dress", "Dress", "^(?i)\\s*dress\\s*$");

    private final String packageName;
    private final String className;
    private final Pattern classPattern;

    GoodList(String packageName, String className, String classPattern) {
        this.packageName = packageName;
        this.className = className;
        this.classPattern = Pattern.compile(classPattern);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public static GoodList getGood(String name) {
        for (GoodList value : GoodList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
