package com.leviathan.model;

import lombok.Data;
import lombok.Getter;

@Data
public class WeaponMode
{

	@Getter
	private final String displayName;

	@Getter
	private final AttackStyle attackStyle;

}
