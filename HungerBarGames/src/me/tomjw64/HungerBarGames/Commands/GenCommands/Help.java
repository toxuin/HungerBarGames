package me.tomjw64.HungerBarGames.Commands.GenCommands;

import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.command.CommandSender;

public class Help extends HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		sender.sendMessage(prefix+GREEN+"HungerBarGames Commands:");
		for(HBGCommand c:CommandHandler.getCmds())
		{
			String perm=c.permission();
			String[] permSplit=perm.split("\\.");
			String permGroup="";
			for(int x=0;x<permSplit.length-1;x++)
			{
				permGroup+=permSplit[x]+".";
			}
			permGroup+="*";
			if(sender.isOp()||sender.hasPermission(perm)
					||sender.hasPermission(permGroup)
					||sender.hasPermission("HBG.*")
					||(!ConfigManager.usingPlayerPerms()&&permSplit[1].equalsIgnoreCase("player")))
			{
				sender.sendMessage(BLUE+"/hbg "+c.usage()+YELLOW+" - "+c.description());
			}
		}
		sender.sendMessage(prefix+GREEN+"End of help");
	}

	@Override
	public String cmd() {
		return "help";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "shows help";
	}

	@Override
	public String permission() {
		return "HBG.player.help";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
