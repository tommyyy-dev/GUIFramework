package org.tommy.frameworks.models.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface GUICloseListener {

    void onClose(Inventory inventory, Player player, InventoryCloseEvent.Reason reason);
}