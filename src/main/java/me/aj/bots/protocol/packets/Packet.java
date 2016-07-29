package me.aj.bots.protocol.packets;

import lombok.Getter;
import me.aj.bots.Bot;

public abstract class Packet {

    public enum PacketDirection {
        IN, OUT
    }

    public enum State {
        HANDSHAKE,
        STATUS,
        LOGIN,
        PLAY
    }

    @Getter
    private Bot bot;

    public Packet(Bot bot) {
        this.bot = bot;
    }

    public abstract PacketDirection getDirection();

    public abstract int getId();

    public abstract State getState();
}
