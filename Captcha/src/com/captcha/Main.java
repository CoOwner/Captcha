package com.captcha;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.captcha.listeners.Listeners;
import com.cloutteam.samjakob.gui.types.PaginatedGUI;


public class Main extends JavaPlugin implements Listener {
	
	public static Main plugin;
	private File dataFile;
	private FileConfiguration data;
	
	@Override
	public void onEnable() {
		plugin = this;
		createCustomData();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		PaginatedGUI.prepare(this);
		System.out.println("Captcha Enabled");
	}
	
	public void onDisable() {
		System.out.println("Captcha Disabled");
	}
	
	public FileConfiguration getCustomData() {
    	return this.data;
    }
	
	private void createCustomData() {
    	dataFile = new File(getDataFolder(), "data.yml");
    	if (!dataFile.exists()) {
    		dataFile.getParentFile().mkdirs();
    		saveResource("data.yml", false);
    	}
    	
    	data = new YamlConfiguration();
    	try {
    		data.load(dataFile);
    	} catch (IOException | InvalidConfigurationException e) {
    		e.printStackTrace();
    	}
    }
	
	public static Main get() {
		return plugin;
	}
	
	public void saveConfig() {
        try {
            this.data.save(this.dataFile);
        } catch (IOException e) {
        	getLogger().warning("Unable to save data.yml");
        }
    }
 
    public void reloadConfig() {
        this.data = YamlConfiguration.loadConfiguration(this.dataFile);
    }

}
