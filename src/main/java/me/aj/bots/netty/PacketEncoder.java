package me.aj.bots.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

public class PacketEncoder extends MessageToByteEncoder<PacketOut> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketOut packetOut, ByteBuf out) throws Exception {
        //Find Id Length
        ByteBuf idBuf = Unpooled.buffer();
        Codec.writeVarInt32(idBuf, packetOut.getId());
        int packetIdLength = idBuf.readableBytes();
        //Get data
        ByteBuf data = packetOut.encode();
        data.markReaderIndex();
        //Threshold
        int threshold = Bot.get().getClientConnection().getCompressionThreshold();
        boolean underThreshold = data.readableBytes() + packetIdLength < threshold;
        data.resetReaderIndex();
        if (Bot.get().getClientConnection().isCompressionEnabled() && !underThreshold) {
            //TODO
            throw new UnsupportedOperationException();
        } else if (Bot.get().getClientConnection().isCompressionEnabled() && underThreshold) {
            ByteBuf header = getHeader(data, packetOut, false);
            Codec.writeVarInt32(out, header.readableBytes() + Codec.sizeOf(0));
            Codec.writeVarInt32(out, 0);
            out.writeBytes(header);
        } else {
            out.writeBytes(getHeader(data, packetOut, true));
        }
    }

    private ByteBuf getHeader(ByteBuf data, PacketOut packetOut, boolean packetLength) {
        ByteBuf header = Unpooled.buffer();
        header.markReaderIndex();
        ByteBuf t = Unpooled.buffer();
        Codec.writeVarInt32(t, packetOut.getId());
        //Packet Length
        if (packetLength) Codec.writeVarInt32(header, data.readableBytes() + t.readableBytes());
        //ID
        Codec.writeVarInt32(header, packetOut.getId());
        //Data
        header.writeBytes(data);
        header.resetReaderIndex();
        return header;
    }
}
