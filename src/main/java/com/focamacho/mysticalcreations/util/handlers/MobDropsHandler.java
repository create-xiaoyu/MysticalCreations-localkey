package com.focamacho.mysticalcreations.util.handlers;

import com.blakebr0.mysticalagriculture.items.ModItems;
import com.focamacho.mysticalcreations.seeds.CustomSeed;
import com.focamacho.mysticalcreations.seeds.CustomSeeds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.List;
import java.util.Random;

public class MobDropsHandler {

	public static Random rand = new Random(); 
	
	@SubscribeEvent
    public void onMobDrops(LivingDropsEvent event){
        EntityLivingBase attacked = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity entity = source.getTrueSource();
        List<EntityItem> drops = event.getDrops();
        
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack held = player.getHeldItemMainhand();
            
            if(!held.isEmpty() && held.getItem() == ModItems.itemSouliumDagger){
            	for(CustomSeed seed : CustomSeeds.chunkSeeds) {
            		List<ResourceLocation> entities = seed.getEntities();
            		if(entities.contains(EntityRegistry.getEntry(attacked.getClass()).getRegistryName())) {
            			drop(attacked, seed.getChunk(), 1, 0, getChanceFromTier(seed.getTier()), drops);
            		}
            	}
            }
        }
	}
    
    public static int getChance(int chance){
    	int dropChance = 0;
    	if(chance == 0){
    		return 0;
    	}
    	if(rand.nextInt(100) < chance){
    		dropChance++;
    	}
    	return dropChance;
    }
    
    public static int getChanceFromTier(int tier){
    	int chance = 35 - (tier * 5);
    	return getChance(chance);
    }

    public void drop(Entity entity, Item item, int amount, int meta, int chance, List<EntityItem> drops){
    	if (chance > 0) {
        	ItemStack stack = new ItemStack(item, amount, meta);
        	EntityItem drop = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, stack);
        	if(stack.getCount() >= 1){
        		drops.add(drop);
        	}
    	}
    }
}
