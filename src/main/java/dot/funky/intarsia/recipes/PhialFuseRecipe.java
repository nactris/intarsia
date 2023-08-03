package dot.funky.intarsia.recipes;

import at.petrak.hexcasting.common.lib.HexItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static java.lang.Math.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PhialFuseRecipe {
    private static final ArrayList<CombineRecipe> combineRecipes = new ArrayList<>();

    public static void initAnvilRecipes() {
        combineRecipes.add(new CombineRecipe(HexItems.BATTERY, HexItems.BATTERY, HexItems.BATTERY));
    }

    @SubscribeEvent
    public static void handleRepair(AnvilUpdateEvent event) {
        combineRecipes.forEach((data) -> {
            if (event.getLeft().getItem() == data.left && event.getRight().getItem() == data.right) {
                int capacity_left = event.getLeft().getTag().getInt("hexcasting:start_media");
                int capacity_right = event.getRight().getTag().getInt("hexcasting:start_media");
                int current_left = event.getLeft().getTag().getInt("hexcasting:media");
                int current_right = event.getRight().getTag().getInt("hexcasting:media");
                CompoundTag outtag = event.getLeft().getTag().copy();
                outtag.putInt("hexcasting:start_media", (capacity_left + capacity_right));
                outtag.putInt("hexcasting:media", (current_left + current_right)*3/4);
                event.setMaterialCost(1);
                ItemStack out = new ItemStack(data.out, 1);
                out.setTag(outtag);
                event.setOutput(out);
                int cost = capacity_left/10000 + capacity_right/10000;
                if(cost <= 1280) {
                    cost = (int) ceil(pow(1.00266, cost));
                }
                else{
                    cost = (int) min(100,ceil(pow(1.00141, cost)+23));
                }

                event.setCost(cost);
            }
        });
    }

    static class CombineRecipe {
        public final Item left;
        public final Item right;
        public final Item out;

        protected CombineRecipe(Item left, Item right, Item out) {
            this.left = left;
            this.right = right;
            this.out = out;
        }
    }

}
