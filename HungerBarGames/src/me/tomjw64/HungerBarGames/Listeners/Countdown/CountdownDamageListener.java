package me.tomjw64.HungerBarGames.Listeners.Countdown;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class CountdownDamageListener extends GameListener{

	public CountdownDamageListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		if(dmg.getEntity() instanceof Player)
		{
			if(getGame().isTribute((Player)dmg.getEntity()))
			{
				dmg.setCancelled(true);
			}
		}
	}

}
