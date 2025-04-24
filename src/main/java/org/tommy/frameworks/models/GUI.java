package org.tommy.frameworks.models;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.tommy.frameworks.models.listeners.GUIClickListener;
import org.tommy.frameworks.models.listeners.GUICloseListener;

import java.util.HashSet;

public abstract class GUI {

    private final String id;
    private final Inventory inventory;
    private final HashSet<GUIClickListener> guiClickListeners;
    private final HashSet<GUICloseListener> guiCloseListeners;

    protected GUI(String id, int size, String title) {
        this.id = id;
        this.inventory = Bukkit.createInventory(new GUIHolder(id), size, title);
        this.guiClickListeners = new HashSet<>();
        this.guiCloseListeners = new HashSet<>();
    }

    protected GUI(String id, int size, Component componentTitle) {
        this.id = id;
        this.inventory = Bukkit.createInventory(new GUIHolder(id), size, componentTitle);
        this.guiClickListeners = new HashSet<>();
        this.guiCloseListeners = new HashSet<>();
    }

    public String getId() {
        return this.id;
    }

    public void addClickListener(GUIClickListener guiClickListener) {
        this.guiClickListeners.add(guiClickListener);
    }

    public void handleClick(InventoryClickEvent event, Player player, int slot) {
        for (GUIClickListener guiClickListener : this.guiClickListeners) {
            guiClickListener.onClick(event, this.inventory, player, slot);
        }
    }

    public void addCloseListener(GUICloseListener guiCloseListener) {
        this.guiCloseListeners.add(guiCloseListener);
    }

    public void handleClose(Player player, InventoryCloseEvent.Reason reason) {
        for (GUICloseListener guiCloseListener : this.guiCloseListeners) {
            guiCloseListener.onClose(this.inventory, player, reason);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}