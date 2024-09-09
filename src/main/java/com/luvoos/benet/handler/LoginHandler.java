package com.luvoos.benet.handler;

import com.luvoos.benet.Player;
import com.luvoos.benet.Server;
import com.luvoos.benet.util.SkinUtil;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginHandler implements BedrockPacketHandler {

    private final BedrockServerSession session;
    public static Player player;
    public static List<String> chainData;

    public LoginHandler(BedrockServerSession session) {
        this.session = session;
    }

    @Override
    public PacketSignal handle(LoginPacket packet) {
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setExtra("");
        loginPacket.setProtocolVersion(Server.getCODEC().getProtocolVersion());
        session.sendPacket(loginPacket);
        chainData = packet.getChain();
        for (String chain : packet.getChain()) {
            System.out.println(chain);
        }

        player = new Player(session);
        Server.players.add(player);

        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        resourcePacksInfoPacket.setHasAddonPacks(false);
        resourcePacksInfoPacket.setScriptingEnabled(false);
        resourcePacksInfoPacket.setForcingServerPacksEnabled(false);
        session.sendPacket(resourcePacksInfoPacket);

        PlayStatusPacket status = new PlayStatusPacket();
        status.setStatus(PlayStatusPacket.Status.LOGIN_SUCCESS);
        session.sendPacket(status);

        session.setCodec(Server.getCODEC());
        session.setCompression(PacketCompressionAlgorithm.ZLIB);
        System.out.println("Handled login");

        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(RequestNetworkSettingsPacket packet) {
        NetworkSettingsPacket networkSettingsPacket = new NetworkSettingsPacket();
        networkSettingsPacket.setCompressionThreshold(0);
        networkSettingsPacket.setCompressionAlgorithm(PacketCompressionAlgorithm.ZLIB);
        session.sendPacketImmediately(networkSettingsPacket);

        session.setCompression(PacketCompressionAlgorithm.ZLIB);
        System.out.println("Handled " + packet);

        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(ClientCacheStatusPacket packet) {
        ClientCacheStatusPacket clientCacheStatusPacket = new ClientCacheStatusPacket();
        clientCacheStatusPacket.setSupported(true);
        session.sendPacket(clientCacheStatusPacket);

        return PacketSignal.HANDLED;
    }

    @Override
    public PacketSignal handle(ResourcePackClientResponsePacket packet) {
        System.out.println(packet.getStatus());
        switch (packet.getStatus()) {
            case HAVE_ALL_PACKS:
                ResourcePackStackPacket resourcePackStackPacket = new ResourcePackStackPacket();
                resourcePackStackPacket.setForcedToAccept(false);
                resourcePackStackPacket.setGameVersion("");
                resourcePackStackPacket.setExperimentsPreviouslyToggled(false);
                session.sendPacket(resourcePackStackPacket);
                break;

            case COMPLETED:
                long uniqueEntityId = 2;
                long runtimeEntityId = 2;
                SerializedSkin skin = SkinUtil.createCustomSkin();

                StartGamePacket startGame = new StartGamePacket();
                startGame.setUniqueEntityId(uniqueEntityId);
                startGame.setRuntimeEntityId(runtimeEntityId);
                startGame.setPlayerGameType(GameType.SURVIVAL);
                startGame.setPlayerPosition(Vector3f.from(0, -1, 0));
                startGame.setRotation(Vector2f.from(0, 0));
                startGame.setSeed(0);
                startGame.setSpawnBiomeType(SpawnBiomeType.DEFAULT);
                startGame.setCustomBiomeName("");
                startGame.setDimensionId(0);
                startGame.setGeneratorId(2);
                startGame.setLevelGameType(GameType.SURVIVAL);
                startGame.setDifficulty(2);
                startGame.setDefaultSpawn(Vector3i.from(0, 1, 0));
                startGame.setAchievementsDisabled(true);
                startGame.setDayCycleStopTime(0);
                startGame.setEduEditionOffers(0);
                startGame.setEduFeaturesEnabled(false);
                startGame.setEducationProductionId("");
                startGame.setRainLevel(0);
                startGame.setLightningLevel(0);
                startGame.setPlatformLockedContentConfirmed(false);
                startGame.setMultiplayerGame(true);
                startGame.setBroadcastingToLan(true);
                startGame.setXblBroadcastMode(GamePublishSetting.PUBLIC);
                startGame.setPlatformBroadcastMode(GamePublishSetting.PUBLIC);
                startGame.setCommandsEnabled(true);
                startGame.setTexturePacksRequired(false);
                startGame.setExperimentsPreviouslyToggled(false);
                startGame.setBonusChestEnabled(false);
                startGame.setStartingWithMap(false);
                startGame.setTrustingPlayers(false);
                startGame.setDefaultPlayerPermission(PlayerPermission.VISITOR);
                startGame.setServerChunkTickRange(4);
                startGame.setBehaviorPackLocked(false);
                startGame.setResourcePackLocked(false);
                startGame.setFromLockedWorldTemplate(false);
                startGame.setUsingMsaGamertagsOnly(false);
                startGame.setFromWorldTemplate(false);
                startGame.setWorldTemplateOptionLocked(false);
                startGame.setOnlySpawningV1Villagers(false);
                startGame.setVanillaVersion("");
                startGame.setLimitedWorldWidth(256);
                startGame.setLimitedWorldHeight(256);
                startGame.setNetherType(false);
                startGame.setEduSharedUriResource(EduSharedUriResource.EMPTY);
                startGame.setChatRestrictionLevel(ChatRestrictionLevel.NONE);
                startGame.setDisablingCustomSkins(false);
                startGame.setDisablingPersonas(false);
                startGame.setDisablingPlayerInteractions(false);
                startGame.setLevelId("");
                startGame.setLevelName("LEVEL NAME");
                startGame.setPremiumWorldTemplateId("0");
                startGame.setTrial(false);
                startGame.setAuthoritativeMovementMode(AuthoritativeMovementMode.CLIENT);
                startGame.setRewindHistorySize(0);
                startGame.setServerAuthoritativeBlockBreaking(false);
                startGame.setCurrentTick(0);
                startGame.setEnchantmentSeed(0);
                startGame.setMultiplayerCorrelationId("1");
                startGame.setInventoriesServerAuthoritative(false);
                startGame.setServerEngine("");
                startGame.setPlayerPropertyData(NbtMap.EMPTY);
                startGame.setBlockRegistryChecksum(0);
                startGame.setWorldTemplateId(UUID.randomUUID());
                startGame.setWorldEditor(false);
                startGame.setClientSideGenerationEnabled(false);
                startGame.setEmoteChatMuted(true);
                startGame.setBlockNetworkIdsHashed(false);
                startGame.setCreatedInEditor(false);
                startGame.setExportedFromEditor(false);
                startGame.setNetworkPermissions(NetworkPermissions.DEFAULT);
                startGame.setForceExperimentalGameplay(OptionalBoolean.empty());
                startGame.setWorldId("");
                startGame.setScenarioId("");
                startGame.setServerId("");
                session.sendPacket(startGame);
                System.out.println("Sent StartGamePacket");

                CreativeContentPacket packet1 = new CreativeContentPacket();
                packet1.setContents(new ItemData[0]);
                session.sendPacket(packet1);

                PlayStatusPacket playStatus = new PlayStatusPacket();
                playStatus.setStatus(PlayStatusPacket.Status.PLAYER_SPAWN);
                session.sendPacket(playStatus);

                ServerSettingsResponsePacket settingsResponsePacket = new ServerSettingsResponsePacket();
                settingsResponsePacket.setFormData("Hey");
                settingsResponsePacket.setFormId(1);
                player.sendPacket(settingsResponsePacket);

                player.sendMessage("§l§3»");
                player.sendToast("§l§aWelcome!", "§7You logged in.");

                PlayerListPacket playerListPacket = new PlayerListPacket();
                playerListPacket.setAction(PlayerListPacket.Action.ADD);

                List<PlayerListPacket.Entry> entries = new ArrayList<>();
                PlayerListPacket.Entry entry = new PlayerListPacket.Entry(UUID.randomUUID());
                entry.setEntityId(2);
                entry.setName("PlayerListEntry");
                entry.setXuid("playerXuid");
                entry.setPlatformChatId("platformChatId");
                entry.setSkin(skin);
                entries.add(entry);
                playerListPacket.getEntries().addAll(entries);
                session.sendPacket(playerListPacket);

                AddEntityPacket entity = new AddEntityPacket();
                entity.setIdentifier("minecraft:iron_golem");
                entity.setEntityType(1);
                entity.setMotion(Vector3f.from(0,0,0));
                entity.setUniqueEntityId(1234);
                entity.setRuntimeEntityId(3);
                entity.setRotation(Vector2f.from(0,0));
                entity.setPosition(Vector3f.from(0,0,0));
                entity.setHeadRotation(1);
                entity.setBodyRotation(1);
                session.sendPacket(entity);

                List<HudElement> elements = new ArrayList<>();
                elements.add(HudElement.AIR_BUBBLES_BAR);
                player.setHudVisibility(HudVisibility.HIDE, elements);

                session.setPacketHandler(new PacketHandler(session));
                break;
        }

        return PacketSignal.HANDLED;
    }

}
