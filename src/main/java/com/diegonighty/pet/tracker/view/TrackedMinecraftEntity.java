package com.diegonighty.pet.tracker.view;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;

public class TrackedMinecraftEntity extends Entity {

	private final TrackedEntity trackedEntity;
	private final ServerLevel serverLevel;
	private CraftEntity bukkitEntity;

	public TrackedMinecraftEntity(
			final ServerLevel world,
			final TrackedEntity trackedEntity
	) {
		super(EntityType.AREA_EFFECT_CLOUD, world);
		this.trackedEntity = trackedEntity;
		this.serverLevel = world;
	}

	public TrackedEntity getTrackedEntity() {
		return trackedEntity;
	}

	@Override
	public CraftEntity getBukkitEntity() {
		if (bukkitEntity == null) {
			this.bukkitEntity = new CraftEntity(serverLevel.getCraftServer(), this) {};
		}
		return bukkitEntity;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(final CompoundTag nbt) {

	}

	@Override
	protected void addAdditionalSaveData(final CompoundTag nbt) {

	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return null;
	}

	@Override
	public String toString() {
		return "TrackedMinecraftEntity";
	}
}
