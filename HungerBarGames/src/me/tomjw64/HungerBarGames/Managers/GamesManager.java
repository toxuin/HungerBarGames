package me.tomjw64.HungerBarGames.Managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.HungerBarGames;

public class GamesManager {
	//All arenas
	private static Set<Arena> arenas=new HashSet<Arena>();
	//Players in a game
	private static Map<Player,Location> inGame=new HashMap<Player,Location>();
	//Players spectating
	private static Map<Player,Location> specing=new HashMap<Player,Location>();
	//Respawn logging
	private static Map<Player,Location> respawns=new HashMap<Player,Location>();
	
	public static void addArena(Arena ar)
	{
		if(getArena(ar.getName())==null)
		{
			arenas.add(ar);
		}
		else
		{
			HungerBarGames.logger.warning("Did not load arena "+ar.getName()+"! There is already an arena with that name!");
		}
	}
	
	public static Set<Arena> getArenas()
	{
		return arenas;
	}
	
	public static Arena getArena(String name)
	{
		for(Arena a:arenas)
		{
			if(a.getName().equalsIgnoreCase(name))
			{
				return a;
			}
		}
		return null;
	}
	
	public static boolean isInGame(Player p)
	{
		return inGame.containsKey(p);
	}
	
	public static void setInGame(Player p,boolean isPlaying)
	{
		if(isPlaying)
		{
			inGame.put(p,p.getLocation());
		}
		else
		{
			Location l=inGame.get(p);
			if(p.isDead())
			{
				respawns.put(p,l);
			}
			else
			{
				p.teleport(l);
			}
			inGame.remove(p);
		}
	}
	
	public static void setSpec(Player p,boolean isSpecing)
	{
		if(isSpecing)
		{
			specing.put(p,p.getLocation());
		}
		else
		{
			Location l=specing.get(p);
			if(p.isDead())
			{
				respawns.put(p,l);
			}
			else
			{
				p.teleport(l);
			}
			specing.remove(p);
		}
	}
	
	public static boolean isSpecing(Player p)
	{
		return specing.containsKey(p);
	}
	
	public static void delArena(Arena a)
	{
		DataManager.removeArena(a.getName());
		arenas.remove(a);
	}
	
	public static Game getGame(Player p,boolean tribute)
	{
		if(tribute)
		{
			for(Arena a:arenas)
			{
				if(a.getGame()!=null)
				{
					if(a.getGame().isTribute(p))
					{
						return a.getGame();
					}
				}
			}
		}
		else
		{
			for(Arena a: arenas)
			{
				if(a.getGame()!=null)
				{
					if(a.getGame().isSpec(p))
					{
						return a.getGame();
					}
				}
			}
		}
		return null;
	}
	
	public static boolean respawnMarked(Player p)
	{
		return respawns.containsKey(p);
	}
	
	public static void respawnUnmark(Player p)
	{
		respawns.remove(p);
	}
	
	public static Location getRespawn(Player p)
	{
		return respawns.get(p);
	}
	
}