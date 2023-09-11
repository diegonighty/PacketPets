package com.diegonighty.pet.tracker;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.diegonighty.pet.access.Access;
import com.diegonighty.pet.tracker.view.TrackedMinecraftEntity;
import com.diegonighty.pet.tracker.view.TrackedServerEntity;
import io.papermc.paper.chunk.system.entity.EntityLookup;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelCallback;
import org.spigotmc.AsyncCatcher;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record InjectedLevelCallback(
		LevelCallback<Entity> delegate,
		ServerLevel level
) implements LevelCallback<Entity> {

	private static final Access.FieldReflect<ServerEntity> SERVER_ENTITY_FIELD
			= Access.findFieldByType(ChunkMap.TrackedEntity.class, ServerEntity.class);

	private static final Access.FieldReflect<LevelCallback<Entity>> CALLBACKS_FIELD
			= Access.findFieldByType(EntityLookup.class, LevelCallback.class);

	@Override
	public void onTrackingStart(final Entity entity) {
		if (entity instanceof TrackedMinecraftEntity modelEntity) {
			AsyncCatcher.catchOp("entity register");

			entity.valid = true;
			final var chunkMap = level.chunkSource.chunkMap;
			final var trackedEntity = chunkMap.new TrackedEntity(entity, 32, 32, false);
			SERVER_ENTITY_FIELD.set(trackedEntity, new TrackedServerEntity(
					level,
					modelEntity,
					trackedEntity::broadcast,
					trackedEntity.seenBy
			));
			chunkMap.entityMap.put(entity.getId(), trackedEntity);

			if (entity.getOriginVector() == null) {
				entity.setOrigin(entity.getBukkitEntity().getLocation());
			}
			if (entity.getOriginWorld() == null) {
				entity.setOrigin(entity.getOriginVector().toLocation(level.getWorld()));
			}

			new EntityAddToWorldEvent(entity.getBukkitEntity()).callEvent();
		} else {
			delegate.onTrackingStart(entity);
		}
	}

	@Override
	public void onTrackingEnd(final Entity entity) {
		delegate.onTrackingEnd(entity);
	}

	@Override
	public void onSectionChange(final Entity entity) {
		delegate.onSectionChange(entity);
	}

	@Override
	public void onCreated(final Entity entity) {
		delegate.onCreated(entity);
	}

	@Override
	public void onDestroyed(final Entity entity) {
		delegate.onDestroyed(entity);
	}

	@Override
	public void onTickingStart(final Entity entity) {
		delegate.onTickingStart(entity);
	}

	@Override
	public void onTickingEnd(final Entity entity) {
		delegate.onTickingEnd(entity);
	}

	@SuppressWarnings("unchecked")
	public static void injectAt(final ServerLevel level) {
		final var entityLookup = (EntityLookup) level.getEntities();
		final var currentCallbacks = (LevelCallback<Entity>) CALLBACKS_FIELD.get(entityLookup);

		if (!(currentCallbacks instanceof InjectedLevelCallback)) {
			CALLBACKS_FIELD.set(entityLookup, new InjectedLevelCallback(currentCallbacks, level));
		}
	}

}

