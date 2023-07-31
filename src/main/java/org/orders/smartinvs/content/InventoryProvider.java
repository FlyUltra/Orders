package org.orders.smartinvs.content;

import org.bukkit.entity.Player;

public interface InventoryProvider {

    void init(Player player, String figureId, InventoryContents contents);
    default void update(Player player, InventoryContents contents) {}

}