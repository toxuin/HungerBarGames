package me.tomjw64.HungerBarGames.Listeners.Lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class LobbyRemovalListener extends GameListener{

	public LobbyRemovalListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void leave(PlayerQuitEvent quit)
	{
		Player quitter=quit.getPlayer();
		if(getGame().isTribute(quitter))
		{
			quit.setQuitMessage(null);
			getGame().removeTribute(quitter);
		}
	}

}
