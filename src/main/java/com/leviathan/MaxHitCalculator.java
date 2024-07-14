package com.leviathan;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leviathan.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.http.api.item.ItemEquipmentStats;
import net.runelite.http.api.item.ItemStats;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class MaxHitCalculator {

	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

    public int calcMaxHit(MaxHitData input) {
		// [[0.5 + r_str * (equip_r_str + 64) / 640 ] * gear_bonus]
        int rangeStr = calculateEffectiveRangeStr(input);
		int equipmentStrength = calcEquipmentRangeStr(client.getLocalPlayer().getPlayerComposition().getEquipmentIds()) + 64;
		// TODO factor in Tbow's gear bonus
		return (int) (0.5 + (rangeStr * equipmentStrength)) / 640;
    }


	private int calcEquipmentRangeStr(int[] equippedItems) {
		int rangedStrengthBonus = 0;
        for (int id : equippedItems) {
			if (id < 512) continue; // Not a valid item
			id -= 512;
			ItemStats s = itemManager.getItemStats(id, true);
			ItemEquipmentStats stats = s.getEquipment();
			if (stats != null) {
				log.info("Calculated equipment range bonus for id: {} ({})", id, s.getEquipment().getRstr());
				rangedStrengthBonus += s.getEquipment().getRstr();
			}
		}
		return rangedStrengthBonus;
	}


	private int calculateEffectiveRangeStr(MaxHitData input) {
		// [(Range level + boost) * prayer bonus + attack style + 8] * void modifier
		int rangeLevelAndBoost = (input.getRangedLevel() + input.getRangedBoost());

		OffensivePrayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != OffensivePrayer.NONE)
			rangeLevelAndBoost = (int) (rangeLevelAndBoost * offensivePrayer.getStrengthMod());

		if (input.getWeaponMode() == AttackStyle.ACCURATE)
			rangeLevelAndBoost += 3;

		rangeLevelAndBoost += 8;
		// TODO Void calcs here
		return rangeLevelAndBoost;
	}
}
