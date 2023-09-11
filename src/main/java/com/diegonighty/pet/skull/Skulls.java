package com.diegonighty.pet.skull;

import com.destroystokyo.paper.profile.ProfileProperty;
import com.diegonighty.pet.config.PetConfiguration;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public final class Skulls {

	public static final UUID PROFILE_UUID = UUID.randomUUID();

	private Skulls() {}

	public static ItemStack create(final PetConfiguration.Pet pet) {
		final var skull = new ItemStack(Material.PLAYER_HEAD);

		skull.editMeta(SkullMeta.class, skullMeta -> {
			skullMeta.displayName(Component.text(pet.name()));

			final var playerProfile = Bukkit.createProfile(PROFILE_UUID, null);
			playerProfile.setProperty(new ProfileProperty("textures", pet.texture(), null));
			skullMeta.setPlayerProfile(playerProfile);
		});

		return skull;
	}

}
