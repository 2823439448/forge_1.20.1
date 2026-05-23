package com.dlc.testmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class LavaSnowball extends Snowball {

    public LavaSnowball(Level level, LivingEntity owner) {
        super(level, owner);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            BlockPos center;
            if (result instanceof BlockHitResult blockHit) {
                center = blockHit.getBlockPos();
            } else {
                center = BlockPos.containing(result.getLocation());
            }

            int radius = 3;
            RandomSource random = this.level().getRandom();

            // 球形范围内随机把方块直接替换为岩浆
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (x * x + y * y + z * z > radius * radius)
                            continue;
                        BlockPos pos = center.offset(x, y, z);
                        double dist = Math.sqrt(x * x + y * y + z * z);
                        float chance = 1.0F - (float) (dist / radius);
                        if (random.nextFloat() < chance) {
                            BlockState state = this.level().getBlockState(pos);
                            if (!state.isAir() && !state.is(Blocks.BEDROCK) && state.getDestroySpeed(this.level(), pos) >= 0) {
                                this.level().setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }

            this.discard();
        }
    }
}
