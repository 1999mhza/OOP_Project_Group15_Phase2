package model.animal.wild;

import java.util.regex.Pattern;

public enum WildList {
    BEAR("model.animal.wild.Bear", "Bear", "^(?i)\\s*bear\\s*$", 400),
    LION("model.animal.wild.Lion", "Lion", "^(?i)\\s*lion\\s*$", 300);

    private final String packageName;
    private final String className;
    private final Pattern classPattern;
    private final int price;

    WildList(String packageName, String className, String classPattern, int price) {
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

    public static WildList getWild(String name) {
        for (WildList value : WildList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
