package com.leviathan.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OffensivePrayer
{
	RIGOUR("Rigour",1.20f, 1.23f),
	EAGLE_EYE("Eagle Eye", 1.15f, 1f),
	HAWK_EYE("Hawk Eye",1.10f, 1f),
	SHARP_EYE("Sharp Eye", 1.05f, 1f),
	NONE("None", 1f, 1f);

	private final String displayName;
	private final float attackMod;
	private final float strengthMod;
}