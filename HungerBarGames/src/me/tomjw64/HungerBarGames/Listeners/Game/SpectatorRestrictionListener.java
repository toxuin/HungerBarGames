package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

public class SpectatorRestrictionListener extends GameListener{

	public SpectatorRestrictionListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void drop(PlayerDropItemEvent drop)
	{
		if(getGame().isSpec(drop.getPlayer()))
		{
			drop.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void interact(PlayerInteractEvent interact)
	{
		if(getGame().isSpec(interact.getPlayer()))
		{
			Player toSpec=(Player)getGame().getTributes().toArray()[(int)(Math.random()*getGame().getNumTributes())];
			interact.getPlayer().teleport(toSpec);
			interact.getPlayer().sendMessage(ConfigManager.getPrefix()+ChatColor.YELLOW+"You are now spectating player "+ChatColor.BLUE+toSpec.getName()+"!");
			interact.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void foodChange(FoodLevelChangeEvent fc)
	{
		if(fc.getEntity() instanceof Player)
		{
			if(getGame().isSpec((Player)fc.getEntity()))
			{
				fc.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void damage(EntityDamageEvent damage)
	{
		if(damage.getEntity() instanceof Player&&getGame().isSpec((Player)damage.getEntity()))
		{
			damage.setCancelled(true);
		}
	}
	
}
