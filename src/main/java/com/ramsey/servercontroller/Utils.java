package com.ramsey.servercontroller;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class Utils {
    public static String getBlockId(Block block) {
        return Registry.BLOCK.getKey(block).toString();
    }

    public static String getItemId(Item item) {
        return Registry.ITEM.getKey(item).toString();
    }
}
