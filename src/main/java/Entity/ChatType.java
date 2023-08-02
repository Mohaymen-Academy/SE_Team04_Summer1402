package Entity;

public enum ChatType {
    pv(0),
    group(1),
    channel(2);

    private final int value;

    private ChatType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
