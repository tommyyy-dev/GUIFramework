# GUIFramework

GUIFramework is a simple and flexible framework designed for creating inventory-based GUI menus for Spigot & Paper. It allows you to easily manage GUI interactions without dealing with the low-level event handling, providing an intuitive API for developers.

## 📦 Installation

### **Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.tommyyy-dev</groupId>
    <artifactId>GUIFramework</artifactId>
    <version>1.0.0</version>
</dependency>

<build>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.6.0</version>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <relocations>
                <relocation>
                    <pattern>com.github.tommyyy-dev</pattern>
                    <shadedPattern>YOUR.PLUGIN.PACKAGE.libs.</shadedPattern>  <!-- CHANGE THIS -->
                </relocation>
            </relocations>
        </configuration>
    </plugin>
</plugins>
</build>
```

### **Gradle (Kotlin DSL)**
```kotlin
plugins {
    id("com.gradleup.shadow") version "8.3.6"
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.tommyyy-dev:GUIFramework:1.0.0")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        val mapping = mapOf("com.github.tommy-dev" to "guiframework")
        val base = "YOUR.PLUGIN.PACKAGE.libs." // CHANGE THIS
        for ((pattern, name) in mapping) relocate(pattern, "${base}${name}")
        mergeServiceFiles()
    }
}
```

## 🚀 Getting Started

### **1. Create a GUIFramework instance**
You can easily create a GUIFramework instance with or without custom options. Make sure you call the `GUIFramework#init(Plugin plugin)` method at least once in your code to register the events. It's best to do this right at the beginning!

```java
private GUIFramework guiFramework;

@Override
public void onEnable() {
    // With custom options
    guiFramework = GUIFramework.withOptions(new GUIFrameworkOptions().setAutoCancelClicks(true)).init(this);
    // With default options
    guiFramework = GUIFramework.withDefaultOptions().init(this);
}
```

### **2. Create a GUI**
You can easily create a GUI using the GUIFramework. Depending on whether you're using Paper or Spigot, you should choose the appropriate method.

```java
// If you use Paper
GUI gui = guiFramework.createPaperGUI("warp-menu", 9, MiniMessage.miniMessage().deserialize("<gold>Warps</gold>"));
// If you use Spigot
GUI gui = guiFramework.createSpigotGUI("warp-menu", 9, "§6Warps");
```

### **3. Fill the GUI**
You can easily get the inventory using the `GUI#getInventory` method. Then, just like without the framework, you add the items to the inventory.

```java
gui.getInventory().setItem(4, new ItemStack(Material.ENDER_PEARL));
```

### **4. Add Click Listeners**
With the GUIFramework, listening for clicks is super easy. No need for listeners, and no annoying queries about which GUI you're dealing with. Just use the `GUI#addClickListener` method :)

```java
gui.addClickListener((event, inventory, player, slot) -> {
    if (slot == 4) {
        Location spawn = Bukkit.getWorld("spawn").getSpawnLocation();
        player.teleport(spawn);
        player.sendMessage("§7You were teleported to the spawn!");
        player.closeInventory();
    }
});
```

Depending on whether you have configured the GUIFramework to automatically cancel click events or not, you do not need to cancel the event.

### **5. Add Close Listeners**
Listening for GUI closures is also super easy. No extra listeners are required, and no annoying queries are needed to determine which GUI is being closed. Just use the `GUI#addCloseListener` method :D

```java
gui.addCloseListener((inventory, player, reason) -> {
    Bukkit.getLogger().info(player.getName() + " closed the GUI: " + inventory.getTitle());
});
```

### **6. Open the GUI for a Player**
Opening a GUI for a player is relatively self-explanatory. Use Bukkit's standard `Player#openInventory(Inventory inventory)` method and specify `gui.getInventory()` as the inventory.

```java
player.openInventory(gui.getInventory());
```

## ❓ Need Help?
If you need assistance, feel free to open an issue on this repository or join our community [Discord](https://discord.com/invite/BKsZxh4D6W)!
