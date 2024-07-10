package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import com.ramsey.servercontroller.Utils;
import com.ramsey.servercontroller.events.ContainerInteractionEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerEventListener {
    private static final HashMap<Player, ContainerInteraction> listeners = new HashMap<>();

    @SubscribeEvent
    public static void containerOpenEvent(PlayerContainerEvent.Open containerEvent) {
        AbstractContainerMenu container = containerEvent.getContainer();
        Player player = containerEvent.getEntity();

        if(!listeners.containsKey(player)) {
            ContainerInteraction listener = new ContainerInteraction(player);

            container.addSlotListener(listener);
            listeners.put(player, listener);
        }
    }

    @SubscribeEvent
    public static void containerCloseEvent(PlayerContainerEvent.Close containerEvent) {
        AbstractContainerMenu container = containerEvent.getContainer();
        Player player = containerEvent.getEntity();

        ContainerInteraction listener = listeners.get(player);

        if(listener != null) {
            container.removeSlotListener(listener);
            listeners.remove(player);

            ContainerInteractionEvent event = new ContainerInteractionEvent();

            event.uuid = player.getUUID().toString();
            event.playerPosition = player.position();
            event.changes = listener.slotChanges;

            EventCollector.record(event);
        }
    }

    public static class ContainerInteraction implements ContainerListener {
        public final Player player;
        public final LinkedList<SlotChange> slotChanges = new LinkedList<>();

        private ContainerInteraction(Player player) {
            this.player = player;
        }

        @Override
        public void slotChanged(@NotNull AbstractContainerMenu abstractContainerMenu, int slot, @NotNull ItemStack itemStack) {
            String item = Utils.getItemId(itemStack.getItem());
            int count = itemStack.getCount();

            slotChanges.add(new SlotChange(item, slot, count));
        }

        @Override
        public void dataChanged(@NotNull AbstractContainerMenu abstractContainerMenu, int i, int i1) { }

        public static class SlotChange {
            public final String item;
            public final int slot;
            public final int count;

            public SlotChange(String item, int slot, int count) {
                this.item = item;
                this.slot = slot;
                this.count = count;
            }
        }
    }
}
