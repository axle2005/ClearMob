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
	static File activeConfig;
	static ConfigurationNode rootnode;
	static ConfigurationLoader<CommentedConfigurationNode> configManager;

	List<String> entitylist;

	public Config(ClearMob plugin) {
		this.plugin = plugin;

		// ConfigurationLoader<CommentedConfigurationNode> loader =
		// HoconConfigurationLoader.builder().setPath(potentialFile).build();

		configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

		onLoad(activeConfig, rootnode, configManager, "default");

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
			// AxleRandomThings.log.warn("Error loading " +
			// defaultConfig.getName() +" config");
		}
		this.entitylist = new ArrayList();
		try {
			for (String entity : rootnode.getNode(new Object[] { "EntityList" }).getList(TypeToken.of(String.class))) {
				this.entitylist.add(entity.toLowerCase());
				// plugin.getLogger().info(entity);
			}
		} catch (ObjectMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void onLoad(File defaultConfig, ConfigurationNode config,
			ConfigurationLoader<CommentedConfigurationNode> configManager, String configname) {

	}

	public static void saveConfig(ConfigurationNode config,
			ConfigurationLoader<CommentedConfigurationNode> configManager) {
		try {
			configManager.save(config);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void defaults(File defaultConfig, ConfigurationNode config) {

		List<String> list_entities = new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
				"minecraft:skeleton", "minecraft:creeper", "minecraft:arrow"));

		config.getNode("EntityList").setValue(list_entities);

	}

	public static void setValueString(String node, String child) {
		rootnode.getNode(node).setValue(child);
		save(activeConfig);

	}

	public static void setValueList(String node, List<String> list_entities) {
		rootnode.getNode(node).setValue(list_entities);
		save(activeConfig);

	}

	private void delete() {
		activeConfig = new File(plugin.getConfigDir().toFile(), "ClearMob.conf");
		activeConfig.delete();

	}

	public static void save(File input) {

		configManager = HoconConfigurationLoader.builder().setFile(input).build();

	}

	public static void Reload(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
		try {
			config = configManager.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Path getConfigDir() {
		return defaultConfig;
	}

	public List<String> getEntitylist() {
		this.entitylist = new ArrayList();
		try {
			for (String entity : rootnode.getNode(new Object[] { "EntityList" }).getList(TypeToken.of(String.class))) {
				this.entitylist.add(entity.toLowerCase());
			}
		} catch (ObjectMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entitylist;
	}

}