package org.tommy.frameworks.models;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class GUIHolder implements InventoryHolder {

    private final String id;
    private Inventory inventory;

    public GUIHolder(String id) {
        this.id = id;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getId() {
        return id;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}