package org.tommy.frameworks;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.tommy.frameworks.models.GUI;
import org.tommy.frameworks.models.GUIHolder;
import org.tommy.frameworks.models.types.PaperGUI;
import org.tommy.frameworks.models.types.SpigotGUI;
import org.tommy.frameworks.options.GUIFrameworkOptions;

import java.util.HashMap;
import java.util.Map;

public class GUIFramework implements Listener {

    private final boolean autoCancelClicks;

    private final Map<String, GUI> storedGuis;

    private GUIFramework() {
        this.autoCancelClicks = false;
        this.storedGuis = new HashMap<>();
    }

    private GUIFramework(boolean autoCancelClicks) {
        this.autoCancelClicks = autoCancelClicks;
        this.storedGuis = new HashMap<>();
    }

    public GUIFramework init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }

    public GUI getGuiById(String id) {
        return this.storedGuis.get(id);
    }

    public GUI createSpigotGUI(String id, int size, String title) {
        GUI spigotGui = new SpigotGUI(id, size, title);
        this.storedGuis.put(id, spigotGui);
        return spigotGui;
    }

    public GUI createPaperGUI(String id, int size, Component componentTitle) {
        GUI paperGui = new PaperGUI(id, size, componentTitle);
        this.storedGuis.put(id, paperGui);
        return paperGui;
    }

    public <T extends GUI> T createCustomGUI(T customGui) {
        this.storedGuis.put(customGui.getId(), customGui);
        return customGui;
    }

    public void removeGUI(String id) {
        this.storedGuis.remove(id);
    }

    public static GUIFramework withDefaultOptions() {
        return new GUIFramework();
    }

    public static GUIFramework withOptions(GUIFrameworkOptions guiFrameworkOptions) {
        return new GUIFramework(guiFrameworkOptions.isAutoCancelClicks());
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof GUIHolder guiHolder) {
            GUI gui = storedGuis.get(guiHolder.getId());
            if (gui != null) {
                if (autoCancelClicks) event.setCancelled(true);
                gui.handleClick(event, (Player) event.getWhoClicked(), event.getSlot());
            }
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof GUIHolder guiHolder) {
            GUI gui = storedGuis.get(guiHolder.getId());
            if (gui != null) {
                gui.handleClose((Player) event.getPlayer(), event.getReason());
            }
        }
    }
}