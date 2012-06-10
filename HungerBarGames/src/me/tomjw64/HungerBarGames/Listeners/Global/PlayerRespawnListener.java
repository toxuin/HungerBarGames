package me.tomjw64.HungerBarGames.Listeners.Global;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener{

	public PlayerRespawnListener()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this,HungerBarGames.plugin);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void respawn(PlayerRespawnEvent respawn)
	{
		Player p=respawn.getPlayer();
		if(GamesManager.respawnMarked(p))
		{
			respawn.setRespawnLocation(GamesManager.getRespawn(p));
			GamesManager.respawnUnmark(p);
		}
	}
	
}
