package model.animal.wild;

import java.util.regex.Pattern;

public enum WildList {
    BEAR("model.animal.wild.Bear", "Bear", "^(?i)\\s*bear\\s*$"),
    TIGER("model.animal.wild.Tiger", "Tiger", "^(?i)\\s*tiger\\s*$"),
    LION("model.animal.wild.Lion", "Lion", "^(?i)\\s*lion\\s*$");

    private final String packageName;
    private final String className;
    private final Pattern classPattern;

    WildList(String packageName, String className, String classPattern) {
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

    public static WildList getWild(String name) {
        for (WildList value : WildList.values()) {
            if (value.classPattern.matcher(name).matches())
                return value;
        }
        return null;
    }
}
