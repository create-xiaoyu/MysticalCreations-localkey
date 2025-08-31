package com.focamacho.mysticalcreations.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemCrop extends ItemBlock {

    private final String name;

    public ItemCrop(Block block, String name) {
        super(block);
        this.setCreativeTab((CreativeTabs)null);
        this.setUnlocalizedName(name + "_crop");
        this.setRegistryName(name + "_crop");
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

        nameFinal.append(I18n.translateToLocal("tile.mysticalcreations.crop.name"));

        return nameFinal.toString();
    }

}