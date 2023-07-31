
package dot.funky.nacsworkshop.common.casting.spells;
import at.petrak.hexcasting.api.spell.Action;
import at.petrak.hexcasting.api.spell.ConstMediaAction;
import at.petrak.hexcasting.api.spell.OperationResult;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.BooleanIota;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.IotaType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class OpEqualType implements ConstMediaAction{

    public static final Action INSTANCE = new OpEqualType();

    @Override
    public int getArgc() {
        return 2;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingContext ctx) {
        IotaType t1 = args.get(0).getType();
        IotaType t2 = args.get(1).getType();
        List<Iota> output = new ArrayList<Iota>();
        output.add(new BooleanIota( t1==t2 ));
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
