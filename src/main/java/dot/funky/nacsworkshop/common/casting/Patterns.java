package dot.funky.nacsworkshop.common.casting;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import dot.funky.nacsworkshop.common.casting.spells.*;
import net.minecraftforge.fml.ModList;

import static at.petrak.hexcasting.api.HexAPI.modLoc;

public class Patterns {


    public static void registerPatterns() {

        try {
            PatternRegistry.mapPattern(HexPattern.fromAngles("qe", HexDir.EAST), modLoc("typeequals"), OpEqualType.INSTANCE, false);
            PatternRegistry.mapPattern(HexPattern.fromAngles("aaedeaa", HexDir.SOUTH_WEST), modLoc("calculate_media"), OpGetMedia.INSTANCE, false);
            PatternRegistry.mapPattern(HexPattern.fromAngles("dwd", HexDir.NORTH_WEST), modLoc("get_health"), OpHealth.INSTANCE, false);


            if (ModList.get().isLoaded("curios")) {
                PatternRegistry.mapPattern(HexPattern.fromAngles("dedweeee", HexDir.SOUTH_EAST), modLoc("slot_write"), OpWriteSlot.INSTANCE, false);
                PatternRegistry.mapPattern(HexPattern.fromAngles("aqawqqqq", HexDir.SOUTH_WEST), modLoc("slot_read"), OpReadSlot.INSTANCE, false);
            }
        } catch (PatternRegistry.RegisterPatternException e) {
            e.printStackTrace();
        }

    }


}
