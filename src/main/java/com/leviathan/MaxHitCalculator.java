package com.leviathan;

import com.leviathan.model.*;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;


@RequiredArgsConstructor
public class MaxHitCalculator {

	private MaxHitData input;

    // 1. Gear + void + tbow
    // 2. Current range level (including potions)
    // 3. Prayers being prayed
    public int calculate() {
//		[[0.5 *  r_str * (equip_r_str + 64) / 640 ] * gear_bonus]
        int maxHit = getRangedStrengthBonus(input);

		// Compute the total range strength of all equipment

		maxHit *= (input.getEquipmentStats().getStrengthRanged() + 64);
		maxHit += 320;
		maxHit /= 640;
		return maxHit;
    }

	private int calculateEquipmentRangeStrengthBonus(MaxHitData input) {
		return 0;
	}


    private int getRangedStrengthBonus(MaxHitData input) {
		int rngStrength = input.getPlayerSkills().get(Skill.RANGED) + input.getPlayerBoosts().get(Skill.RANGED);

		Prayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != null)
			rngStrength = (int) (rngStrength * offensivePrayer.getStrengthMod());

		if (input.getWeaponMode().getCombatFocus() == CombatFocus.ACCURATE)
			rngStrength += 3;

		rngStrength += 8;

//		float voidLevel = voidLevel(input);
		float voidLevel = 2;
		if (voidLevel == 2)
			rngStrength = (int) (rngStrength * 1.125f);
		else if (voidLevel == 1)
			rngStrength = (int) (rngStrength * 1.125f);

		// unsure whether this should be here or in effectiveRangedStrength
		// unlike tbow accuracy, this one could make a difference
//		if (EquipmentRequirement.TBOW.isSatisfied(input))
//			rngStrength = (int) (rngStrength * tbowDmgModifier(input));


		return rngStrength;
	}
}
