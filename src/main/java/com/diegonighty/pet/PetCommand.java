package com.diegonighty.pet;

import com.diegonighty.pet.menu.PetView;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetCommand extends Command {

	private final ViewFrame viewFrame;

	public PetCommand(final ViewFrame viewFrame) {
		super("pet");
		this.viewFrame = viewFrame;
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, final @NotNull String[] args) {
		if (sender instanceof Player player) {
			viewFrame.open(PetView.class, player);
			return true;
		}
		return false;
	}
}
