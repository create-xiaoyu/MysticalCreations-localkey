package com.focamacho.mysticalcreations.util.handlers;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor {

	private final int tintIndex;
	private final int color;
	
	public ItemColorHandler(Integer color, int tintIndex) {
		this.color = color;
		this.tintIndex = tintIndex;
	}
	
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		if(tintIndex == this.tintIndex) {
			return this.color;
		}
		return -1;
	}

}
