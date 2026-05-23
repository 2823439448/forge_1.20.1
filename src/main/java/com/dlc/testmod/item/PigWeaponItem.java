package com.dlc.testmod.item;

import com.dlc.testmod.entity.LavaSnowball;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class PigWeaponItem extends SwordItem {

    private static final int COOLDOWN_TICKS = 20;

    public PigWeaponItem(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            shoot(player, player.getItemInHand(hand));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public static void shoot(Player player, ItemStack stack) {
        if (!(stack.getItem() instanceof PigWeaponItem weapon))
            return;
        if (player.getCooldowns().isOnCooldown(weapon))
            return;

        LavaSnowball snowball = new LavaSnowball(player.level(), player);
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        player.level().addFreshEntity(snowball);
        player.getCooldowns().addCooldown(weapon, COOLDOWN_TICKS);
    }
}
