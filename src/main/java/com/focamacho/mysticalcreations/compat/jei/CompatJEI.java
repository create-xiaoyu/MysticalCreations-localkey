package com.focamacho.mysticalcreations.compat.jei;

import com.blakebr0.mysticalagriculture.items.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class CompatJEI implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new CruxCropCategory(helper));
	}

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		registry.addRecipeCatalyst(new ItemStack(ModItems.itemCrafting, 1, 16), CruxCropCategory.UID);
		registry.handleRecipes(CruxCropWrapper.class, recipe -> recipe.setHelper(jeiHelpers), CruxCropCategory.UID);
		registry.addRecipes(CruxCropRecipeMaker.getRecipes(), CruxCropCategory.UID);

	}
}

