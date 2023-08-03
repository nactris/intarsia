package dot.funky.intarsia.capabilities;

import dot.funky.intarsia.IntarsiaConfig;
import dot.funky.intarsia.common.curios.CurioPackagedHex;
import dot.funky.intarsia.common.network.PacketHandler;
import dot.funky.intarsia.events.CurioKeyboardEventHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.Math.min;

public class CurioCompatibilityHandler {


    public static void onGrantAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        if (event.getAdvancement().getId().toString().equals("hexcasting:y_u_no_cast_angy") || event.getAdvancement().getId().toString().equals("hexcasting:aab_big_cast") || event.getAdvancement().getId().toString().equals("hexcasting:aaa_wasteful_cast") || event.getAdvancement().getId().toString().equals("hexcasting:opened_eyes") || event.getAdvancement().getId().toString().equals("hexcasting:enlightenment")) {

            int slots = CuriosApi.getSlotHelper().getSlotsForType(event.getEntity(), "castingdevice");
            CuriosApi.getSlotHelper().setSlotsForType("castingdevice", event.getEntity(), min(IntarsiaConfig.get().max_curio_hex_slot.get(), slots + 1));
        }
    }

    public static void IMCCurios(final InterModEnqueueEvent event) {

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("castingdevice").size(0).build() //
        );
    }

    public void attachCurioCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();

        if (ModList.get().isLoaded("hexcasting") && stack.getItem() instanceof at.petrak.hexcasting.api.item.HexHolderItem) {
            ICurio curioPackagedHex = new CurioPackagedHex(stack);

            evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                final LazyOptional<ICurio> curio = LazyOptional.of(() -> curioPackagedHex);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, curio);
                }
            });
        }
    }

    public static void clientTick(TickEvent.ClientTickEvent evt) {
        if (evt.phase == TickEvent.Phase.END) {
            CurioKeyboardEventHandler.clientTick();
        }
    }

    public static void init(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, new CurioCompatibilityHandler()::attachCurioCapabilities);
    }

}
