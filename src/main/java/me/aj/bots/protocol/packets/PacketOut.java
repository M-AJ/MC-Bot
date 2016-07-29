package me.aj.bots.protocol.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.aj.bots.Bot;

import java.io.IOException;

public abstract class PacketOut extends Packet {

    protected ByteBuf byteBuf;

    public PacketOut(Bot bot) {
        super(bot);
        this.byteBuf = Unpooled.buffer();
    }

    public abstract ByteBuf encode() throws IOException;
}
