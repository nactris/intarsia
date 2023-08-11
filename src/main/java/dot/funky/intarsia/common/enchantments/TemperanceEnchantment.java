package dot.funky.intarsia.common.enchantments;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class TemperanceEnchantment extends Enchantment {

    public TemperanceEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return (stack.getItem() instanceof DiggerItem || stack.getItem() instanceof SwordItem);
    }
    public boolean isTreasureOnly() {
        return true;
    }
    @Override
    public Component getFullname(int level) {
        MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
        if (level < 2){
            mutablecomponent.withStyle(ChatFormatting.GRAY);
        } else {
            mutablecomponent.withStyle(ChatFormatting.DARK_GRAY);
        }
        return mutablecomponent;
    }

    public void breakSpeed(PlayerEvent.BreakSpeed event) {

        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) return;


        if (stack.getEnchantmentLevel(this) > 0 ) { //&& stack.getEnchantmentLevel(this) < 2
            float hardness = event.getState().getDestroySpeed(player.level, event.getPosition().orElse(BlockPos.ZERO));
            if (stack.getDestroySpeed(event.getState()) > 1.0F && 30.0F * hardness <= event.getOriginalSpeed()) {
                event.setNewSpeed(29.99999F * hardness);
            }

        }
    }


    public void changeActivation(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel(this) > 0 && event.getEntity().isCrouching() && event.getHand().equals(InteractionHand.OFF_HAND)) {
            event.setCanceled(true);

        } else if (event.getEntity().isCrouching() && event.getItemStack().getEnchantmentLevel(this) > 0) {
            setActive(event.getEntity(), event.getHand(), !isActive(event.getEntity(), event.getHand()));

            if (event.getEntity().level.isClientSide) {
                float p = isActive(event.getEntity(), event.getHand()) ? 2.6f : 1.6f;
                long  a = event.getEntity().getRandom().nextLong();
                event.getEntity().level.playSeededSound( event.getEntity(),  event.getEntity(), SoundEvents.LODESTONE_COMPASS_LOCK,  event.getEntity().getSoundSource(), 1f, p,1);
                event.getEntity().displayClientMessage(Component.translatable(isActive(event.getEntity(), event.getHand()) ? "intarsia.enchantments.temperance_on" : "intarsia.enchantments.temperance_off"),true);
                event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 5);
              // event.getEntity().swing(event.getHand(),false);

            }
        }
    }

    public boolean isActive(Player player, InteractionHand hand) {

        if (!player.getItemInHand(hand).hasTag()) {
            setActive(player, hand, true);
        }
        return player.getItemInHand(hand).getEnchantmentLevel(this) <= 1;
    }

    public void setActive(Player player, InteractionHand hand, boolean isActive) {

        ResourceLocation enchantmentTag = EnchantmentHelper.getEnchantmentId(this);
        ListTag listtag = player.getItemInHand(hand).getEnchantmentTags();
        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag currentTag = listtag.getCompound(i);
            ResourceLocation currentEnchantment = EnchantmentHelper.getEnchantmentId(currentTag);
            if (currentEnchantment != null && currentEnchantment.equals(enchantmentTag)) {
                currentTag.putInt("lvl", isActive ? 1 : 2);
                listtag.set(i, currentTag);
            }
        }

    }


}
