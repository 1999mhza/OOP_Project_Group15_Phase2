package model.factory;

import java.util.regex.Pattern;

public enum FactoryList {
    MILL("model.factory.Mill", "Mill", "^(?i)\\s*mill\\s*$", 150),
    COOKIE_BAKERY("model.factory.CookieBakery", "CookieBakery", "^(?i)\\s*cookie\\s*bakery\\s*$", 250),
    CAKE_BAKERY("model.factory.CakeBakery", "CakeBakery", "^(?i)\\s*cake\\s*bakery\\s*$", 350),
    SPINNERY("model.factory.Spinnery", "Spinnery", "^(?i)\\s*spinnery\\s*$", 250),
    WEAVING_FACTORY("model.factory.WeavingFactory", "WeavingFactory", "^(?i)\\s*weaving\\s*factory\\s*$", 350),
    SEWING_FACTORY("model.factory.SewingFactory", "SewingFactory", "^(?i)\\s*sewing\\s*factory\\s*$", 450),
    INCUBATOR("model.factory.Incubator", "Incubator", "^(?i)\\s*incubator\\s*$", 450);

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
