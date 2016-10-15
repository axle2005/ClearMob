package io.github.axle2005.clearmob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class Config {

	ClearMob plugin;

	Path defaultConfig;
	File activeConfig;
	ConfigurationNode rootnode;
	ConfigurationLoader<CommentedConfigurationNode> configManager;

	List<String> entitylist;

	public Config(ClearMob plugin) {
		this.plugin = plugin;
		configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

	}

	Config(ClearMob plugin, Path defaultConfig, ConfigurationLoader<CommentedConfigurationNode> configManager) {
		this.plugin = plugin;
		this.defaultConfig = defaultConfig;
		this.configManager = configManager;

		activeConfig = new File(getConfigDir().toFile(), "ClearMob.conf");

		configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

		try {

			rootnode = configManager.load();
			if (!activeConfig.exists()) {
				defaults(activeConfig, rootnode);
				saveConfig(rootnode, configManager);

			}

		}
 
		catch (IOException e) {
			e.printStackTrace();
		} 
		entitylist = getEntitylist();
	}
	


	public void saveConfig(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
		try {
			configManager.save(config);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void defaults(File defaultConfig, ConfigurationNode config) {

		List<String> listEntities = new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
				"minecraft:skeleton", "minecraft:creeper", "minecraft:arrow"));

		config.getNode("EntityList").setValue(listEntities);

	}

	private void delete() {
		activeConfig = new File(plugin.getConfigDir().toFile(), "ClearMob.conf");
		activeConfig.delete();

	}
	public void reload()
	{
		CommandExec exec = new CommandExec(plugin, getEntitylist());
		
	}
	public void save(File input) {

		configManager = HoconConfigurationLoader.builder().setFile(input).build();

	}

	public Path getConfigDir() {
		return defaultConfig;
	}


	
	public List<String> getEntitylist() {
		

		activeConfig = new File(getConfigDir().toFile(), "ClearMob.conf");

		configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

		try {

			rootnode = configManager.load();
			if (!activeConfig.exists()) {
				defaults(activeConfig, rootnode);
				saveConfig(rootnode, configManager);

			}

		}
 
		catch (IOException e) {
			e.printStackTrace();
		} 
		entitylist = new ArrayList<String>();
		try {
			for (String entity : rootnode.getNode(new Object[] { "EntityList" }).getList(TypeToken.of(String.class))) {
				entitylist.add(entity.toLowerCase());
				//plugin.getLogger().info(entity);
			}
		} catch (ObjectMappingException e) {
			e.printStackTrace();
		}
		return entitylist;
	}
	
	public void setValueString(String node, String child) {
		rootnode.getNode(node).setValue(child);
		save(activeConfig);

	}

	public void setValueList(String node, List<String> list_entities) {
		rootnode.getNode(node).setValue(list_entities);
		save(activeConfig);

	}

}