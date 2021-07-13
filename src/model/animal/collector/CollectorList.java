package model.animal.collector;

import java.util.regex.Pattern;

public enum CollectorList {
    CAT("model.animal.collector.Cat", "Cat", "^(?i)\\s*cat\\s*$", 150);

    private final String packageName;
    private final String className;
    private final Pattern classPattern;
    private final int price;

    CollectorList(String packageName, String className, String classPattern, int price) {
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

    public static CollectorList getCollector(String name) {
        for (CollectorList value : CollectorList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
