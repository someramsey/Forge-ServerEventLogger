package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import com.ramsey.servercontroller.events.BlockBreakEvent;
import com.ramsey.servercontroller.events.BlockPlaceEvent;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockEventListener {
    @SubscribeEvent
    public static void blockPlaceEvent(BlockEvent.EntityPlaceEvent blockEvent) {
        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent();

        Entity entity = blockEvent.getEntity();

        if (entity == null) {
            return;
        }

        blockPlaceEvent.uuid = entity.getStringUUID();
        blockPlaceEvent.position = blockEvent.getPos();
        blockPlaceEvent.block = Registry.BLOCK.getKey(blockEvent.getPlacedBlock().getBlock()).toString();

        EventCollector.add(blockPlaceEvent);
    }

    @SubscribeEvent
    public static void blockBreakEvent(BlockEvent.BreakEvent blockEvent) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent();

        blockBreakEvent.uuid = blockEvent.getPlayer().getStringUUID();
        blockBreakEvent.position = blockEvent.getPos();
        blockBreakEvent.block = Registry.BLOCK.getKey(blockEvent.getState().getBlock()).toString();

        EventCollector.add(blockBreakEvent);
    }
}
