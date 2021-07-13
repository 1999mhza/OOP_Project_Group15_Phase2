package model.animal.protective;

import java.util.regex.Pattern;

public enum ProtectiveList {
    HOUND("model.animal.protective.Hound", "Hound", "^(?i)\\s*hound\\s*$", 100);

    private final String packageName;
    private final String className;
    private final Pattern classPattern;
    private final int price;

    ProtectiveList(String packageName, String className, String classPattern, int price) {
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

    public static ProtectiveList getProtective(String name) {
        for (ProtectiveList value : ProtectiveList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
