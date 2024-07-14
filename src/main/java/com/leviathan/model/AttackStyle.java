package com.leviathan.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AttackStyle
{

	ACCURATE("Accurate"),
	LONGRANGE("Longrange"),
	RAPID("Rapid"),
	OTHER("Other");

	@Getter
	private final String displayName;
}