package me.tomjw64.HungerBarGames.Listeners.Countdown;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class CountdownMotionListener extends GameListener{
	
	public CountdownMotionListener(Game gm)
	{
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		if(getGame().isTribute(move.getPlayer()))
		{
			Location from=move.getFrom();
			Location to=move.getTo();
			double x=Math.floor(from.getX());
			double z=Math.floor(from.getZ());
			if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z)
			{
				if(ConfigManager.getExplode())
				{
					move.getFrom().getWorld().createExplosion(to,0,false);
					move.getPlayer().setHealth(0);
				}
				else
				{
					x+=.5;
					z+=.5;
					move.getPlayer().teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
				}
			}
		}
	}

}
