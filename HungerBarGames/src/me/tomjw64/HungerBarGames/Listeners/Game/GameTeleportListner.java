package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class GameTeleportListner extends GameListener {

	public GameTeleportListner(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void teleport(PlayerTeleportEvent tele)
	{
		if(getGame().isTribute(tele.getPlayer())&&!getGame().getArena().isInArena(tele.getTo()))
		{
			tele.setCancelled(true);
		}
	}

}
