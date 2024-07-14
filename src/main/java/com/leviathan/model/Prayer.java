package com.leviathan.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prayer
{
	RIGOUR("Rigour",1.2f, 1.23f, 24),
	EAGLE_EYE("Eagle Eye", 1.15f, 1f, 12),
	HAWK_EYE("Hawk Eye",1.1f, 1f, 6),
	SHARP_EYE("Sharp Eye", 1.05f, 1f, 3),
	CLARITY_OF_THOUGHT("Clarity of Thought", 1.05f, 1f, 3);

	private final String displayName;
	private final float attackMod;
	private final float strengthMod;
	private final int drainRate;
}