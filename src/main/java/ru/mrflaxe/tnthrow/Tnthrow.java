package ru.mrflaxe.tnthrow;

import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.tnthrow.commands.CommandRefresh;
import ru.mrflaxe.tnthrow.listeners.EntityDamageListener;
import ru.mrflaxe.tnthrow.listeners.EntityExplodeListener;
import ru.mrflaxe.tnthrow.listeners.PlayerInterractListener;
import ru.mrflaxe.tnthrow.meсhanics.TeamProvider;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

public class Tnthrow extends JavaPlugin{
	
	private TeamProvider teamProvider;
	
	private Configuration config;
	private Messages messages;

	@Override
	public void onEnable() {
		initconfigs();
		
		this.teamProvider = new TeamProvider();
		
		registerEvents();
		registerCommands();
	}

	public void initconfigs() {
		this.config = new Configuration(this, "config.yml");
		config.refresh();
		
		this.messages = new Messages(this, "messages.yml");
		messages.refresh();
	}
	
	private void registerEvents() {
		new PlayerInterractListener(config, this, teamProvider).register(this);
		new EntityExplodeListener(config, this).register();
		new EntityDamageListener(teamProvider).register(this);
	}
	
	private void registerCommands() {
		new CommandRefresh("tntrefresh", messages, config).register(this);
	}

}
