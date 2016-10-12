package io.github.axle2005.clearmob;


import java.io.File;

import org.slf4j.Logger;


import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;

public class Basic {
	private Logger logger;
	private File file;
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	private ConfigurationNode config;
	
	private ClearMob plugin;
	
	public Basic(ClearMob plugin){
		this.plugin = plugin;
		
		setupDefaultConfig();
		
		
	}
	
	private void setupDefaultConfig() {
		file = new File(plugin.getConfigDir().toFile(), "ClearMob.conf");
		configManager = HoconConfigurationLoader.builder().setFile(file).build();
		
		Config.onLoad(file, config, configManager, "default");
		
	}
	
	private void delete() {
		file = new File(plugin.getConfigDir().toFile(), "ClearMob.conf");
		file.delete();
		
	}
	
	public void save(File input) {
		
		configManager = HoconConfigurationLoader.builder().setFile(input).build();
		
	}
	
	
	
	
	
	
}



