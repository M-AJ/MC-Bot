package me.aj.bots.protocol.packets;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;

public abstract class PacketIn extends Packet {

    public PacketIn(Bot bot) {
        super(bot);
    }

    public abstract void decode(ByteBuf byteBuf);

    public abstract void handle();
}
