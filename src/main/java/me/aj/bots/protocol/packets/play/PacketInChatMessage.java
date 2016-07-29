package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.Bot;
import me.aj.bots.util.Codec;

public class PacketInChatMessage extends PacketIn {

    private String chat;

    private int position;

    public PacketInChatMessage(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.chat = Codec.readString(byteBuf);
        this.position = byteBuf.readByte();
    }

    @Override
    public void handle() {
        System.out.print("\nChatMessage. Text:" + chat);
    }

    public int getId() {
        return 0x02;
    }

    public State getState() {
        return State.PLAY;
    }
}
