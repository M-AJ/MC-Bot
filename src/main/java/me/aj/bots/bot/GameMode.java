package me.aj.bots.bot;

import lombok.Getter;

public enum GameMode {

    SURVIVAL((short) 0),
    CREATIVE((short) 1),
    ADVENTURE((short) 2),
    SPECTATOR((short) 3),
    HARDCORE((short) 0x8);

    private static GameMode[] values = GameMode.values();

    @Getter
    private short id;

    GameMode(short id) {
        this.id = id;
    }

    public static GameMode fromShort(short b) {
        for (GameMode m : values) {
            if (m.id == b) {
                return m;
            }
        }
        return null;
    }
}
