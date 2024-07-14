package com.leviathan;

import com.google.inject.Provides;
import javax.inject.Inject;

import com.leviathan.model.AttackStyle;
import com.leviathan.model.MaxHitData;
import com.leviathan.model.OffensivePrayer;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.NPCManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.http.api.item.ItemEquipmentStats;
import net.runelite.http.api.item.ItemStats;

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

	@Inject
	private ItemManager	itemManager;

	private HealthManager healthManager;
	private MaxHitData maxHitData;
	private MaxHitCalculator calc;
	private AttackStyle attackStyle;


	// Highlight leviathan when max hit will trigger enrage
	// Get current leviathan HP
	// Get players max hit

	@Override
	protected void startUp() throws Exception
	{
		if(client.getGameState() == GameState.LOGGED_IN) {
			log.info("Leviathan Helper Plugin started (v1.0.0)!");
			healthManager = new HealthManager(npcManager);
			maxHitData = new MaxHitData();

			int attackStyleVarbit = client.getVarpValue(VarPlayer.ATTACK_STYLE);
			int equippedWeaponTypeVarbit = client.getVarbitValue(Varbits.EQUIPPED_WEAPON_TYPE);
			int castingModeVarbit = client.getVarbitValue(Varbits.DEFENSIVE_CASTING_MODE);
			log.info("Attack Style Varbit: {}, equippedWeaponTypeVarbit: {}, castingModeVarbit: {}", attackStyleVarbit, equippedWeaponTypeVarbit, castingModeVarbit);
			maxHitData.setWeaponMode(getAttackStyle(equippedWeaponTypeVarbit, attackStyleVarbit, castingModeVarbit));
			maxHitData.setOffensivePrayer(getOffensivePrayer());
			maxHitData.setRangedLevel(client.getRealSkillLevel(Skill.RANGED));
			maxHitData.setRangedBoost(client.getBoostedSkillLevel(Skill.RANGED) - maxHitData.getRangedLevel());

			int maxHit = calc.calcMaxHit(maxHitData);
			log.info("Players Max Hit is: {}", maxHit);
		}
	}

	private OffensivePrayer getOffensivePrayer() {
		int rigour = client.getVarbitValue(Varbits.PRAYER_RIGOUR);
		int eagleEye = client.getVarbitValue(Varbits.PRAYER_EAGLE_EYE);
		int hawkEye = client.getVarbitValue(Varbits.PRAYER_HAWK_EYE);
		int sharpEye = client.getVarbitValue(Varbits.PRAYER_SHARP_EYE);

		if(rigour == 1) {
			return OffensivePrayer.RIGOUR;
		} else if(eagleEye == 1) {
			return OffensivePrayer.EAGLE_EYE;
		} else if(hawkEye == 1) {
			return OffensivePrayer.HAWK_EYE;
		} else if(sharpEye == 1) {
			return OffensivePrayer.SHARP_EYE;
		}

		return OffensivePrayer.NONE;
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Shutting Down Leviathan Helper Plugin.");
	}

	@Subscribe
	public void onGameTick() {

	}


	 private AttackStyle getAttackStyle(int equippedWeaponType, int attackStyleIndex, int castingMode) {
		AttackStyle[] attackStyles = getWeaponAttackStyles(equippedWeaponType);
		if (attackStyleIndex < attackStyles.length)
		{
			// from script4525
			// Even though the client has 5 attack styles for Staffs, only attack styles 0-4 are used, with an additional
			// casting mode set for defensive casting
			if (attackStyleIndex == 4)
			{
				attackStyleIndex += castingMode;
			}

			attackStyle = attackStyles[attackStyleIndex];
			if (attackStyle == null)
			{
				attackStyle = AttackStyle.OTHER;
			}
		}
		return attackStyle;
	}

    private AttackStyle[] getWeaponAttackStyles(int weaponType)
	{
		// from script4525
		int weaponStyleEnum = client.getEnum(EnumID.WEAPON_STYLES).getIntValue(weaponType);
		int[] weaponStyleStructs = client.getEnum(weaponStyleEnum).getIntVals();

		AttackStyle[] styles = new AttackStyle[weaponStyleStructs.length];
		int i = 0;
		for (int style : weaponStyleStructs) {
			StructComposition attackStyleStruct = client.getStructComposition(style);
			String attackStyleName = attackStyleStruct.getStringValue(ParamID.ATTACK_STYLE_NAME);

			AttackStyle attackStyle = AttackStyle.valueOf(attackStyleName.toUpperCase());
			if (attackStyle == AttackStyle.OTHER)
			{
				// "Other" is used for no style
				++i;
				continue;
			}
			styles[i++] = attackStyle;
		}
		return styles;
	}

	@Provides
	LeviathanHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LeviathanHelperConfig.class);
	}
}
