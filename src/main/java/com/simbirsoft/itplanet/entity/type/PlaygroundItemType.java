package com.simbirsoft.itplanet.entity.type;

public enum PlaygroundItemType {
    MAZAY("Мазай"),
    HARE("Заяц"),
    EMPTY(""),
    DESTINATION("Финиш");

    private final String name;

    PlaygroundItemType(String name) {
        this.name = name;
    }

    public static PlaygroundItemType valueOf(char c) {
        PlaygroundItemType type = EMPTY;
        if (c == 'S') type = MAZAY;
        if (c == 'U') type = HARE;
        if (c == 'F') type = DESTINATION;
        return type;
    }

    public String getName() {
        return name;
    }
}
