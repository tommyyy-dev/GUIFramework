package org.tommy.frameworks.models.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface GUIClickListener {

    void onClick(InventoryClickEvent event, Inventory inventory, Player player, int slot);
}