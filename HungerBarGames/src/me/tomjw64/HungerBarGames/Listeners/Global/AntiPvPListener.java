package me.tomjw64.HungerBarGames.Listeners.Global;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiPvPListener implements Listener{	
	
	public AntiPvPListener()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this,HungerBarGames.plugin);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		if(dmg.getEntity() instanceof Player&&dmg.getDamager() instanceof Player)
		{
			Player dmgd=(Player)dmg.getEntity();
			Player dmgr=(Player)dmg.getDamager();
			if(!GamesManager.isInGame(dmgd)||!GamesManager.isInGame(dmgr))
			{
				dmg.setCancelled(true);
			}
		}
	}
	
}
