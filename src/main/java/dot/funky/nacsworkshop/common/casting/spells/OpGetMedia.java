
package dot.funky.nacsworkshop.common.casting.spells;

import at.petrak.hexcasting.api.item.MediaHolderItem;
import at.petrak.hexcasting.api.spell.Action;
import at.petrak.hexcasting.api.spell.ConstMediaAction;
import at.petrak.hexcasting.api.spell.OperationResult;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.DoubleIota;
import at.petrak.hexcasting.api.spell.iota.Iota;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class OpGetMedia implements ConstMediaAction{

    public static final Action INSTANCE = new OpGetMedia();

    @Override
    public int getArgc() {
        return 0;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingContext ctx) {
        ctx.assertEntityInRange(ctx.getCaster());
        int media = 0;
        List<ItemStack> items = new ArrayList<>();
        items.addAll(ctx.getCaster().getInventory().items);
        items.addAll(ctx.getCaster().getInventory().armor);
        items.addAll(ctx.getCaster().getInventory().offhand);

        for (ItemStack is: items){
            if (is.getItem() instanceof MediaHolderItem){
                media +=  ((MediaHolderItem) is.getItem()).getMedia(is);
            }
        }
        List<Iota> output = new ArrayList<>();
        output.add(new DoubleIota( (double) media/10000));
        return output;
    }

    @Override
    public boolean getAlwaysProcessGreatSpell() {
        return false;
    }

    @Override
    public boolean getCausesBlindDiversion() {
        return false;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return DefaultImpls.getDisplayName(this);
    }
    @Override
    public boolean isGreat() {
        return false;
    }

    @Override
    public int getMediaCost() {
        return 0;
    }

    @NotNull
    public OperationResult operate(@NotNull SpellContinuation continuation, @NotNull List stack, @Nullable Iota ravenmind, @NotNull CastingContext ctx) {
        return DefaultImpls.operate(this, continuation, stack, ravenmind, ctx);
    }
}
