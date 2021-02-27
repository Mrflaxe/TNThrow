package ru.mrflaxe.tnthrow.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import ru.mrflaxe.tnthrow.meсhanics.TeamProvider;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.cooldown.preset.LitePlayersCooldownStorage;

public class PlayerInterractListener implements Listener {

	private final Configuration config;
	private final JavaPlugin plugin;
	private final TeamProvider teamProvider;
	
	private final LitePlayersCooldownStorage cooldown;
	
	public PlayerInterractListener(Configuration config, JavaPlugin plugin, TeamProvider teamProvider) {
		this.config = config;
		this.plugin = plugin;
		this.teamProvider = teamProvider;
		
		this.cooldown = new LitePlayersCooldownStorage(config.getInt("cooldown", 1));
	}
	
	@EventHandler
	public void PlayerInteractEvent (PlayerInteractEvent e) {
		Action act = e.getAction();
		if(!act.equals(Action.RIGHT_CLICK_AIR)) return;
		
		Player p = e.getPlayer();
		String name = p.getName();
		PlayerInventory inv = p.getInventory();
		ItemStack item = inv.getItemInMainHand();
		
		if(!item.getType().equals(Material.TNT)) return;
		
		e.setCancelled(true);
		
		// if player was waiting not enough, we cancel it.
		if(cooldown.containsKey(name) && cooldown.getRemainedTime(name) != 0) return;
		
		cooldown.refreshResetDate(name);
		p.setCooldown(Material.TNT, config.getInt("cooldown") * 20);
		
		int amount = item.getAmount();
		
		// if player in the creative mode we will not take TNT's from him.
		if(p.getGameMode() == GameMode.SURVIVAL) {
			if(amount > 0) {
				item.setAmount(amount - 1);
				inv.setItemInMainHand(item);
			} else { // Otherwise, we will
				inv.remove(item);
			}
		}
		
		// And... we launch a TNT!
		Location loc = p.getLocation();
		String team = teamProvider.getTeam(name);
		
		TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc.add(0, 1, 0), EntityType.PRIMED_TNT);
		
		double power = config.getDouble("power");
		int ticks = config.getInt("fusetime") * 20;
		
		Vector playerDirection = e.getPlayer().getLocation().getDirection();
		Vector velocity = playerDirection.multiply(power);
		
		tnt.setVelocity(velocity);
		tnt.setFuseTicks(ticks);
		tnt.setMetadata("team", new FixedMetadataValue(plugin, team));
		tnt.setMetadata("player", new FixedMetadataValue(plugin, name));
		
		loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 1);
	}

	// registration of our handler which will perform in the main class.
	public void register(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
