package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class GameChestListener extends GameListener{

	public GameChestListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void chestOpen(PlayerInteractEvent interact)
	{
		if(getGame().isTribute(interact.getPlayer())
				&&getGame().getArena().getFiller()!=null
				&&interact.getAction()==Action.RIGHT_CLICK_BLOCK
				&&interact.getClickedBlock().getState() instanceof Chest)
		{
			Chest c=(Chest)interact.getClickedBlock().getState();
			if(!getGame().beenFilled(c))
			{
				getGame().getArena().getFiller().fillChest(c);
				getGame().setFilled(c);
			}
		}
	}
}
