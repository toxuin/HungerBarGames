package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.ChestClass;
import me.tomjw64.HungerBarGames.Managers.ChestClassManager;

public class AutoAssign extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Arena a=CommandHandler.getSelections().get(sender);
		if(a!=null)
		{
			ChestClass cc=ChestClassManager.getChestClass(args[0]);
			if(cc!=null)
			{
				a.setFiller(cc);
				sender.sendMessage(prefix+YELLOW+"Auto-filler set to class "+BLUE+args[0]+YELLOW+" for arena "+a.getName()+"!");
			}
			else
			{
				sender.sendMessage(prefix+RED+"There is no chest class by that name! Create it in the config!");
			}
		}
		else
		{
			sender.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
		}
	}

	@Override
	public String cmd() {
		return "autoassign";
	}

	@Override
	public String usage() {
		return cmd()+" [class]";
	}

	@Override
	public String description() {
		return "assigns all chests in an arena";
	}

	@Override
	public String permission() {
		return "HBG.admin.chestassign";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
