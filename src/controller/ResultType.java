package controller;

public enum ResultType {
    NOT_ENOUGH,
    EXISTED,
    NOT_EXISTED,
    SUCCESS,
    INVALID_NUMBER,
    BAD_CONDITION,
    FINISH;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
