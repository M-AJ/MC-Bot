package me.aj.bots.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.aj.bots.protocol.ProtocolManager;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.Packet;
import me.aj.bots.protocol.packets.handshake.PacketOutHandshake;
import me.aj.bots.protocol.packets.login.PacketOutLoginStart;

public class ClientHandler extends SimpleChannelInboundHandler<TransitPacket> {

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, TransitPacket transitPacket) throws Exception {
        Bot.get().getProtocolManager().getPackets().handlePacket(transitPacket);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("\nActive Sending Handshake and Login");
        Bot.get().getProtocolManager().sendPacket(new PacketOutHandshake(Bot.get()));
        Bot.get().getClientConnection().setState(Packet.State.LOGIN);
        Bot.get().getProtocolManager().sendPacket(new PacketOutLoginStart(Bot.get()));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.print("\nRegistered Channel");
        Bot.get().setProtocolManager(new ProtocolManager(ctx.channel()));
    }
}
