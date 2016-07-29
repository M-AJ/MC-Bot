package me.aj.bots;

import me.aj.bots.protocol.packets.play.PacketOutChatMessage;
import me.aj.bots.util.Codec;

import java.util.Scanner;

public class Main {

    //TODO
    //Encoding Compressed Packets
    //Optional - More Packet Handling
    //Encryption for online-mode

    private final Scanner scanner = new Scanner(System.in);
    private final String ip = "localhost";
    private final String name = "BOT_" + Codec.randInt(1, 100);
    private final int port = 25565;
    private final int protocolVersion = 47; //1.8 Server

    private Main() {
        System.out.print("Enter Chat: ");
        new Thread(() -> {
            while (!Thread.interrupted()) {
                String command = scanner.nextLine();
                Bot.get().getProtocolManager().sendPacket(new PacketOutChatMessage(Bot.get(),
                        command));
                System.out.print("Sent command : " + command);
            }
        }).start();
        new Bot(name, protocolVersion, ip, port);
    }

    public static void main(String[] args) {
        new Main();
    }
}
