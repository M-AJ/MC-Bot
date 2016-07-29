package me.aj.bots.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.aj.bots.Bot;
import me.aj.bots.util.Codec;

import java.util.List;
import java.util.zip.Inflater;

public final class CompressionHandler extends MessageToMessageDecoder<ByteBuf> {

    private final Inflater inflater = new Inflater();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (!Bot.get().getClientConnection().isCompressionEnabled()) {
            msg.retain();
            out.add(msg);
            return;
        }
        int index = msg.readerIndex();
        int uncompressedSize = Codec.readVarInt32(msg);
        if (uncompressedSize == 0) {
            int length = msg.readableBytes();
            if (length >= Bot.get().getClientConnection().getCompressionThreshold()) {
                throw new RuntimeException("Received uncompressed message of size " + length + " greater than threshold " +
                        Bot.get().getClientConnection().getCompressionThreshold());
            }
            out.add(msg.readBytes(length));
        } else {
            msg.markReaderIndex();
            Codec.readVarInt32(msg);
            msg.resetReaderIndex();
            byte[] sourceData = new byte[msg.readableBytes()];
            msg.readBytes(sourceData);
            inflater.setInput(sourceData);
            byte[] destData = new byte[uncompressedSize];
            int resultLength = inflater.inflate(destData);
            inflater.reset();
            if (resultLength == 0) {
                msg.readerIndex(index);
                msg.retain();
                out.add(msg);
            } else if (resultLength != uncompressedSize) {
                throw new RuntimeException("Received compressed message claiming to be of size " + uncompressedSize + " but actually " + resultLength);
            } else {
                out.add(Unpooled.wrappedBuffer(destData));
            }
        }
    }
}