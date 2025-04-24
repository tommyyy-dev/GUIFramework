package org.example.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.tommy.frameworks.GUIFramework;
import org.tommy.frameworks.models.GUI;
import org.tommy.frameworks.options.GUIFrameworkOptions;

import java.util.Objects;

public final class ExamplePlugin extends JavaPlugin {

    private GUIFramework guiFramework;

    @Override
    public void onEnable() {
        guiFramework = GUIFramework.withOptions(new GUIFrameworkOptions()
                .setAutoCancelClicks(true)).init(this);
        GUI gui = guiFramework.createPaperGUI("warp-menu", 9, "§6Warps");
        gui.getInventory().setItem(4, new ItemStack(Material.ENDER_PEARL));
        gui.addClickListener((event, inventory, player, slot) -> {
            if (slot == 4) {
                Location spawn = Bukkit.getWorld("spawn").getSpawnLocation();
                player.teleport(spawn);
                player.sendMessage("§7You were teleported to the spawn!");
                player.closeInventory();
            }
        });
        Objects.requireNonNull(getCommand("warp")).setExecutor((sender, command, label, args) -> {
           if (sender instanceof Player player) {
               player.openInventory(gui.getInventory());
           }
           return true;
        });
    }
}