package com.diegonighty.pet.entity;

import com.diegonighty.pet.config.PetConfiguration;
import com.diegonighty.pet.tracker.MinecraftEntitySpawner;
import net.minecraft.world.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetManager {

	private final Collection<TrackedEntityPet> entities = new ArrayList<>();
	private final Map<UUID, Entity> entityByPlayer = new HashMap<>();

	public void spawn(final PetConfiguration.Pet pet, final Player owner) {
		removeEntity(owner);

		final var trackedPet = new TrackedEntityPet(pet, owner);
		final var globalEntity = MinecraftEntitySpawner.spawn(owner.getLocation(), trackedPet);

		entityByPlayer.put(owner.getUniqueId(), globalEntity);
		entities.add(trackedPet);
	}

	public void removeEntity(final Player owner) {
		final var oldEntity = entityByPlayer.remove(owner.getUniqueId());
		if (oldEntity != null) {
			oldEntity.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	public void startTicking(final Plugin plugin) {
		plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(
						plugin,
						() -> {
							for (final var watchedEntity : entities) {
								final var globalEntity = entityByPlayer.get(watchedEntity.owner().getUniqueId());
								for (final var watcher : watchedEntity.watchers()) {
									watchedEntity.onTick(watcher, globalEntity);
								}
							}
						},
						0,
						1L
				);
	}

}
