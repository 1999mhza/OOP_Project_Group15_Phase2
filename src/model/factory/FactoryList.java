package model.factory;

import java.util.regex.Pattern;

public enum FactoryList {
    BAKERY("model.factory.Bakery", "Bakery", "^(?i)\\s*bakery\\s*$", 250),
    ICE_CREAM_MAKER("model.factory.IceCreamMaker", "IceCreamMaker", "^(?i)\\s*ice\\s*cream\\s*maker\\s*$", 550),
    MILL("model.factory.Mill", "Mill", "^(?i)\\s*mill\\s*$", 150),
    MILK_PACKER("model.factory.MilkPacker", "MilkPacker", "^(?i)\\s*milk\\s*packer\\s*$", 400),
    SEWING_MACHINE("model.factory.SewingMachine", "SewingMachine", "^(?i)\\s*sewing\\s*machine\\s*$", 400),
    LOOM("model.factory.Loom", "Loom", "^(?i)\\s*loom\\s*$", 250);

    private final String packageName;
    private final String className;
    private final Pattern classPattern;
    private final int price;

    FactoryList(String packageName, String className, String classPattern, int price) {
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

    public static FactoryList getFactory(String name) {
        for (FactoryList value : FactoryList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
