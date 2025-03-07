package xyz.damt.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(String name, JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder().getAbsolutePath(), name + ".yml");

        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }

        load();
        save();
    }

    public void load() {
        try {
            this.load(this.file);
        } catch (InvalidConfigurationException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reload() {
      this.load();
      this.save();
    }

}
