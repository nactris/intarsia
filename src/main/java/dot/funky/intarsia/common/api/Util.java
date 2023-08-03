package dot.funky.intarsia.common.api;

import kotlin.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.util.Mth.floor;

public class Util {


    public static Pair<Boolean, BlockPos> RayTraceBlocks(Level level, Vec3 from, Vec3 to, int step) {


        for (int i = 0; i < step; i++) {
            Vec3 pos = from.lerp(to, (double) i / step);


            for (AABB box : level.getBlockState(new BlockPos(pos)).getShape(level, new BlockPos(pos)).toAabbs()) {
             Vec3 relpos = new Vec3(pos.x-floor(pos.x), pos.y-floor(pos.y),pos.z-floor(pos.z));

                if ( box.contains(relpos) && !level.getBlockState(new BlockPos(pos)).isAir()) {
                    return new Pair(true, new BlockPos(pos));
                }
            }

        }
        return new Pair(false, new BlockPos(to));

    }


}
