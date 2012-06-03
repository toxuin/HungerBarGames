package me.tomjw64.HungerBarGames.Managers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.General.ChestClass;
import me.tomjw64.HungerBarGames.General.ChestItem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class ChestClassManager {
	private static File configFile;
	private static FileConfiguration config;
	
	private static Set<ChestClass> chests=new HashSet<ChestClass>();
	
	//Call onEnable for initialization
	public static void loadConfig(HungerBarGames pl)
	{
		PluginDescriptionFile pdf=pl.getDescription();
		//Naming the file
		configFile=new File(pl.getDataFolder(),"chestclasses.yml");
		//Create if it doesn't exist
		if(!configFile.exists())
		{
			configFile.getParentFile().mkdirs();
			try {
				configFile.createNewFile();
				HungerBarGames.logger.info("["+pdf.getName()+"] Generating chest class file!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}
		//Actually loading the file
		config=new YamlConfiguration();
		try {
			config.load(configFile);
			HungerBarGames.logger.info("["+pdf.getName()+"] Loading chest classes!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}
		
		for(String x:config.getKeys(false))
		{
			ChestClass cc= new ChestClass(x);
			for(String i:config.getStringList(x))
			{
				String[] info=i.split(";");
				try
				{
					int chance=Integer.parseInt(info[1]);
					int amount=Integer.parseInt(info[2]);
					int item;
					if(info[0].contains("#"))
					{
						String[] itemInfo=info[0].split("#");
						item=Integer.parseInt(itemInfo[0]);
						short data=Short.parseShort(itemInfo[1]);
						cc.addItem(new ChestItem(item,chance,amount,data));
					}
					else
					{
						item=Integer.parseInt(info[0]);
						cc.addItem(new ChestItem(item,chance,amount));
					}
				}
				catch(Exception wtf)
				{
					wtf.printStackTrace();
					HungerBarGames.logger.warning("Could not load a chest item under class "+x);
				}
			}
			chests.add(cc);
		}
	}
	
	public static ChestClass getChestClass(String name)
	{
		for(ChestClass cc:chests)
		{
			if(cc.getName().equalsIgnoreCase(name))
			{
				return cc;
			}
		}
		return null;
	}
	
	public static Set<ChestClass> getClasses()
	{
		return chests;
	}
	
	//Get the config
	public static FileConfiguration getConfig()
	{
		return config;
	}
	
	//Save config
	public static void saveConfig()
	{
		try {
			config.save(configFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
}
