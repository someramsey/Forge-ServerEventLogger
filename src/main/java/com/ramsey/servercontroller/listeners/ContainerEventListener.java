package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.ServerControllerMain;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerEventListener {
    private static final HashMap<AbstractContainerMenu, ContainerInteraction> listeners = new HashMap<>();

    @SubscribeEvent
    public static void containerOpenEvent(PlayerContainerEvent.Open containerEvent) {
        AbstractContainerMenu container = containerEvent.getContainer();
        ContainerInteraction listener = new ContainerInteraction(containerEvent.getEntity());

        container.addSlotListener(listener);
        listeners.put(container, listener);

        System.out.println(containerEvent);
        System.out.println(containerEvent.getContainer());
    }

    @SubscribeEvent
    public static void containerCloseEvent(PlayerContainerEvent.Close containerEvent) {
        AbstractContainerMenu container = containerEvent.getContainer();
        ContainerInteraction listener = listeners.get(container);

        container.removeSlotListener(listener);

        System.out.println(containerEvent);
        System.out.println(containerEvent.getContainer());
    }

    private static class ContainerInteraction implements ContainerListener {
        public final Player player;
        public final LinkedList<SlotChange> slotChanges = new LinkedList<>();

        private ContainerInteraction(Player player) {
            this.player = player;
        }

        @Override
        public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {
            slotChanges.add(new SlotChange(abstractContainerMenu, i, itemStack));
        }

        @Override
        public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int i1) { }

        private static class SlotChange {
            private final AbstractContainerMenu abstractContainerMenu;
            private final int slot;
            private final ItemStack item;

            private SlotChange(AbstractContainerMenu abstractContainerMenu, int slot, ItemStack item) {
                this.abstractContainerMenu = abstractContainerMenu;
                this.slot = slot;
                this.item = item;
            }
        }
    }
}
