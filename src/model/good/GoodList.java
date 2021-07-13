package model.good;

import java.util.regex.Pattern;

public enum GoodList {
    BREAD("model.good.Bread", "Bread", "^(?i)\\s*bread\\s*$"),
    EGG("model.good.Egg", "Egg", "^(?i)\\s*egg\\s*$"),
    FABRIC("model.good.Fabric", "Fabric", "^(?i)\\s*fabric\\s*$"),
    FEATHER("model.good.Feather", "Feather", "^(?i)\\s*feather\\s*$"),
    FLOUR("model.good.Flour", "Flour", "^(?i)\\s*flour\\s*$"),
    ICE_CREAM("model.good.IceCream", "IceCream", "^(?i)\\s*ice\\s*cream\\s*$"),
    MILK("model.good.Milk", "Milk", "^(?i)\\s*milk\\s*$"),
    PACKET_MILK("model.good.PacketMilk", "PacketMilk", "^(?i)\\s*packet\\s*milk\\s*$"),
    SHIRT("model.good.Shirt", "Shirt", "^(?i)\\s*shirt\\s*$");

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
