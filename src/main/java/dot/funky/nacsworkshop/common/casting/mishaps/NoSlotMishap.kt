package dot.funky.nacsworkshop.common.casting.mishaps



import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack


class NoSlotMishap(val item: ItemStack, val slot: Int) : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
            dyeColor(DyeColor.BROWN)

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
    }

    override fun errorMessage(ctx: CastingContext, errorCtx: Context) =
        "hexcasting.mishap.no_slot".asTranslatedComponent( actionName(errorCtx.action), slot.toString())



}