package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

public class GameMotionListener extends GameListener{
	
	public GameMotionListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		Player mover=move.getPlayer();
		boolean tribute=getGame().isTribute(mover);
		if(tribute||getGame().isSpec(mover))
		{
			Game g=GamesManager.getGame(mover,tribute);
			if(!g.getArena().isInArena(move.getTo()))
			{
				Location from=move.getFrom();
				double x=Math.floor(from.getX()+.5);
				double z=Math.floor(from.getZ()+.5);
				mover.teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
				mover.sendMessage(ChatColor.RED+"You cannot go outside the arena!");
			}
		}
	}

}
