package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;

public class Unassign extends HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a=CommandHandler.getSelections().get(p);
			if(a!=null)
			{
				BlockState target=p.getTargetBlock(null,30).getState();
				if(target instanceof Chest)
				{
					Chest c=(Chest)target;
					a.unassign(c);
					p.sendMessage(prefix+GREEN+"Chest unassigned!");
				}
				else
				{
					p.sendMessage(prefix+RED+"The block you are looking at is not a chest!");
				}
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
			}
		}
	}

	@Override
	public String cmd() {
		return "unassign";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "unassigns a chest from all classes";
	}

	@Override
	public String permission() {
		return "HBG.admin.chestassign";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
