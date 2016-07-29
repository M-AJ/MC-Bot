package me.aj.bots.protocol.packets.status;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.util.Codec;

public class PacketInResponse extends PacketIn {

    private String response;

    public PacketInResponse(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.response = Codec.readString(byteBuf);
    }

    @Override
    public void handle() {
        System.out.print("\nServer Info: " + response);
        //Send login Packet
    }

    public int getId() {
        return 0x00;
    }

    public State getState() {
        return State.STATUS;
    }
}
