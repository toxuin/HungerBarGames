package me.tomjw64.HungerBarGames.Commands;

import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

import org.bukkit.command.CommandSender;

public abstract class HBGCommand extends ChatVariableHolder{
	public abstract void execute(CommandSender sender, String[] args);
	public abstract String cmd();
	public abstract String usage();
	public abstract String description();
	public abstract String permission();
	public abstract int numArgs();
}
