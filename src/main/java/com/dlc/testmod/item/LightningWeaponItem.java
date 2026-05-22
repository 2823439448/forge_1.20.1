package com.dlc.testmod.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class LightningWeaponItem extends SwordItem {

    public LightningWeaponItem(Properties properties) {
        super(Tiers.DIAMOND, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level().isClientSide) {
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, target.level());
            lightning.setPos(target.getX(), target.getY(), target.getZ());
            target.level().addFreshEntity(lightning);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
