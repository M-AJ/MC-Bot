package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

public class PacketOutKeepAlive extends PacketOut {

    private int keepalive;

    public PacketOutKeepAlive(Bot bot, int id) {
        super(bot);
        this.keepalive = id;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() {
        Codec.writeVarInt32(byteBuf, keepalive);
        return byteBuf;
    }

    public int getId() {
        return 0x00;
    }

    public State getState() {
        return State.PLAY;
    }
}
