package dot.funky.intarsia;

import net.minecraftforge.common.ForgeConfigSpec;

public class IntarsiaConfig {

    static final int DEFAULT_GOLEM_TIME_MIN = 12000;
    static final int DEFAULT_GOLEM_TIME_MAX = 6000;
    public static final ForgeConfigSpec CONFIG_SPEC;

    // Somewhere the constructor is accessible
    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setup(configBuilder);
        CONFIG_SPEC = configBuilder.build();
    }


    public static ForgeConfigSpec.BooleanValue is_health_enabled;
    public static ForgeConfigSpec.BooleanValue is_golem_enabled;
    public static ForgeConfigSpec.BooleanValue does_golem_charge;
    public static ForgeConfigSpec.IntValue min_growth_time;
    public static ForgeConfigSpec.IntValue max_growth_time;
    public static ForgeConfigSpec.BooleanValue is_curio_rw_enabled;
    public static ForgeConfigSpec.BooleanValue is_media_counter_enabled;
    public static ForgeConfigSpec.BooleanValue is_type_compare_enabled;
    public static ForgeConfigSpec.IntValue max_curio_hex_slot;
    public static ForgeConfigSpec.BooleanValue phial_phusion;


    // In some config class
    private static void setup(ForgeConfigSpec.Builder builder) {

        builder.push("Amethyst Golem");
        is_golem_enabled = builder.comment("Can golems spawn?").define("is_golem_enabled", true);
        does_golem_charge = builder.comment("Does the golem drop charged amethyst - always false without hexcasting").define("can_golem_charge", true);

        min_growth_time = builder.comment("Minimum time does it take for golem to grow its crystal by 1 step").defineInRange("min_growth_time", DEFAULT_GOLEM_TIME_MIN, 0, Integer.MAX_VALUE);
        max_growth_time = builder.comment("Maximum time does it take for golem to grow its crystal by 1 step").defineInRange("max_growth_time", DEFAULT_GOLEM_TIME_MAX, 0, Integer.MAX_VALUE);


        builder.pop();
        builder.push("Hexcasting");
        is_curio_rw_enabled = builder.comment("Are curio read/write patterns enabled?").define("is_curio_rw_enabled", true);
        is_health_enabled = builder.comment("Is Kahuna's Purification pattern enabled?").define("is_health_enabled", true);
        is_media_counter_enabled = builder.comment("Is Jeweler's Reflection pattern enabled?").define("is_media_counter_enabled", true);
        is_type_compare_enabled = builder.comment("Is Archivist's Distillation pattern enabled?").define("is_type_compare_enabled", true);
        max_curio_hex_slot = builder.comment("Maximum amount of curio slots you can get from hexcasting achivements").defineInRange("max_curio_hex_slots", 5, 0, 5);
        phial_phusion = builder.comment("Can phials be fused in anvil?").define("phial_phusion", true);




    }




}
