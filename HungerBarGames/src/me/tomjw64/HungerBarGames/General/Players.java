package me.tomjw64.HungerBarGames.General;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Players {
	
	public static void heal(Player p)
	{
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);
	}
	
	public static void clearInv(Player p)
	{
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
	}
	
}
