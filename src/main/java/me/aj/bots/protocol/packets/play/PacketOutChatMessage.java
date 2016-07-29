package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

public class PacketOutChatMessage extends PacketOut {

    private String text;

    public PacketOutChatMessage(Bot bot, String text) {
        super(bot);
        this.text = text;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() {
        Codec.writeString(byteBuf, text);
        return byteBuf;
    }

    public int getId() {
        return 0x01;
    }

    public State getState() {
        return State.PLAY;
    }
}
