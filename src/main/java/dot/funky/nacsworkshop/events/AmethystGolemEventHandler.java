package dot.funky.nacsworkshop.events;

import dot.funky.nacsworkshop.common.entities.AmethystGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AmethystGolemEventHandler {

    @SubscribeEvent
    public static void onAmethystGolemDeath(LivingDeathEvent event) {

        if (event.getEntity() instanceof AmethystGolem) {
            Level world = event.getEntity().level;

            if (!world.isClientSide()) {

                BlockPos pos = event.getEntity().blockPosition();
                world.destroyBlock(pos, true);
                world.setBlock(pos, Blocks.BUDDING_AMETHYST.defaultBlockState(), 2);
                world.levelEvent(2001, pos, Block.getId(world.getBlockState(pos)));
                world.blockUpdated(pos, Blocks.BUDDING_AMETHYST);
            }

        }
    }
}
