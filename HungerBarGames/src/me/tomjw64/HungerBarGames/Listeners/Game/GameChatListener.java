package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

public class GameChatListener extends GameListener{

	public GameChatListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void chat(PlayerChatEvent chat)
	{
		if(ConfigManager.getChatRestricted())
		{
			chat.getRecipients().removeAll(getGame().getTributes());
			if(getGame().isTribute(chat.getPlayer()))
			{
				int radius=ConfigManager.getChatRadius();
				for(Entity e:chat.getPlayer().getNearbyEntities(radius,radius,radius))
				{
					if(e instanceof Player)
					{
						chat.getRecipients().add((Player)e);
					}
				}
			}
		}
	}

}
