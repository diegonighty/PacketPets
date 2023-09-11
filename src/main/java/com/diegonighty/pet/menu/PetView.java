package com.diegonighty.pet.menu;

import com.diegonighty.pet.config.PetConfiguration;
import com.diegonighty.pet.entity.PetManager;
import com.diegonighty.pet.skull.Skulls;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.component.BukkitItemComponentBuilder;
import me.devnatan.inventoryframework.component.Pagination;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.state.State;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PetView extends View {

	private final State<Pagination> paginationState;
	private final PetManager petManager;

	public PetView(final PetConfiguration petConfig, final PetManager petManager) {
		this.petManager = petManager;
		this.paginationState = paginationState(
				() -> new ArrayList<>(petConfig.pets),
				this::buildItemPet
		);
	}

	private void buildItemPet(
			final BukkitItemComponentBuilder builder,
			final PetConfiguration.Pet pet
	) {
		builder.withItem(Skulls.create(pet))
				.onClick(ctx -> {
					final var player = ctx.getPlayer();

					player.sendMessage(Component.text("You selected " + pet.name()));
					petManager.spawn(pet, player);
					ctx.setCancelled(true);
				});
	}

	@Override
	public void onInit(final ViewConfigBuilder config) {
		config.title("Select a pet!");
		config.size(1);
		config.layout(
				"  OOOOO  "
		);
	}

	@Override
	public void onFirstRender(final RenderContext render) {
		final var pagination = paginationState.get(render);

		render.firstSlot(new ItemStack(Material.ARROW))
				.watch(paginationState)
				.displayIf(pagination::canBack)
				.onClick(pagination::back);

		render.lastSlot(new ItemStack(Material.ARROW))
				.watch(paginationState)
				.displayIf(pagination::canAdvance)
				.onClick(pagination::advance);
	}
}
