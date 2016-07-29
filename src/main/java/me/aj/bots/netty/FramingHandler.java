package me.aj.bots.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.aj.bots.util.Codec;

import java.util.List;

public final class FramingHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (!Codec.readableVarInt(in)) {
            System.out.print("\nCould not read this var int!");
        } else {
            int length = Codec.readVarInt32(in);
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
            } else {
                ByteBuf buf = ctx.alloc().buffer(length);
                in.readBytes(buf, length);
                out.add(buf);
            }
        }
    }
}