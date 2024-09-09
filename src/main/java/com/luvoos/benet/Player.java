package com.luvoos.benet;

import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.*;

import java.util.List;

public class Player {

    private BedrockServerSession session;
    private String clientId;
    private boolean moving = false;
    private boolean sneaking = false;
    private Vector3f position;
    private Vector3f rotation;
    private boolean isOnGround;

    public Player(BedrockServerSession session) {
        this.session = session;
    }

    public void sendPacket(BedrockPacket packet) {
        this.session.sendPacket(packet);
    }

    public void sendPacketImmediately(BedrockPacket packet) {
        this.session.sendPacketImmediately(packet);
    }

    /**
     * Send a raw message to player
     * @param message Message string
     */
    public void sendMessage(String message) {
        TextPacket packet = new TextPacket();
        packet.setMessage(message);
        packet.setFilteredMessage("");
        packet.setType(TextPacket.Type.RAW);
        packet.setXuid("");
        packet.setSourceName("");
        packet.setNeedsTranslation(false);
        this.sendPacket(packet);
    }

    public void sendTip(String text) {
        TextPacket packet = new TextPacket();
        packet.setMessage(text);
        packet.setFilteredMessage("");
        packet.setType(TextPacket.Type.TIP);
        packet.setXuid("");
        packet.setSourceName("");
        packet.setNeedsTranslation(false);
        this.sendPacket(packet);
    }

    public void sendPopup(String text) {
        TextPacket packet = new TextPacket();
        packet.setMessage(text);
        packet.setFilteredMessage("");
        packet.setType(TextPacket.Type.POPUP);
        packet.setXuid("");
        packet.setSourceName("");
        packet.setNeedsTranslation(false);
        this.sendPacket(packet);
    }

    public void sendWhisper(String message, String sourceName) {
        TextPacket packet = new TextPacket();
        packet.setMessage(message);
        packet.setSourceName(sourceName);
        packet.setType(TextPacket.Type.WHISPER);
        packet.setXuid("");
        packet.setFilteredMessage("");
        packet.setPlatformChatId("");
        this.sendPacket(packet);
    }

    public void sendChat(String message, String sourceName) {
        TextPacket packet = new TextPacket();
        packet.setPlatformChatId("");
        packet.setXuid("");
        packet.setFilteredMessage("");
        packet.setSourceName(sourceName);
        packet.setMessage(message);
        this.sendPacket(packet);
    }

    public void sendToast(String title, String content) {
        ToastRequestPacket packet = new ToastRequestPacket();
        packet.setTitle(title);
        packet.setContent(content);
        this.sendPacket(packet);
    }

    public void sendTitle(String text, int fadeInTime, int stayTime, int fadeOutTime) {
        SetTitlePacket packet = new SetTitlePacket();
        packet.setText(text);
        packet.setType(SetTitlePacket.Type.TITLE);
        packet.setXuid("");
        packet.setFadeInTime(fadeInTime);
        packet.setFadeOutTime(fadeOutTime);
        packet.setStayTime(stayTime);
        packet.setPlatformOnlineId("");
        this.sendPacket(packet);
    }

    public void sendActionBar(String text, int fadeInTime, int stayTime, int fadeOutTime) {
        SetTitlePacket packet = new SetTitlePacket();
        packet.setText(text);
        packet.setFadeInTime(fadeInTime);
        packet.setFadeOutTime(fadeOutTime);
        packet.setStayTime(stayTime);
        packet.setType(SetTitlePacket.Type.ACTIONBAR);
        packet.setXuid("");
        packet.setPlatformOnlineId("");
        this.sendPacket(packet);
    }

    public void setSpawnPosition(Vector3i spawnPosition, Vector3i blockPosition, int dimensionId) {
        SetSpawnPositionPacket packet = new SetSpawnPositionPacket();
        packet.setSpawnType(SetSpawnPositionPacket.Type.PLAYER_SPAWN);
        packet.setSpawnPosition(spawnPosition);
        packet.setBlockPosition(blockPosition);
        packet.setDimensionId(dimensionId);
        this.sendPacket(packet);
    }

    public void setHealth(int health) {
        SetHealthPacket packet = new SetHealthPacket();
        packet.setHealth(health);
        this.sendPacket(packet);
        this.updateAttributes();
    }

    public void setGameMode(int gameMode) {
        SetPlayerGameTypePacket packet = new SetPlayerGameTypePacket();
        packet.setGamemode(gameMode);
        this.sendPacket(packet);
    }

    public void updateAttributes() {
        UpdateAttributesPacket packet = new UpdateAttributesPacket();
        this.sendPacket(packet);
    }

    public void transfer(String address, int port) {
        TransferPacket packet = new TransferPacket();
        packet.setAddress(address);
        packet.setPort(port);
        this.sendPacket(packet);
    }

    public void transfer(String address) {
        TransferPacket packet = new TransferPacket();
        packet.setAddress(address);
        packet.setPort(19132);
        this.sendPacket(packet);
        System.out.println("Transfer: " + address);
    }

    public void setHudVisibility(HudVisibility visibility, List<HudElement> hudElements) {
        SetHudPacket packet = new SetHudPacket();
        for (HudElement hudElement : hudElements) {
            packet.getElements().add(hudElement);
        }
        packet.setVisibility(visibility);
        this.sendPacket(packet);
    }

    public void updateAbilities(List<AbilityLayer> abilityLayers, PlayerPermission playerPermission, CommandPermission commandPermission, long uniqueEntityId) {
        UpdateAbilitiesPacket packet = new UpdateAbilitiesPacket();
        packet.setAbilityLayers(abilityLayers);
        packet.setPlayerPermission(playerPermission);
        packet.setCommandPermission(commandPermission);
        packet.setUniqueEntityId(uniqueEntityId);
        this.sendPacket(packet);
    }

    public void setTime(int time) {
        SetTimePacket packet = new SetTimePacket();
        packet.setTime(time);
        this.sendPacket(packet);
    }

    public void sendPlayStatus(PlayStatusPacket.Status status) {
        PlayStatusPacket packet = new PlayStatusPacket();
        packet.setStatus(status);
        this.sendPacket(packet);
    }

    public void setAttributes(List<AttributeData> attributes) {
        UpdateAttributesPacket packet = new UpdateAttributesPacket();
        packet.setAttributes(attributes);
        packet.setRuntimeEntityId(2);
        this.sendPacket(packet);
    }

    public void kill() {
        EntityEventPacket packet = new EntityEventPacket();
        packet.setType(EntityEventType.DEATH);
        packet.setData(1);
        packet.setRuntimeEntityId(2);
        this.sendPacket(packet);
    }

    public void kick(String reason) {
        getSession().disconnect(reason);
    }

    public void kick() {
        this.kick("Disconnected");
    }

    public void ban(String reason) {
        Server.bannedPlayers.put(this, reason);
    }

    public void teleport(Vector3f where) {
        MovePlayerPacket packet = new MovePlayerPacket();
        packet.setMode(MovePlayerPacket.Mode.TELEPORT);
        packet.setEntityType(2);
        packet.setPosition(where);
        packet.setRuntimeEntityId(2);
        packet.setRotation(Vector3f.from(0,0,0));
        packet.setOnGround(true);
        packet.setRidingRuntimeEntityId(0);
        packet.setTick(System.currentTimeMillis());
        packet.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        this.sendPacket(packet);
    }

    public void sendPosition(Vector3f pos, double yaw, double pitch, MovePlayerPacket.Mode mode) {
        MovePlayerPacket packet = new MovePlayerPacket();
        packet.setRuntimeEntityId(2);
        packet.setPosition(pos.add(0, 1.62, 0));
        packet.setRotation(Vector3f.from(pitch, yaw, yaw));
        packet.setMode(mode);
        if (mode == MovePlayerPacket.Mode.TELEPORT) {
            packet.setTeleportationCause(MovePlayerPacket.TeleportationCause.BEHAVIOR);
        }
        this.sendPacketImmediately(packet);
    }

    // Getters and setters
    public BedrockServerSession getSession() {
        return session;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setOnGround(boolean onGround) {
        isOnGround = onGround;
    }
}