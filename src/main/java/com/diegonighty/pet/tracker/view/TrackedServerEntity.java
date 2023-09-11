package com.diegonighty.pet.tracker.view;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;

import java.util.Set;
import java.util.function.Consumer;

public class TrackedServerEntity extends ServerEntity {

	private final TrackedEntity trackedEntity;

	public TrackedServerEntity(
			final ServerLevel worldserver,
			final TrackedMinecraftEntity entity,
			final Consumer<Packet<?>> consumer,
			final Set<ServerPlayerConnection> trackedPlayers
	) {
		super(worldserver, entity, 0, false, consumer, trackedPlayers);
		this.trackedEntity = entity.getTrackedEntity();
	}

	@Override
	public void sendChanges() {}

	@Override
	public void sendPairingData(final ServerPlayer player, final Consumer<Packet<ClientGamePacketListener>> sender) {
		trackedEntity.track(player.getBukkitEntity());
	}

	@Override
	public void removePairing(final ServerPlayer player) {
		trackedEntity.untrack(player.getBukkitEntity());
	}
}

