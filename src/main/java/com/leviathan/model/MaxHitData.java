package com.leviathan.model;

import com.google.inject.Singleton;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Singleton
@NoArgsConstructor
public class MaxHitData {
	private AttackStyle weaponMode;
	private int rangedLevel;
	private int rangedBoost;
	private OffensivePrayer offensivePrayer;
}