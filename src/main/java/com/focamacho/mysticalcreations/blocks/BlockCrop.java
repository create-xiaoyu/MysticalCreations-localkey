package com.focamacho.mysticalcreations.blocks;

import com.blakebr0.cucumber.util.Utils;
import com.blakebr0.mysticalagriculture.blocks.crop.BlockMysticalCrop;
import com.blakebr0.mysticalagriculture.config.ModConfig;
import com.blakebr0.mysticalagriculture.items.ModItems;
import com.focamacho.mysticalcreations.items.ItemCrop;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockCrop extends BlockMysticalCrop {

    private Item seed;
    private final Item crop;
    private final Item essence;
    private final String name;
    private ItemStack crux = null;
    private final int tier;

    public BlockCrop(String name, Item essence, int tier) {
		super(name);
		this.setUnlocalizedName(name + "_crop");
		this.setRegistryName(name + "_crop");
        this.setCreativeTab((CreativeTabs)null);

        this.name = name;
        this.tier = tier;
        this.essence = essence;
        this.crop = new ItemCrop(this, this.name);
    }

    @Override
    public String getLocalizedName() {
    	StringBuilder nameFinal = new StringBuilder();
    	nameFinal.append(I18n.translateToLocal("tile.mysticalcreations.crop.name.before"));
		String[] name = this.name.split("_");
		if(name.length > 1) {
			for(String string : name) {
				nameFinal.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
			}
		} else {
			nameFinal.append(name[0].substring(0, 1).toUpperCase()).append(name[0].substring(1)).append(" ");
		}
		nameFinal.append(I18n.translateToLocal("tile.mysticalcreations.crop.name"));
		return nameFinal.toString();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
    	this.checkAndDropBlock(world, pos, state);
    	int i = this.getAge(state);
        if(world.getLightFromNeighbors(pos.up()) >= 9){
	    	if(i < this.getMaxAge()){
	    		float f = getGrowthChance(this, world, pos);
	    		if(rand.nextInt((int)(35.0F / f) + 1) == 0) {
	    			if(this.canGrow(world, pos, state, world.isRemote)) {
	    				world.setBlockState(pos, this.withAge(i + 1), 2);
	    			}
	    		}
	    	}
        }
    }

    public ItemCrop getItemCrop() {
    	return (ItemCrop) crop;
    }

	public int getTier() {
		return this.tier;
	}

	public ItemStack getCrux() {
		return crux;
	}

	public void setCrux(ItemStack crux) {
    	this.crux = crux;
	}

	@Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state){
        return false;
    }

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if(!canGrow(world, pos, state, world.isRemote)) return;
		super.grow(world, rand, pos, state);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		if(this.crux != null && worldIn.getBlockState(pos.down(2)) != Block.getBlockFromItem(this.crux.getItem()).getStateFromMeta(this.crux.getMetadata())) return false;
		return super.canGrow(worldIn, pos, state, isClient);
	}

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    	return EnumPlantType.Crop;
    }

	@Override
	public BlockMysticalCrop setSeed(Item seed) {
		this.seed = seed;
		return this;
	}

	@Override
    public Item getSeed(){
    	return this.seed;
    }

    @Override
    public Item getCrop() {
    	return this.crop;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? this.crop : this.seed;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        int age = state.getValue(AGE);
        Random rand = Utils.rand;

        int essence = 0;
        int fertilizer = 0;
        int seeds = 1;

        if(age == 7){
	       	if(ModConfig.confSeedChance > 0 && this.tier != 6){
	       		if(rand.nextInt(100 / ModConfig.confSeedChance) > 0){
	        		seeds = 1;
	       		} else {
	       			seeds = 2;
	       		}
	       	}
	       	else {
	       		seeds = 1;
	       	}
        }

        if(age == 7){
        	if(ModConfig.confFertilizedEssenceChance > 0){
        		if(!(rand.nextInt(100 / ModConfig.confFertilizedEssenceChance) > 0)) {
					fertilizer = 1;
        		}
        	}
        }

        if(age == 7){
        	if(ModConfig.confEssenceChance > 0){
                if(rand.nextInt(100 / ModConfig.confEssenceChance) > 0){
                	essence = 1;
                } else {
                	essence = 2;
                }
        	}
        	else essence = 1;
        }

        drops.add(new ItemStack(this.getSeed(), seeds, 0));
        if(essence > 0){ drops.add(new ItemStack(this.essence, essence, 0)); }
        if(fertilizer > 0 && ModConfig.confFertilizedEssence){ drops.add(new ItemStack(ModItems.itemFertilizedEssence, fertilizer, 0)); }
    }

}
