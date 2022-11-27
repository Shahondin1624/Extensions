package events;

public enum EventTypes implements EventType {
    DEFAULT("default"),
    TEST("test"),
    ERROR("error"),
    ;

    private final String name;

    EventTypes(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}
