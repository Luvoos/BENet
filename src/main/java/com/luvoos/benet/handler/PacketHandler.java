package com.luvoos.benet.handler;

import com.luvoos.benet.Player;
import com.luvoos.benet.Server;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.PacketSignal;

public class PacketHandler implements BedrockPacketHandler {

    //@Override
    //    public PacketSignal handle() {
    //
    //        return PacketSignal.HANDLED;
    //    }

    private final BedrockServerSession session;
    private Player player = LoginHandler.player;

    public PacketHandler(BedrockServerSession session) {
        this.session = session;
    }

    @Override
    public PacketSignal handle(MovePlayerPacket packet) {
        //player.setPosition(packet.getPosition());
        //        player.setRotation(packet.getRotation());
        //        player.setOnGround(packet.isOnGround());
        //        if (!player.isSneaking()) {
        //
        //        } else {
        //            MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        //            movePlayerPacket.setPosition(player.getPosition().sub(0,0.2,0));
        //            movePlayerPacket.setOnGround(player.isOnGround());
        //            movePlayerPacket.setEntityType(1);
        //            movePlayerPacket.setRotation(player.getRotation());
        //            movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        //            movePlayerPacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        //            movePlayerPacket.setTick(System.currentTimeMillis());
        //            movePlayerPacket.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        //            movePlayerPacket.setRidingRuntimeEntityId(0);
        //            player.sendPacketImmediately(movePlayerPacket);
        //        }



        //Vector3f newPos = packet.getPosition().sub(0, 1.62, 0);
        //        Vector3f currentPos = packet.getPosition();
        //
        //        float yaw = packet.getRotation().getY() % 360;
        //        float pitch = packet.getRotation().getX() % 360;
        //
        //        if (yaw < 0) {
        //            yaw += 360;
        //        }
        //
        //        if (packet.getMode()== MovePlayerPacket.Mode.NORMAL){
        //            if (currentPos.distance(newPos) > 0) {
        //            player.sendPosition(currentPos, yaw, pitch, MovePlayerPacket.Mode.RESPAWN);
        //            return PacketSignal.HANDLED;
        //            }
        //        }

        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(TextPacket packet) {
        for (Player playerr : Server.players) {
            playerr.sendMessage("<" + packet.getSourceName() + "> " + packet.getMessage());
        }
        switch (packet.getMessage()) {
            case "clientold":
                player.sendPlayStatus(PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD);
                break;
            case "kill":
                player.kill();
                break;
            case "kickself":
                player.kick("§cYou were disconnected!");
                break;
            case "tp":
                player.teleport(Vector3f.from(2,5,2));
                break;
            case "toast":
                player.sendToast("§l§aTOAST TITLE STRING IS SHOWN HERE!", "§7The content string is shown here.");
                break;
            case "transfer":
                player.transfer("15.235.9.18", 19132);
        }
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(RequestChunkRadiusPacket packet) {
        ChunkRadiusUpdatedPacket responsePacket = new ChunkRadiusUpdatedPacket();
        responsePacket.setRadius(packet.getRadius());
        session.sendPacket(responsePacket);
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(EmoteListPacket packet) {
        EmoteListPacket responsePacket = new EmoteListPacket();
        responsePacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        session.sendPacket(responsePacket);
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(SetLocalPlayerAsInitializedPacket packet) {
        System.out.println("A player was locally initialized!");
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(ServerSettingsRequestPacket packet) {
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(AnimatePacket packet) {
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(PlayerActionPacket packet) {
        //switch (packet.getAction()) {
        //            case START_SNEAK:
        //                player.setSneaking(true);
        //                break;
        //            case STOP_SNEAK:
        //                player.setSneaking(false);
        //                break;
        //        }
        //        if (!player.isSneaking()) {
        //
        //        } else {
        //            MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        //            movePlayerPacket.setPosition(player.getPosition().sub(0,0.2,0));
        //            movePlayerPacket.setOnGround(player.isOnGround());
        //            movePlayerPacket.setEntityType(1);
        //            movePlayerPacket.setRotation(player.getRotation());
        //            movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        //            movePlayerPacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        //            movePlayerPacket.setTick(System.currentTimeMillis());
        //            movePlayerPacket.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        //            movePlayerPacket.setRidingRuntimeEntityId(0);
        //            player.sendPacketImmediately(movePlayerPacket);
        //        }
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(InteractPacket packet) {
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(CommandRequestPacket packet) {
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(SettingsCommandPacket packet) {
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(MobEquipmentPacket packet) {
        ItemData item = packet.getItem();
        MobEquipmentPacket responsePacket = new MobEquipmentPacket();
        responsePacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        responsePacket.setContainerId(packet.getContainerId());
        responsePacket.setItem(item);
        responsePacket.setHotbarSlot(packet.getHotbarSlot());
        responsePacket.setInventorySlot(packet.getInventorySlot());
        session.sendPacket(responsePacket);
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(RespawnPacket packet) {
        RespawnPacket responsePacket = new RespawnPacket();
        responsePacket.setPosition(packet.getPosition());
        responsePacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        responsePacket.setState(RespawnPacket.State.SERVER_READY);
        session.sendPacket(responsePacket);
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(InventoryTransactionPacket packet) {
        player.sendTip("INVENTORY TRANSACTION!");
        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(EmotePacket packet) {
        return PacketSignal.HANDLED;
    }
}
