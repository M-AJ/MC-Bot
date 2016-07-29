package me.aj.bots.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.aj.bots.util.Codec;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        int packetID = Codec.readVarInt32(buf);
        ByteBuf data = buf.readBytes(buf.readableBytes());
        list.add(new TransitPacket(data, packetID));
    }
}
