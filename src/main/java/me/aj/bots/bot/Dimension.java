package me.aj.bots.bot;

import lombok.Getter;

public enum Dimension {

    NETHER((byte) -1),
    OVERWORLD((byte) 0),
    END((byte) 1);

    private static Dimension[] values = Dimension.values();

    @Getter
    private byte id;

    Dimension(byte id) {
        this.id = id;
    }

    public static Dimension fromByte(byte b) {
        for (Dimension m : values) {
            if (m.id == b) {
                return m;
            }
        }
        return null;
    }
}
