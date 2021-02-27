package ru.mrflaxe.tnthrow.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.tnthrow.meсhanics.TeamProvider;

public class EntityDamageListener implements Listener {
	
	private final TeamProvider teamProvider;
	
	public EntityDamageListener(TeamProvider teamProvider) {
		this.teamProvider = teamProvider;
	}
	
	@EventHandler
	public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if(e.getCause() != DamageCause.ENTITY_EXPLOSION) return;
		if(e.getEntityType() != EntityType.PLAYER) return;
		if(e.getDamager().getType() != EntityType.PRIMED_TNT) return;
		
		Entity damager = e.getDamager();
		Player player = (Player) e.getEntity();
		
		String damagerTeam = damager.getMetadata("team").get(0).asString();
		String damagerName = damager.getMetadata("player").get(0).asString();
		if(damagerTeam == null || damagerName == null) return;
		
		String playerTeam = teamProvider.getTeam(player.getName());
		if(damagerTeam == playerTeam) {
			if(damagerName != player.getName()) e.setCancelled(true);
		}
	}
	
	public void register(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
