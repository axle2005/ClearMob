package io.github.axle2005.clearmob;
import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {

	static File activeConfig;
	static ConfigurationNode cconfig;
	static ConfigurationLoader<CommentedConfigurationNode> cconfigManager;
	


	public static void onLoad(File defaultConfig, ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager,  String configname) {
	
			try{
				
				config = configManager.load();
				
					if(!defaultConfig.exists()){
						Defaults(defaultConfig, config, configname);
						saveConfig(config, configManager);

					}
			}
			catch (IOException e){
				//AxleRandomThings.log.warn("Error loading " + defaultConfig.getName() +" config");
			}
		}
	
	public static void Defaults(File defaultConfig, ConfigurationNode config, String configname) {
		if( configname == "slack") {
			config.getNode("webhook-url").setValue("<url here>");
			config.getNode("token").setValue("<token here>");
			config.getNode("port").setValue("<port here>");
			config.getNode("channel").setValue("<channel here>");
		}
		
		if(configname =="default"){
			config.getNode("Test").setValue("enabled");	
		}
		
	}
	
	public static void commit(String node, String child) {
		cconfig.getNode(node).setValue(child);
		saveConfig(cconfig, cconfigManager);
	}
	
	public static void saveConfig(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
		try {
			configManager.save(config);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	

	public static void Reload(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
		try {
			config = configManager.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//AxleRandomThings.log.warn("Problem loading config");
		}
	}
	
	public static void delete(ConfigurationNode config, ConfigurationLoader<CommentedConfigurationNode> configManager) {
	}
	
	
}
	