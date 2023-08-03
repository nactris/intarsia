package dot.funky.intarsia;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class IntarsiaConfig {

    static final int DEFAULT_GOLEM_TIME_MIN = 12000;
    static final int DEFAULT_GOLEM_TIME_MAX = 6000;
    private static IntarsiaConfig INSTANCE;

    // Somewhere the constructor is accessible
    static {
        Pair<IntarsiaConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(IntarsiaConfig::new);
    }

    public final ForgeConfigSpec.BooleanValue is_golem_enabled;
    public final ForgeConfigSpec.BooleanValue does_golem_charge;
    public final ForgeConfigSpec.IntValue min_growth_time;
    public final ForgeConfigSpec.IntValue max_growth_time;
    public final ForgeConfigSpec.BooleanValue is_curio_rw_enabled;
    public final ForgeConfigSpec.BooleanValue is_media_counter_enabled;
    public final ForgeConfigSpec.BooleanValue is_type_compare_enabled;
    public final ForgeConfigSpec.IntValue max_curio_hex_slot;
    public final ForgeConfigSpec.BooleanValue phial_phusion;


    // In some config class
    IntarsiaConfig(ForgeConfigSpec.Builder builder) {

        builder.push("Amethyst Golem");
        is_golem_enabled = builder.comment("Can golems spawn?").define("is_golem_enabled", true);
        does_golem_charge = builder.comment("Does the golem drop charged amethyst - always false without hexcasting").define("can_golem_charge", true);

        min_growth_time = builder.comment("Minimum time does it take for golem to grow its crystal by 1 step").defineInRange("min_growth_time", DEFAULT_GOLEM_TIME_MIN, 0, Integer.MAX_VALUE);
        max_growth_time = builder.comment("Maximum time does it take for golem to grow its crystal by 1 step").defineInRange("max_growth_time", DEFAULT_GOLEM_TIME_MAX, 0, Integer.MAX_VALUE);


        builder.pop();
        builder.push("Hexcasting");
        is_curio_rw_enabled = builder.comment("Are curio read/write patterns enabled?").define("is_curio_rw_enabled", true);
        is_media_counter_enabled = builder.comment("Is Jeweler's Reflection pattern enabled?").define("is_media_counter_enabled", true);
        is_type_compare_enabled = builder.comment("Is Archivist's Distillation pattern enabled?").define("is_type_compare_enabled", true);
        max_curio_hex_slot = builder.comment("Maximum amount of curio slots you can get from hexcasting achivements").defineInRange("max_curio_hex_slots", 5, 0, 5);
        phial_phusion = builder.comment("Can phials be fused in anvil?").define("phial_phusion", true);




    }

    public static IntarsiaConfig get() {
        return INSTANCE;
    }

    public void setCommon(IntarsiaConfig ic) {
        INSTANCE = ic;
    }


}
