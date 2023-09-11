package com.diegonighty.pet.config;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public final class ConfigurationContainer<C> {
	private final AtomicReference<C> config;
	private final YamlConfigurationLoader loader;
	private final Class<C> clazz;
	private final Logger logger;

	private ConfigurationContainer(
			final C config,
			final Class<C> clazz,
			final YamlConfigurationLoader loader,
			final Logger logger
	) {
		this.config = new AtomicReference<>(config);
		this.loader = loader;
		this.clazz = clazz;
		this.logger = logger;
	}

	public boolean reload() {
		try {
			final CommentedConfigurationNode node = loader.load();
			final C newConfig = node.get(clazz);
			node.set(clazz, newConfig);
			loader.save(node);
			config.set(newConfig);
			return true;
		} catch (final ConfigurateException exception) {
			logger.error("Could not reload {} configuration file", clazz.getSimpleName(), exception);
			return false;
		}
	}

	public @NotNull C get() {
		return this.config.get();
	}

	public static <C> ConfigurationContainer<C> load(
			final Logger logger,
			final Path path,
			final Class<C> clazz,
			final String file,
			final UnaryOperator<ConfigurationOptions> options
	) throws IOException {
		try {
			final var fileName = file.endsWith(".yml") ? file : file + ".yml";
			final var configPath = path.resolve(fileName);

			final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
					.defaultOptions(options)
					.path(configPath)
					.build();

			final CommentedConfigurationNode node = loader.load();
			final C config = node.get(clazz);
			node.set(clazz, config);
			loader.save(node);
			return new ConfigurationContainer<>(config, clazz, loader, logger);
		} catch (final ConfigurateException exception) {
			logger.error("Could not load {} configuration file", clazz.getSimpleName(), exception);
			throw exception;
		}
	}

	public static <C> ConfigurationContainer<C> load(
			final Logger logger,
			final Path path,
			final Class<C> clazz,
			final String file
	) throws IOException {
		return load(logger, path, clazz, file, opts -> opts
				.shouldCopyDefaults(true)
		);
	}
}
