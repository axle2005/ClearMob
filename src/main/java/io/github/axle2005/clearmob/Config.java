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
    CommentedConfigurationNode rootnode;
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    List<String> entitylist;
    List<String> listEntityDefaults = new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
	    "minecraft:skeleton", "minecraft:creeper", "minecraft:arrow"));
    List<String> listTileDefaults = new ArrayList<String>(Arrays.asList("PlaceHolder"));
    List<String> listItemDefaults = new ArrayList<String>(Arrays.asList("minecraft:redstone", "minecraft:diamond"));

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
		saveConfig(rootnode, configManager);

	    }
	    convertConfigToNew();

	    if (rootnode.getNode("Warning", "Enabled").isVirtual() == true) {
		rootnode.getNode("Warning", "Enabled").setValue(true);
	    }
	    if (rootnode.getNode("Warning", "Messages", "Warning").isVirtual() == true) {
		rootnode.getNode("Warning", "Messages", "Warning").setValue("[ClearMob] Clearing Entities in 1 minute");
	    }
	    if (rootnode.getNode("Warning", "Messages", "Cleared").isVirtual() == true) {
		rootnode.getNode("Warning", "Messages", "Cleared").setValue("[ClearMob] Entities have been cleared");
	    }
	    if (rootnode.getNode("Clearing", "Lists", "EntityList").isVirtual() == true) {
		rootnode.getNode("Clearing", "Lists", "EntityList").setValue(listEntityDefaults);
	    }
	    if (rootnode.getNode("Clearing", "Lists", "TileEntityList").isVirtual() == true) {
		rootnode.getNode("Clearing", "Lists", "TileEntityList").setValue(listTileDefaults);
	    }
	    if (rootnode.getNode("Clearing", "Lists", "ItemList").isVirtual() == true) {
		rootnode.getNode("Clearing", "Lists", "ItemList")
			.setValue(listItemDefaults);
	    }
	    if (rootnode.getNode("Clearing", "Interval").isVirtual() == true) {
		rootnode.getNode("Clearing", "Interval").setValue(60);
	    }
	    if (rootnode.getNode("Clearing", "PassiveMode").isVirtual() == true) {
		rootnode.getNode("Clearing", "PassiveMode").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "KillAllMonsters").isVirtual() == true) {
		rootnode.getNode("Clearing", "KillAllMonsters").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "KillDrops", "Enabled").isVirtual() == true) {
		rootnode.getNode("Clearing", "KillDrops", "Enabled").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "KillDrops", "ListType").isVirtual() == true) {
		rootnode.getNode("Clearing", "KillDrops", "ListType").setComment("Accepts 'WhiteList' or 'BlackList', Whitelist will remove everything on list, Blacklist does reverse").setValue("BlackList");
	    }
	    if (rootnode.getNode("Clearing", "KillAnimalGroups").isVirtual() == true) {
		rootnode.getNode("Clearing", "KillAnimalGroups").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "CrashMode").isVirtual() == true) {
		rootnode.getNode("Clearing", "CrashMode").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "MobLimiter", "Enabled").isVirtual() == true) {
		rootnode.getNode("Clearing", "MobLimiter", "Enabled").setValue(false);
	    }
	    if (rootnode.getNode("Clearing", "MobLimiter", "Limit").isVirtual() == true) {
		rootnode.getNode("Clearing", "MobLimiter", "Limit").setValue(500);
	    }

	    // Convert config to version 1.3.4 (Removal of ListTypes)
	    convertTo134();

	    saveConfig(rootnode, configManager);
	}

	catch (IOException e) {
	    e.printStackTrace();
	}
	entitylist = getEntitylist();
	// saveConfig(rootnode, configManager);
	save(activeConfig);
    }

    private void convertTo134() {
	if (rootnode.getNode("Clearing", "Lists", "ItemBlackList").isVirtual() == false) {

	    rootnode.getNode("Clearing", "Lists", "ItemBlackList").setComment("");
	    rootnode.getNode("Clearing", "Lists", "ItemList")
		    .setValue(rootnode.getNode("Clearing", "Lists", "ItemBlackList").getValue());
	    rootnode.getNode("Clearing", "Lists").removeChild("ItemBlackList");
	}

	if (!rootnode.getNode("Clearing", "Lists", "TileEntityList").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "Lists", "TileEntityList").setComment("Tile Entities on list get removed");
	}
	if (!rootnode.getNode("Clearing", "Lists", "EntityList").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "Lists", "EntityList").setComment("Entities on list get removed");
	}
	if (!rootnode.getNode("Clearing", "KillAllMonsters").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "KillAllMonsters").setComment("Removes all Monsters if enabled");
	}
	if (!rootnode.getNode("Clearing", "KillAnimalGroups").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "KillAnimalGroups")
		    .setComment("Removes Animals if there are more than 20 in 3 block radius. (Down to 20)");
	}
	if (!rootnode.getNode("Clearing", "KillDrops").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "KillDrops")
		    .setComment("Removes all Items on ground except ones on Blacklist");
	}
	if (!rootnode.getNode("Clearing", "MobLimiter").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "MobLimiter").setComment("Limits Mob Spawning (Bosses can still spawn");
	}
	if (!rootnode.getNode("Clearing", "Interval").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "Interval").setComment("Time in seconds for Passive mode to run");
	}
	if (!rootnode.getNode("Clearing", "PassiveMode").getComment().isPresent()) {
	    rootnode.getNode("Clearing", "PassiveMode").setComment("Allows server to automatically clear entities");
	}
	if (!rootnode.getNode("Warning").getComment().isPresent()) {
	    rootnode.getNode("Warning")
		    .setComment("Broadcasts a Message 60 seconds before Entities are cleared. (Can be colored)");
	}
    }

    private void convertConfigToNew() {
	if (rootnode.getNode("Warning", "Message").isVirtual() == false) {

	    rootnode.getNode("Warning", "Messages")
		    .setValue(rootnode.getNode("Warning", "Message", "Warning").getValue());
	    rootnode.getNode("Warning").removeChild("Message");
	}

	if (rootnode.getNode("Interval").isVirtual() == false) {

	    rootnode.getNode("Clearing", "Interval").setValue(rootnode.getNode("Interval").getValue());
	    rootnode.removeChild("Interval");
	}
	if (rootnode.getNode("PassiveMode").isVirtual() == false) {

	    rootnode.getNode("Clearing", "PassiveMode").setValue(rootnode.getNode("PassiveMode").getValue());
	    rootnode.removeChild("PassiveMode");
	}
	if (rootnode.getNode("EntityList").isVirtual() == false) {
	    rootnode.getNode("Clearing", "EntityList").setValue(rootnode.getNode("EntityList").getValue());
	    rootnode.removeChild("EntityList");
	}
	if (rootnode.getNode("Clearing", "EntityList").isVirtual() == false) {
	    rootnode.getNode("Clearing", "Lists", "EntityList")
		    .setValue(rootnode.getNode("Clearing", "EntityList").getValue());
	    rootnode.getNode("Clearing").removeChild("EntityList");
	}
    }

    public void saveConfig(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
	try {
	    configManager.save(config);
	} catch (IOException e) {

	    e.printStackTrace();
	}
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
		// defaults(activeConfig, rootnode);
		saveConfig(rootnode, configManager);

	    }

	}

	catch (IOException e) {
	    e.printStackTrace();
	}
	entitylist = new ArrayList<String>();
	try {
	    for (String entity : rootnode.getNode("Clearing", "Lists", "EntityList")
		    .getList(TypeToken.of(String.class))) {
		entitylist.add(entity.toLowerCase());
		// plugin.getLogger().info(entity);
	    }
	} catch (ObjectMappingException e) {
	    e.printStackTrace();
	}

	return entitylist;
    }

    public List<String> getTilelist() {

	activeConfig = new File(getConfigDir().toFile(), "ClearMob.conf");

	configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

	try {

	    rootnode = configManager.load();
	    if (!activeConfig.exists()) {
		// defaults(activeConfig, rootnode);
		saveConfig(rootnode, configManager);

	    }

	}

	catch (IOException e) {
	    e.printStackTrace();
	}
	entitylist = new ArrayList<String>();
	try {
	    for (String entity : rootnode.getNode("Clearing", "Lists", "TileEntityList")
		    .getList(TypeToken.of(String.class))) {
		entitylist.add(entity.toLowerCase());
		// plugin.getLogger().info(entity);
	    }
	} catch (ObjectMappingException e) {
	    e.printStackTrace();
	}
	return entitylist;
    }

    public List<String> getItemlist() {

	activeConfig = new File(getConfigDir().toFile(), "ClearMob.conf");

	configManager = HoconConfigurationLoader.builder().setFile(activeConfig).build();

	try {

	    rootnode = configManager.load();
	    if (!activeConfig.exists()) {
		// defaults(activeConfig, rootnode);
		saveConfig(rootnode, configManager);

	    }

	}

	catch (IOException e) {
	    e.printStackTrace();
	}
	entitylist = new ArrayList<String>();
	try {
	    for (String entity : rootnode.getNode("Clearing", "Lists", "ItemList")
		    .getList(TypeToken.of(String.class))) {
		entitylist.add(entity.toLowerCase());
		// plugin.getLogger().info(entity);
	    }
	} catch (ObjectMappingException e) {
	    e.printStackTrace();
	}
	return entitylist;
    }

    public Integer getNodeInt(String node) {
	int x = 0;
	if (node.contains(",")) {
	    String[] y = node.split(",");
	    if (y.length == 2) {
		x = rootnode.getNode(y[0], y[1]).getInt();
	    } else if (y.length == 3) {
		x = rootnode.getNode(y[0], y[1], y[2]).getInt();
	    }
	} else
	    x = rootnode.getNode(node).getInt();

	return x;

    }

    public Boolean getNodeBoolean(String node) {
	Boolean x = false;
	if (node.contains(",")) {
	    String[] y = node.split(",");
	    if (y.length == 2) {
		x = rootnode.getNode(y[0], y[1]).getBoolean();
	    } else if (y.length == 3) {
		x = rootnode.getNode(y[0], y[1], y[2]).getBoolean();
	    }
	} else
	    x = rootnode.getNode(node).getBoolean();

	return x;

    }

    public String getNodeString(String node) {
	String x = "";
	if (node.contains(",")) {
	    String[] y = node.split(",");
	    if (y.length == 2) {
		x = rootnode.getNode(y[0], y[1]).getString();
		;
	    } else if (y.length == 3) {
		x = rootnode.getNode(y[0], y[1], y[2]).getString();
	    }
	} else
	    x = rootnode.getNode(node).getString();

	return x;

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