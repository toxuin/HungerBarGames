package me.tomjw64.HungerBarGames.Commands.ModCommands;

import java.util.Set;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.ChestClass;
import me.tomjw64.HungerBarGames.Managers.ChestClassManager;

public class ListClasses extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Set<ChestClass> classes=ChestClassManager.getClasses();
		if(classes.size()==0)
		{
			sender.sendMessage(prefix+RED+"There are no classes currently available!");
		}
		else
		{
			String list=prefix+YELLOW+"Classes: ";
			for(ChestClass cc:classes)
			{
				list+=GREEN+cc.getName()+WHITE+", ";
			}
			list=list.substring(0,list.length()-2);
			sender.sendMessage(list);
		}
	}

	@Override
	public String cmd() {
		return "classes";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "lists all available chest classes for use";
	}

	@Override
	public String permission() {
		return "HBG.mod.classlist";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
