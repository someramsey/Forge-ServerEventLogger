package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import com.ramsey.servercontroller.events.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEventListener {
    @SubscribeEvent
    public static void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent playerEvent) {
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent();

        Player player = playerEvent.getEntity();

        playerJoinEvent.uuid = player.getStringUUID();
        playerJoinEvent.level = player.level.dimension().location().toString();
        playerJoinEvent.position = player.position();
        playerJoinEvent.name = player.getName().getString();

        EventCollector.record(playerJoinEvent);
    }

    @SubscribeEvent
    public static void playerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent playerEvent) {
        PlayerLeaveEvent playerLeaveEvent = new PlayerLeaveEvent();

        Player player = playerEvent.getEntity();

        playerLeaveEvent.uuid = player.getStringUUID();
        playerLeaveEvent.position = player.position();

        EventCollector.record(playerLeaveEvent);
    }

    @SubscribeEvent
    public static void playerChangeLevelEvent(PlayerEvent.PlayerChangedDimensionEvent playerEvent) {
        PlayerChangeLevelEvent playerChangeLevelEvent = new PlayerChangeLevelEvent();

        Player player = playerEvent.getEntity();

        playerChangeLevelEvent.uuid = player.getStringUUID();
        playerChangeLevelEvent.position = player.position();
        playerChangeLevelEvent.level = playerEvent.getTo().location().toString();

        EventCollector.record(playerChangeLevelEvent);
    }

    @SubscribeEvent
    public static void playerChatEvent(ServerChatEvent.Submitted chatEvent) {
        PlayerChatEvent playerChatEvent = new PlayerChatEvent();

        playerChatEvent.uuid = chatEvent.getPlayer().getStringUUID();
        playerChatEvent.message = chatEvent.getRawText();

        EventCollector.record(playerChatEvent);
    }

    @SubscribeEvent
    public static void playerDeathEvent(LivingDeathEvent deathEvent) {
        Entity entity = deathEvent.getEntity();

        if (entity.level.isClientSide()) {
            return;
        }

        if (entity instanceof Player player) {
            PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent();

            playerDeathEvent.uuid = player.getStringUUID();
            playerDeathEvent.position = player.position();
            playerDeathEvent.cause = deathEvent.getSource().msgId;

            EventCollector.record(playerDeathEvent);
        }
    }

    @SubscribeEvent
    public static void playerSpawnEvent(PlayerEvent.PlayerRespawnEvent spawnEvent) {
        PlayerSpawnEvent playerSpawnEvent = new PlayerSpawnEvent();

        Player player = spawnEvent.getEntity();

        playerSpawnEvent.position = player.position();
        playerSpawnEvent.uuid = player.getStringUUID();
        playerSpawnEvent.level = player.level.dimension().location().toString();

        EventCollector.record(playerSpawnEvent);
    }
}
