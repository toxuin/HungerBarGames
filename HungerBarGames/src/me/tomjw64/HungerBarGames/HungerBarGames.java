/*
 * HungerBarGames
 * A Hunger Games plugin for bukkit
 * By tomjw64 and HaydenCappadona
 */

package me.tomjw64.HungerBarGames;

import java.util.logging.Logger;

import me.tomjw64.HungerBarGames.Listeners.Global.*;
import me.tomjw64.HungerBarGames.Managers.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerBarGames extends JavaPlugin{
	public static HungerBarGames plugin;
	public static final Logger logger=Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable()
	{
		
		PluginDescriptionFile pdf = getDescription();
		GamesManager.endAll();
		DataManager.saveArenas();
		logger.info("["+pdf.getName()+"] disabled!");
	}
	@Override
	public void onEnable()
	{
		plugin=this;
				
		//Load all YML files
		ConfigManager.loadConfig(this);
		ChestClassManager.loadConfig(this);
		DataManager.loadDatabase(this);
		PlayerDataManager.loadPlayerData(this);
		
		//Load listeners
		if(ConfigManager.getPvP())
		{
			new AntiPvPListener();
		}
		new ArenaProtectionListener();
		new PlayerRespawnListener();
		
		//Load commands
		CommandHandler.loadCommands(this);
		
		
		
		PluginDescriptionFile pdf = getDescription();
		logger.info("["+pdf.getName()+"] version "+pdf.getVersion()+" is enabled!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("hbg"))
		{
			if(args.length>0)
			{
				CommandHandler.handleCommand(this,sender,args);
			}
			else
			{
				ChatColor YELLOW=ChatColor.YELLOW;
				ChatColor BLUE=ChatColor.BLUE;
				ChatColor GREEN=ChatColor.GREEN;
				sender.sendMessage(YELLOW+"HungerBarGames version "+BLUE+getDescription().getVersion()+YELLOW+".");
				sender.sendMessage(YELLOW+"Made by "+GREEN+"tomjw64"+YELLOW+" and "+GREEN+"HaydenCappadona"+YELLOW+"!");
				sender.sendMessage(YELLOW+"Type "+BLUE+"/hbg help"+YELLOW+" for HungerBarGames commands!");
			}
		}
		return true;
	}
}
