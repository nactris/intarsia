package dot.funky.nacsworkshop.common;

import dot.funky.nacsworkshop.NacsWorkshop;
import dot.funky.nacsworkshop.common.entities.AmethystGolem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NacsWorkshop.MODID);

    public static final RegistryObject<EntityType<AmethystGolem>> AMETHYST_GOLEM = ENTITIES.register("amethyst_golem", () -> EntityType.Builder.of(AmethystGolem::new, MobCategory.MISC).sized(0.85F, 1.5F).build("amethyst_golem"));
}
