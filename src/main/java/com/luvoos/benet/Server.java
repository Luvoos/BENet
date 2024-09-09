package com.luvoos.benet;

import com.luvoos.benet.handler.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.BedrockPong;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v686.Bedrock_v686;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    public static List<Player> players = new ArrayList<>();
    public static HashMap<Player, String> bannedPlayers = new HashMap<>();
    private static int clientIdCounter = 0;
    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {

        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 19132);

        BedrockPong pong = new BedrockPong();
        pong.protocolVersion(getCODEC().getProtocolVersion());
        pong.ipv6Port(19133);
        pong.ipv4Port(19132);
        pong.gameType("Survival");
        pong.maximumPlayerCount(10);
        pong.playerCount(0);
        pong.motd("MOTD");
        pong.subMotd("SUB MOTD");
        pong.version(getCODEC().getMinecraftVersion());
        pong.nintendoLimited(false);
        pong.edition("MCPE");

        new ServerBootstrap()
                .channelFactory(RakChannelFactory.server(NioDatagramChannel.class))
                .option(RakChannelOption.RAK_ADVERTISEMENT, pong.toByteBuf())
                .group(new NioEventLoopGroup())
                .childHandler(new BedrockServerInitializer() {
                    @Override
                    protected void initSession(BedrockServerSession session) {
                        session.setLogging(true);
                        players.clear();
                        // Connection established
                        // Make sure to set the packet codec version you wish to use before sending out packets
                        session.setCodec(getCODEC());
                        // Remember to set a packet handler, so you receive incoming packets
                        session.setPacketHandler(new LoginHandler(session));
                        // By default, the server will use a compatible codec that will read any LoginPacket.
                        // After receiving the LoginPacket, you need to set the correct packet codec for the client and continue.
                    }
                })
                .bind(address)
                .syncUninterruptibly();

        System.out.println("Started on " + getCODEC().getMinecraftVersion());
    }

    // Getters and setters
    public static BedrockCodec getCODEC() {
        return Bedrock_v686.CODEC;
    }

    public static int getClientIdCounter() {
        return clientIdCounter;
    }

    public static void setClientIdCounter(int value) {
        clientIdCounter = value;
    }

    public static void addToClientIdCounter(int value) {
        clientIdCounter += value;
    }
}