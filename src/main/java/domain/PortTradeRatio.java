package domain;

public enum PortTradeRatio {
    THREE_TO_ONE(3),
    TWO_TO_ONE(2);

    private int value;
    PortTradeRatio(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
