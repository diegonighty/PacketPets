package com.diegonighty.pet.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Set;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public final class PetConfiguration {

	public Set<Pet> pets = Set.of(
			new Pet(
					"DenDenMushi",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjN2M4ZTg5MzRhOThhNTUyMDJiYTI5YjViYWJkYTZmNjBkNjQ5MDNmNzkwZGIzYTgwOGZiZjNlMWJiMjIwOCJ9fX0="
			),
			new Pet(
					"Frog",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThjOTRkN2NiZjZhN2UyMDI1YzJiZjNiOTc0MDJjOGM3ZDg4OGNkYjE5MTI3N2U1NTZhZWQ1MTk1NWZkMThkIn19fQ=="
			),
			new Pet(
					"Newt",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ5NGRhNWJhOGE3MzAxMTQzYzAwNDFjYzU2NjhiNjYyNTVhMTMzMDFkMjJhMDcxZThmZWFmNzViZjNlOTYwMiJ9fX0="
			),
			new Pet(
					"Whale",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJlMDVlYzU2NDYzN2Q5NjI4ZWNkOWUzZjMxMmIwNTg4ZDliOGIyYzhkYjA0NzE0MmYxMjAwNjVhNmFhOTNjOCJ9fX0="
			),
			new Pet(
					"Shark",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmVhZmM2MTkzZmY5NzY3NDRhYzI2Mzg1YTU1OTc2ZTJlZjFiMGMwZTMwNDFmMjA2NmY5ZmNlYzgwOTQ0ZTdmMCJ9fX0="
			),
			new Pet(
					"BlackCat",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRhNzI2MDEyZGI4MDU1NWI3YzVlZTVkYmQ2OTJmZWVjYzJhYWVkMTExYzhiOThhMzNjZjgwNTVjNGZlZjAwMiJ9fX0="
			),
			new Pet(
					"ColdFrog",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU4NTJhOTU5Mjg4OTc3NDYwMTI5ODhmYmQ1ZGJhYTFiNzBiN2E1ZmI2NTE1NzAxNmY0ZmYzZjI0NTM3NGMwOCJ9fX0="
			),
			new Pet(
					"Chicken",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ4ZmVmYWQyNTVkNmU1YzlmODI0NWM1MTZmMGQ5YmJmZjRmMDU1MmRiOGIxYWZjM2RjZjdkMWRkNWJlMjNmZCJ9fX0="
			),
			new Pet(
					"Turtle",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjg0ZjRjNWJjYzUxNTVhNjNkNTViM2MwZGYwZGYxMDBlZTU4M2E0NGZkY2EyM2Q3ZWE1MzU0YzdiYThlZTA0MiJ9fX0"
			)
	);

	@ConfigSerializable
	public record Pet(
			String name,
			String texture
	) {
	}

}
