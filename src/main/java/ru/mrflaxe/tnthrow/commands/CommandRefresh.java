package ru.mrflaxe.tnthrow.commands;

import org.bukkit.command.CommandSender;

import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.preset.standalone.PermissibleCommand;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

public class CommandRefresh extends PermissibleCommand{
	
	private final Messages messages;
	private final Configuration config;
	
	public CommandRefresh(String command, Messages messages, Configuration config) {
		super(command, "tnt.refresh", messages);
		
		this.messages = messages;
		this.config = config;
	}

	@Override
	protected void executeCommand(CommandSender sender, CommandArguments args) {
		messages.refresh();
		config.refresh();
		
		sender.sendMessage("&e[TNTrow]&2 Configuration has been reloaded");
	}

}
