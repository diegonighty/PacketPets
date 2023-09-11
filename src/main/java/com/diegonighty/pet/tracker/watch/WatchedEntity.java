package com.diegonighty.pet.tracker.watch;

import com.diegonighty.pet.access.Packets;
import com.diegonighty.pet.tracker.view.TrackedEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class WatchedEntity implements TrackedEntity {

	private final Map<UUID, Integer> watchers = new HashMap<>();

	@Override
	public void track(final Player player) {
		final var entity = buildEntity();
		watchers.put(player.getUniqueId(), entity.getId());

		Packets.sendPacket(
				player,
				new ClientboundAddEntityPacket(entity)
		);

		final var data = entity.getEntityData()
						.packDirty();

		if (data != null) {
			Packets.sendPacket(
					player,
					new ClientboundSetEntityDataPacket(
							entity.getId(),
							data
					)
			);
		}

		Packets.sendPacket(
				player,
				new ClientboundSetEquipmentPacket(
						entity.getId(),
						equipment()
				)
		);

		onStartTracking(player);
	}

	@Override
	public void untrack(final Player player) {
		final var entityId = watchers.remove(player.getUniqueId());
		if (entityId == null) {
			return;
		}

		Packets.sendPacket(player, new ClientboundRemoveEntitiesPacket(entityId));
		onStopTracking(player);
	}

	protected void onStartTracking(final Player player) {
		// empty by default
	}

	protected void onStopTracking(final Player player) {
		// empty by default
	}

	public void onTick(final Player viewer, final Entity globalEntity) {
		// empty by default
	}

	protected abstract Entity buildEntity();

	protected List<Pair<EquipmentSlot, ItemStack>> equipment() {
		return List.of();
	}

	public List<Player> watchers() {
		return watchers.keySet().stream()
				.map(Bukkit::getPlayer)
				.toList();
	}

	public int getEntityIdByPlayer(Player player) {
		return watchers.get(player.getUniqueId());
	}
}
