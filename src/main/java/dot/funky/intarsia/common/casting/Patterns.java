package dot.funky.intarsia.common.casting;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import dot.funky.intarsia.IntarsiaConfig;
import dot.funky.intarsia.common.casting.spells.*;
import dot.funky.nacsworkshop.common.casting.spells.OpHealth;
import net.minecraftforge.fml.ModList;

import static at.petrak.hexcasting.api.HexAPI.modLoc;

public class Patterns {


    public static void registerPatterns() {

        try {
            if (IntarsiaConfig.is_type_compare_enabled.get())
                PatternRegistry.mapPattern(HexPattern.fromAngles("qe", HexDir.EAST), modLoc("typeequals"), OpEqualType.INSTANCE, false);
            if (IntarsiaConfig.is_media_counter_enabled.get())
                PatternRegistry.mapPattern(HexPattern.fromAngles("aaedeaa", HexDir.SOUTH_WEST), modLoc("calculate_media"), OpGetMedia.INSTANCE, false);
            if (IntarsiaConfig.is_health_enabled.get())
                PatternRegistry.mapPattern(HexPattern.fromAngles("awa", HexDir.SOUTH_WEST), modLoc("get_health"), OpHealth.INSTANCE, false);

            if (IntarsiaConfig.is_sending_enabled.get())
                PatternRegistry.mapPattern(HexPattern.fromAngles("qqwqqqwaqqqqqdeeeedeeee", HexDir.SOUTH_WEST), modLoc("sending"), OpMailman.INSTANCE, true);
    //        awaawa

            if (ModList.get().isLoaded("curios") && IntarsiaConfig.is_curio_rw_enabled.get()) {
                PatternRegistry.mapPattern(HexPattern.fromAngles("dedweeee", HexDir.SOUTH_EAST), modLoc("slot_write"), OpWriteSlot.INSTANCE, false);
                PatternRegistry.mapPattern(HexPattern.fromAngles("aqawqqqq", HexDir.SOUTH_WEST), modLoc("slot_read"), OpReadSlot.INSTANCE, false);
            }
        } catch (PatternRegistry.RegisterPatternException e) {
            e.printStackTrace();
        }

    }


}
