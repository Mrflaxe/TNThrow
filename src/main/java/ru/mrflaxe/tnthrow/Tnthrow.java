package ru.mrflaxe.tnthrow;

import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.tnthrow.commands.CommandRefresh;
import ru.mrflaxe.tnthrow.listeners.EntityExplodeListener;
import ru.mrflaxe.tnthrow.listeners.PlayerInterractListener;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

public class Tnthrow extends JavaPlugin{
	
	private Configuration config;
	private Messages messages;

	@Override
	public void onEnable() {
		initconfigs();
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
		new PlayerInterractListener(config).register(this);
		new EntityExplodeListener(config, this).register();
	}
	
	private void registerCommands() {
		new CommandRefresh("tntrefresh", messages, config).register(this);
	}

}
