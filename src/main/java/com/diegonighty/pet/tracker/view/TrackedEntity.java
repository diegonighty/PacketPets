package com.diegonighty.pet.tracker.view;

import org.bukkit.entity.Player;

public interface TrackedEntity {

	void track(final Player player);

	void untrack(final Player player);

}
