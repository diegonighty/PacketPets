package com.diegonighty.pet.tracker;

import com.diegonighty.pet.tracker.view.TrackedEntity;
import com.diegonighty.pet.tracker.view.TrackedMinecraftEntity;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public final class MinecraftEntitySpawner {

	private MinecraftEntitySpawner() {
	}

	public static Entity spawn(final Location location, final TrackedEntity tracked) {
		final var level = ((CraftWorld) location.getWorld()).getHandle();
		InjectedLevelCallback.injectAt(level);

		final var entity = new TrackedMinecraftEntity(
				level,
				tracked
		);
		entity.setPos(location.getX(), location.getY(), location.getZ());
		level.addFreshEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
		return entity;
	}

}
