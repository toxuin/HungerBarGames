package me.tomjw64.HungerBarGames.Listeners.Countdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class CountdownArenaProtect extends GameListener{

	public CountdownArenaProtect(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void explode(EntityExplodeEvent explode)
	{
		explode.setCancelled(true);
	}
}
