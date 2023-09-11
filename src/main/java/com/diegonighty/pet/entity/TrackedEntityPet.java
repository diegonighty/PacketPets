package com.diegonighty.pet.entity;

import com.diegonighty.pet.access.Packets;
import com.diegonighty.pet.config.PetConfiguration.Pet;
import com.diegonighty.pet.skull.Skulls;
import com.diegonighty.pet.tracker.watch.WatchedEntity;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.List;

public class TrackedEntityPet extends WatchedEntity {

	public static final double OFFSET_X = 0.75;
	public static final double OFFSET_Y = 0.5;
	public static final double OFFSET_Z = 0.75;

	private final Pet pet;
	private final Player owner;

	public TrackedEntityPet(final Pet pet, final Player owner) {
		this.pet = pet;
		this.owner = owner;
	}

	@Override
	public void onTick(final Player viewer, final Entity globalEntity) {
		final var x = owner.getLocation().getX() - OFFSET_X;
		final var y = owner.getLocation().getY() - OFFSET_Y;
		final var z = owner.getLocation().getZ() - OFFSET_Z;

		globalEntity.setPos(x, y, z);

		Packets.sendPacket(
				viewer,
				new ClientboundTeleportEntityPacket(
						buff(
								getEntityIdByPlayer(viewer),
								x, y, z,
								(byte) owner.getYaw(),
								(byte) owner.getPitch()
						)
				)
		);
	}

	@Override
	protected Entity buildEntity() {
		final var level = ((CraftWorld) owner.getWorld()).getHandle();
		final var entity = new ArmorStand(
				level,
				owner.getLocation().getX() - OFFSET_X,
				owner.getLocation().getY() - OFFSET_Y,
				owner.getLocation().getZ() - OFFSET_Z
		);

		entity.setNoGravity(true);
		entity.setInvisible(true);

		return entity;
	}

	@Override
	protected List<Pair<EquipmentSlot, ItemStack>> equipment() {
		return List.of(Pair.of(EquipmentSlot.HEAD, ItemStack.fromBukkitCopy(Skulls.create(pet))));
	}

	private FriendlyByteBuf buff(
			final int entityId,
			final double x,
			final double y,
			final double z,
			final byte yaw,
			final byte pitch
	) {
		final var buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeVarInt(entityId);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeByte(yaw);
		buf.writeByte(pitch);
		buf.writeBoolean(false);
		return buf;
	}

	public Player owner() {
		return owner;
	}
}
