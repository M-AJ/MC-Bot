package me.aj.bots.bot;

import lombok.Getter;

public enum LevelType {

    DEFAULT("default"),
    FLAT("flat"),
    LARGEBIOMES("largeBiomes"),
    AMPLIFIED("amplified"),
    DEFAULT_1_1("default_1_1");

    private static LevelType[] values = LevelType.values();

    @Getter
    private String name;

    LevelType(String name) {
        this.name = name;
    }

    public static LevelType fromName(String n) {
        for (LevelType m : values) {
            if (m.name.equalsIgnoreCase(n)) {
                return m;
            }
        }
        return null;
    }
}
