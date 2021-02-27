package ru.mrflaxe.tnthrow.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.tnthrow.meсhanics.TeamProvider;

public class PlayerChangeWorldListener implements Listener {

	private final JavaPlugin plugin;
	private final TeamProvider teamProvider;
	
	public PlayerChangeWorldListener(JavaPlugin plugin, TeamProvider teamProvider) {
		this.plugin = plugin;
		this.teamProvider = teamProvider;
	}
	
	@EventHandler
	public void playerChangeWorldEvent(PlayerChangedWorldEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			Player player = e.getPlayer();
			
			ItemStack helmet = player.getEquipment().getHelmet();
			if(helmet.getType() != Material.LEATHER_HELMET) return;
			
			LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
			Color color = meta.getColor();
			
			if(color.equals(Color.RED)) {
				teamProvider.setTeam(player.getName(), "red");
				return;
			}
			
			if(color.equals(Color.BLUE)) {
				teamProvider.setTeam(player.getName(), "blue");
				return;
			}
			
			if(color.equals(Color.GREEN)) {
				teamProvider.setTeam(player.getName(), "green");
				return;
			}
			
			if(color.equals(Color.YELLOW)) {
				teamProvider.setTeam(player.getName(), "yellow");
				return;
			}
		}, 20);
	}

	public void register(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
}
