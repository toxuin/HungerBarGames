package me.tomjw64.HungerBarGames.Commands.GenCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

public class Tributes extends HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=GamesManager.getArena(args[0]);
		if(a!=null)
		{
			if(a.getGame()!=null)
			{
				if(sender instanceof Player&&GamesManager.isInGame((Player)sender))
				{
					sender.sendMessage(prefix+YELLOW+"There are "+a.getGame().getNumTributes()+" tributes still alive!");
				}
				else
				{
					String list=prefix+YELLOW+"Tributes: ";
					for(Player p:a.getGame().getTributes())
					{
						list+=GREEN+p.getName()+WHITE+", ";
					}
					list=list.substring(0,list.length()-2);
					sender.sendMessage(list);
				}
			}
			else
			{
				sender.sendMessage(prefix+RED+"A game is not running in arena "+BLUE+args[0]+"!");
			}
		}
		else
		{
			sender.sendMessage(prefix+RED+"There is no arena by that name!");
		}
	}

	@Override
	public String cmd() {
		return "tributes";
	}

	@Override
	public String usage() {
		return cmd()+" [arena]";
	}

	@Override
	public String description() {
		return "views tributes still in a game";
	}

	@Override
	public String permission() {
		return "HBG.player.tributes";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
