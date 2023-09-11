package com.diegonighty.pet.access;

import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class Packets {
	private Packets() {
	}

	public static void sendPacket(final Player player, final Packet<?> packet) {
		((CraftPlayer) player).getHandle().connection.connection.send(packet);
	}

}
