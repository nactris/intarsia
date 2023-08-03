package dot.funky.intarsia.events;


import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static dot.funky.intarsia.common.EnchantementRegistry.MERCY;
import static dot.funky.intarsia.common.EnchantementRegistry.TEMPERANCE;

public class EnchantmentsEventHandler {

    @SubscribeEvent
    public static void mercyCursePreventKill(LivingHurtEvent event) {
      MERCY.get().preventKill(event);

    }
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void temperancePreventInstantBreak(PlayerEvent.BreakSpeed event) {
        TEMPERANCE.get().breakSpeed(event);


    }
/*
Turning enchants on and off didn't feel right
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void temperanceActivate(PlayerInteractEvent.RightClickItem event) {
        TEMPERANCE.get().changeActivation(event);

    }

*/

}
