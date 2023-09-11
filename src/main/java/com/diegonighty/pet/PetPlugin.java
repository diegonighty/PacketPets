package com.diegonighty.pet;

import com.diegonighty.pet.menu.PetView;
import com.diegonighty.pet.config.ConfigurationContainer;
import com.diegonighty.pet.config.PetConfiguration;
import com.diegonighty.pet.entity.PetManager;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.io.IOException;

public class PetPlugin extends JavaPlugin implements Listener {

	private final Logger logger = getSLF4JLogger();
	private final PetManager petManager = new PetManager();
	private ConfigurationContainer<PetConfiguration> petConfiguration;

	@Override
	public void onEnable() {
		try {
			this.petConfiguration = ConfigurationContainer.load(
					getSLF4JLogger(),
					getDataFolder().toPath(),
					PetConfiguration.class,
					"config"
			);

			logger.info("Loaded {} pets", petConfiguration.get().pets.size());

			for (final var pet : petConfiguration.get().pets) {
				logger.info("Loaded pet: {}", pet.name());
			}

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		petManager.startTicking(this);

		final var viewFrame = ViewFrame.create(this)
				.with(new PetView(petConfiguration.get(), petManager))
				.register();

		getServer().getCommandMap()
						.register("pets", new PetCommand(viewFrame));

		getServer().getPluginManager()
				.registerEvents(this, this);
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		petManager.removeEntity(event.getPlayer());
	}

}
