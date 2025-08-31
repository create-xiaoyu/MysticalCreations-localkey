package com.focamacho.mysticalcreations.items;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.*;

public class ItemEssence extends Item {

	private final String name;
	
	public ItemEssence(String name) {
		this.setRegistryName(name + "_essence");
		this.setUnlocalizedName(name + "_essence");
		this.setCreativeTab(MysticalAgriculture.CREATIVE_TAB);
		this.name = name;
	}

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        StringBuilder nameFinal = new StringBuilder();

        // 获取物品名称的本地化翻译
        String customNameKey = "item.mysticalcreations.custom.name." + this.name;
        String customName = I18n.translateToLocal(customNameKey);

        // 没有翻译键使用原来的逻辑
        if (!customName.equals(customNameKey)) {
            nameFinal.append(customName);
        } else {

            String[] nameParts = this.name.split("_");
            if(nameParts.length > 1) {
                for(String part : nameParts) {
                    nameFinal.append(part.substring(0, 1).toUpperCase())
                            .append(part.substring(1)).append(" ");
                }
            } else {
                nameFinal.append(nameParts[0].substring(0, 1).toUpperCase())
                        .append(nameParts[0].substring(1)).append(" ");
            }
        }

        nameFinal.append(I18n.translateToLocal("item.mysticalcreations.essence.name"));

        return nameFinal.toString();
    }

}
