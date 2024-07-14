package com.leviathan.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // for gson
public class ItemStats
{

	private int itemId;
	private String name;

	@SerializedName("arange")
	private int accuracyRanged;

	@SerializedName("rstr")
	private int strengthRanged;

	@SerializedName(value = "speed", alternate = {"aspeed"})
	private int speed;

	private int prayer;

	private int slot;
}