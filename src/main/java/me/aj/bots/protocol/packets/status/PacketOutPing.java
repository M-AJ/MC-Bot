package me.aj.bots.protocol.packets.status;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;

import java.io.IOException;

public class PacketOutPing extends PacketOut {

    public PacketOutPing(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() throws IOException {
        return byteBuf;
    }

    public int getId() {
        return 0x01;
    }

    public State getState() {
        return State.STATUS;
    }
}
