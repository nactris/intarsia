package dot.funky.nacsworkshop.common.casting.mishaps



import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotResult
import java.util.*


class SlotMishap(val item: ItemStack, val slot: Int, val wanted: Component) : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
            dyeColor(DyeColor.BROWN)

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
        yeet(ctx,slot)
    }

    override fun errorMessage(ctx: CastingContext, errorCtx: Context) = if (item.isEmpty)
        "hexcasting.mishap.no_item.slot".asTranslatedComponent( actionName(errorCtx.action), wanted)
    else
       "hexcasting.mishap.bad_item.slot".asTranslatedComponent( actionName(errorCtx.action), wanted, item.count, item.displayName)

    private fun yeet(ctx: CastingContext, slot: Int) {

        val item: Optional<SlotResult> = CuriosApi.getCuriosHelper().findCurio(ctx.caster, "castingdevice", slot)
        if (item.isPresent ) {
            CuriosApi.getCuriosHelper().setEquippedCurio(ctx.caster, "castingdevice", slot, ItemStack.EMPTY)
            val delta = ctx.caster.lookAngle.scale(0.5)
            val entity = ItemEntity(ctx.world, ctx.position.x, ctx.position.y, ctx.position.z, item.get().stack(), delta.x + (Math.random() - 0.5) * 0.1, delta.y + (Math.random() - 0.5) * 0.1, delta.z + (Math.random() - 0.5) * 0.1)
            entity.setPickUpDelay(40)
            ctx.world.addWithUUID(entity)
        }
    }

}