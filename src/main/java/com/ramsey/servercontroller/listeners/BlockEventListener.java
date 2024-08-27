package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import com.ramsey.servercontroller.Utils;
import com.ramsey.servercontroller.events.BlockChangeEvent;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockEventListener {
    @SubscribeEvent
    public static void blockPlaceEvent(BlockEvent.EntityPlaceEvent blockEvent) {
        Entity entity = blockEvent.getEntity();

        if (entity instanceof Player) {
            BlockChangeEvent blockPlaceEvent = new BlockChangeEvent(BlockChangeEvent.BlockEventType.BLOCK_PLACE);

            blockPlaceEvent.uuid = entity.getStringUUID();
            blockPlaceEvent.position = blockEvent.getPos();
            blockPlaceEvent.block = Utils.getBlockId(blockEvent.getPlacedBlock().getBlock());

            EventCollector.record(blockPlaceEvent);
        }
    }

    @SubscribeEvent
    public static void blockBreakEvent(BlockEvent.BreakEvent blockEvent) {
        BlockChangeEvent blockBreakEvent = new BlockChangeEvent(BlockChangeEvent.BlockEventType.BLOCK_BREAK);

        blockBreakEvent.uuid = blockEvent.getPlayer().getStringUUID();
        blockBreakEvent.position = blockEvent.getPos();
        blockBreakEvent.block = Registry.BLOCK.getKey(blockEvent.getState().getBlock()).toString();

        EventCollector.record(blockBreakEvent);
    }


}
