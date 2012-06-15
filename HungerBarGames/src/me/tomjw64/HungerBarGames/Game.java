package me.tomjw64.HungerBarGames;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Players;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Listeners.Lobby.*;
import me.tomjw64.HungerBarGames.Listeners.Countdown.*;
import me.tomjw64.HungerBarGames.Listeners.Game.*;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;
import me.tomjw64.HungerBarGames.Managers.GamesManager;
import me.tomjw64.HungerBarGames.Threads.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class Game extends ChatVariableHolder{
	private Arena arena;
	private Set<Player> tributes=new HashSet<Player>();
	private Set<Player> spectators=new HashSet<Player>();
	private Set<String> deaths=new HashSet<String>();
	private Set<Chest> filledChests=new HashSet<Chest>();
	private Set<GameListener> listeners=new HashSet<GameListener>();
	private boolean repeat;
	private Status status;
	private boolean notEnoughPlayers=false;
		
	public Game(Arena ar, boolean rpt)
	{
		arena=ar;
		repeat=rpt;
		
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"A lobby has been started for arena "+BLUE+arena.getName()+"!");
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"Type "+BLUE+"/hbg join "+arena.getName()+YELLOW+" to join the game");
		
		new Lobby(this);
	}
	
	public void startGame()
	{
		status=Status.IN_GAME;
		updateListeners();
		
		new NightCheck(this);
	}
	
	public void startCountdown()
	{
		Collection<Location> spawns=arena.getSpawns().values();
		Iterator<Location> i=spawns.iterator();
		String list=prefix+GREEN+"Tributes: ";
		for(Player p:tributes)
		{
			list+=RED+p.getName()+WHITE+", ";
			p.teleport(i.next());
			p.setGameMode(GameMode.SURVIVAL);
			Players.clearInv(p);
			Players.heal(p);
		}
		list=list.substring(0,list.length()-2);
		for(Player p:tributes)
		{
			p.sendMessage(prefix+GREEN+"The countdown has begun!");
			p.sendMessage(list);
		}
		
		arena.fillChests();
		new Countdown(this);
		
	}
	
	public void endGame(boolean forced)
	{
		status=null;
		unregisterListeners();
		if(!forced)
		{
			for(Player p:spectators)
			{
				setSpec(p,false);
			}
			for(Player p:tributes)
			{
				removeTribute(p);
			}
			arena.endGame(repeat);
		}
		else
		{
			arena.endGame(false);
			Bukkit.getServer().broadcastMessage(prefix+YELLOW+"The game in arena "+BLUE+arena.getName()+YELLOW+" has been cancelled!");
		}
	}
	
	public void declareWinner()
	{
		Player p=(Player)tributes.toArray()[0];
		GamesManager.setInGame(p,false);
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"Player "+BLUE+p.getName()+YELLOW+" has won the game in arena "+BLUE+arena.getName()+"!");
		Players.heal(p);
		for(String cmd:ConfigManager.getWinCommands())
		{
			cmd=cmd.replace("<player>", p.getName());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),cmd);
		}
		endGame(false);
	}
	
	public void addTribute(Player p)
	{
		if(getNumTributes()<arena.getMaxPlayers()&&status==Status.LOBBY&&getNumTributes()<arena.getNumSpawns())
		{
			tributes.add(p);
			GamesManager.setInGame(p,true);
			p.setGameMode(GameMode.SURVIVAL);
			Players.clearInv(p);
			Players.heal(p);
			p.teleport(arena.getLobby());
			p.sendMessage(prefix+YELLOW+"You have joined the game in arena "+BLUE+arena.getName()+"!");
			p.sendMessage(prefix+YELLOW+"This game has "+BLUE+getNumTributes()+"/"+arena.getMaxPlayers()+YELLOW+" players!");
			if(notEnoughPlayers&&getNumTributes()>=arena.getMinPlayers())
			{
				notEnoughPlayers=false;
				startCountdown();
			}
		}
		else if(getNumTributes()>=arena.getMaxPlayers()||getNumTributes()>=arena.getNumSpawns())
		{
			p.sendMessage(prefix+RED+"There is not enough room in the game!");
		}
		else
		{
			p.sendMessage(prefix+RED+"The game has already been started!");
		}
	}
	
	public void eliminateTribute(Player p)
	{
		p.getWorld().strikeLightning(p.getLocation().add(0, 100, 0));
		deaths.add(p.getName());
		removeTribute(p);
		if(getNumTributes()==1)
		{
			declareWinner();
		}
	}
	
	public void removeTribute(Player p)
	{
		tributes.remove(p);
		GamesManager.setInGame(p,false);
	}
	
	public Set<Player> getTributes()
	{
		return tributes;
	}
	
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	
	public int getNumTributes()
	{
		return tributes.size();
	}
	
	public Set<String> getDeaths()
	{
		return deaths;
	}
	
	public void clearDeaths()
	{
		deaths.clear();
	}
	
	public void setStatus(Status stat)
	{
		status=stat;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public void setNotEnoughPlayers()
	{
		notEnoughPlayers=true;
	}
	
	public void updateListeners()
	{
		unregisterListeners();
		switch(status)
		{
		case LOBBY:
			new LobbyLevelListener(this);
			new LobbyBlockListener(this);
			break;
		case COUNTDOWN:
			new CountdownMotionListener(this);
			new CountdownInteractListener(this);
			break;
		case IN_GAME:
			new EliminationListener(this);
			new GameDamageListener(this);
			new GameBlockListener(this);
			new BlockLogger(this);
			new GameChestListener(this);
			new GameTeleportListner(this);
			new GameMotionListener(this);
			new SpectatorRestrictionListener(this);
			break;
		}
	}
	
	public void unregisterListeners()
	{
		Object[] gls=listeners.toArray();
		for(int x=0;x<gls.length;x++)
		{
			GameListener gl=(GameListener)gls[x];
			listeners.remove(gl);
			gl.unregister();
			gl=null;
		}
	}
	
	public Arena getArena()
	{
		return arena;
	}
	
	public void setFilled(Chest c)
	{
		filledChests.add(c);
	}
	
	public boolean beenFilled(Chest c)
	{
		return filledChests.contains(c);
	}
	
	public void addListener(GameListener gl)
	{
		listeners.add(gl);
	}
	
	public boolean isSpec(Player p)
	{
		return spectators.contains(p);
	}
	
	public void setSpec(Player p,boolean set)
	{
		if(set)
		{
			spectators.add(p);
			GamesManager.setSpec(p,true);
			p.setGameMode(GameMode.SURVIVAL);
			p.setAllowFlight(true);
			for(Player other:Bukkit.getServer().getOnlinePlayers())
			{
				other.hidePlayer(p);
			}
			p.teleport(arena.getSpec());
			p.sendMessage(prefix+YELLOW+"You are now spectating arena "+BLUE+arena.getName()+"!");
		}
		else
		{
			spectators.remove(p);
			GamesManager.setSpec(p,false);
			p.setAllowFlight(false);
			for(Player other:Bukkit.getServer().getOnlinePlayers())
			{
				other.showPlayer(p);
			}
			p.sendMessage(prefix+YELLOW+"You have stopped spectating arena "+BLUE+arena.getName()+"!");
		}
	}
	
}
