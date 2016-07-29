package me.aj.bots.netty;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class TransitPacket {

    private ByteBuf packetData;
    private int id;

    public TransitPacket(ByteBuf packetData, int id) {
        this.packetData = packetData;
        this.id = id;
    }
}
