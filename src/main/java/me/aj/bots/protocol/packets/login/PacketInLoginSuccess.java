package me.aj.bots.protocol.packets.login;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.util.Codec;

public class PacketInLoginSuccess extends PacketIn {

    private String name;

    private String uuid;

    public PacketInLoginSuccess(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.uuid = Codec.readString(byteBuf);
        this.name = Codec.readString(byteBuf);
    }

    @Override
    public void handle() {
        System.out.print("\nLogin. Username:" + name + " UUID:" + uuid);
        Bot.get().getClientConnection().setState(State.PLAY);
    }

    public int getId() {
        return 0x02;
    }

    public State getState() {
        return State.LOGIN;
    }
}
