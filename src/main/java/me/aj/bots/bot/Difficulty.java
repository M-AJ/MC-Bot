package me.aj.bots.bot;

import lombok.Getter;

public enum Difficulty {

    PEACEFUL((short) 0),
    EASY((short) 1),
    NORMAL((short) 2),
    HARD((short) 3);

    private static Difficulty[] values = Difficulty.values();

    @Getter
    private short id;

    Difficulty(short id) {
        this.id = id;
    }

    public static Difficulty fromShort(short b) {
        for (Difficulty m : values) {
            if (m.id == b) {
                return m;
            }
        }
        return null;
    }
}
