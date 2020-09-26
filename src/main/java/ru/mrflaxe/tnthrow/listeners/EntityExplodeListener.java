package ru.mrflaxe.tnthrow.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.soknight.lib.configuration.Configuration;

public class EntityExplodeListener implements Listener {
	
	private final List<String> blackList;
	
	private final JavaPlugin plugin;
	
	public EntityExplodeListener(Configuration config, JavaPlugin plugin) {
		blackList = config.getList("blacklist"); // blacklist contains a list of blocks which shouldn't  blow up
		
		this.plugin = plugin;
	}
	
	@EventHandler
	public void EntityExplodeEvent (EntityExplodeEvent e) {
		if(e.getEntityType() != EntityType.PRIMED_TNT) return;

		e.blockList().stream()
					.filter(b -> blackList.contains(b.getType().toString())) // I filter blocks which are not contained in our blacklist
					.forEach(b -> {
						Material type = b.getType();
						BlockData blockData = b.getBlockData();
						
						Bukkit.getScheduler().runTaskLater(plugin, task -> {
							b.setType(type);
							b.setBlockData(blockData);
							String name = type.toString();
							
							// these if-blocks are processing some variations of sounds for replacing blocks
							if(name.endsWith("OAK") || name.endsWith("LOG") || name.endsWith("PUMPKIN") || name.endsWith("MELON")) {
								b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_PLACE, 10, 1);
								return;
							}
							
							if(type.toString().endsWith("ORE")) {
								b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_PLACE, 10, 1);
								return;
							}
						}, 40);
					});
	}
	
	// registration of our handler which will perform in the main class.
	public void register() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
