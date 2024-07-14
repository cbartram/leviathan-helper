package com.leviathan.model;


import com.google.inject.Singleton;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.api.Skill;

import java.util.Map;

@Data
@Singleton
@NoArgsConstructor
public class MaxHitData {
	private AttackStyle weaponMode;
	private Map<Skill, Integer> playerSkills;
	private Map<Skill, Integer> playerBoosts;
	private int equipmentRangedStrength;
	private Prayer offensivePrayer;
}