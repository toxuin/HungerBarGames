package me.tomjw64.HungerBarGames.Threads;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

public class Lobby extends ChatVariableHolder implements Runnable{
	private Game game;
	
	public Lobby(Game gm)
	{
		game=gm;
	}
	@Override
	public void run()
	{
		
	}
	
}
