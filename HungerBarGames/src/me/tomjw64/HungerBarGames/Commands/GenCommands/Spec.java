package me.tomjw64.HungerBarGames.Commands.GenCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spec extends HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			if(!GamesManager.isInGame(p))
			{
				if(!GamesManager.isSpecing(p))
				{
					Arena a=GamesManager.getArena(args[0]);
					if(a!=null)
					{
						if(a.getGame()!=null&&a.getGame().getStatus()==Status.IN_GAME)
						{
							a.getGame().setSpec(p,true);
						}
						else
						{
							p.sendMessage(prefix+RED+"There is not a game running in that arena!");
						}
					}
					else
					{
						p.sendMessage(prefix+RED+"There is no arena by that name!");
					}
				}
				else
				{
					p.sendMessage(prefix+RED+"You are already spectating a game! Leave before you spectate another!");
				}
			}
			else
			{
				p.sendMessage(prefix+RED+"You are in a game! Leave before you spectate!");
			}
		}
	}

	@Override
	public String cmd() {
		return "spec";
	}

	@Override
	public String usage() {
		return cmd()+" [arena]";
	}

	@Override
	public String description() {
		return "spectates a game";
	}

	@Override
	public String permission() {
		return "HBG.player.spec";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
