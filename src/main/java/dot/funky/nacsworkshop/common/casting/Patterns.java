package dot.funky.nacsworkshop.common.casting;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import dot.funky.nacsworkshop.common.casting.spells.OpEqualType;
import dot.funky.nacsworkshop.common.casting.spells.OpGetMedia;

import static at.petrak.hexcasting.api.HexAPI.modLoc;

public class Patterns {




    public static void registerPatterns() {

        try {
           PatternRegistry.mapPattern(HexPattern.fromAngles("qe", HexDir.EAST),modLoc("typeequals"), OpEqualType.INSTANCE, false);
           PatternRegistry.mapPattern(HexPattern.fromAngles("aaedeaa", HexDir.SOUTH_WEST),modLoc("calculate_media"), OpGetMedia.INSTANCE, false);
        } catch (PatternRegistry.RegisterPatternException e) {
            e.printStackTrace();
        }

    }


}
