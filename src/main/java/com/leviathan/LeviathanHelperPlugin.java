package com.leviathan;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.NPCManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Leviathan Helper"
)
public class LeviathanHelperPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private LeviathanHelperConfig config;

	@Inject
	private NPCManager npcManager;

	private HealthManager healthManager;


	// Highlight leviathan when max hit will trigger enrage
	// Get current leviathan HP
	// Get players max hit

	@Override
	protected void startUp() throws Exception
	{
		log.info("Leviathan Helper Plugin started (v1.0.0)!");
		healthManager = new HealthManager(npcManager);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Shutting Down Leviathan Helper Plugin.");
	}

	@Subscribe
	public void onGameTick() {

	}

	@Provides
	LeviathanHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LeviathanHelperConfig.class);
	}
}
