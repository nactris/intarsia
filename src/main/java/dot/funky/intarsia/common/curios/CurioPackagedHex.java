package dot.funky.intarsia.common.curios;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class CurioPackagedHex implements ICurio {

private ItemStack stack;
    public CurioPackagedHex(ItemStack s) {
        stack = s;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }


    @Nonnull
    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }




}
