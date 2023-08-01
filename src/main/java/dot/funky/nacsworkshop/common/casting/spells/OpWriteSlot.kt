package dot.funky.nacsworkshop.common.casting.spells

import at.petrak.hexcasting.api.item.IotaHolderItem
import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getPositiveInt
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapOthersName
import at.petrak.hexcasting.api.spell.mishaps.MishapOthersName.Companion.getTrueNameFromDatum
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import dot.funky.nacsworkshop.common.casting.mishaps.NoSlotMishap
import dot.funky.nacsworkshop.common.casting.mishaps.SlotMishap
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.CuriosApi

// we make this a spell cause imo it's a little ... anticlimactic for it to just make no noise
object OpWriteSlot : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        ctx.assertEntityInRange(ctx.caster)
        val iota = args[1]
        val slot = args.getPositiveInt(0, argc)
        val slotcount = CuriosApi.getSlotHelper().getSlotsForType(ctx.caster, "castingdevice")
        val itemOption = CuriosApi.getCuriosHelper().findCurio(ctx.caster, "castingdevice", slot)
        val item: ItemStack
        if (itemOption.isEmpty) {
            item = ItemStack.EMPTY
        } else {
            item = itemOption.get().stack
        }
        if (slot >= slotcount) {
            throw NoSlotMishap(item, slot)
        }

        if (itemOption.isEmpty || !(item.item is IotaHolderItem)) {
            throw SlotMishap(item, slot, "hexcasting.mishap.bad_item.iota.write".asTranslatedComponent)
        }
        if (!(item.item as IotaHolderItem).canWrite(item, iota)) {
            throw SlotMishap(item, slot, "hexcasting.mishap.bad_item.iota.readonly".asTranslatedComponent(iota.type.typeName()) )
        }

        val tn = getTrueNameFromDatum(iota, ctx.caster as Player)
        if (tn != null) {
            throw MishapOthersName(tn)
        }

        (item.item as IotaHolderItem).writeDatum(item, iota)

        return listOf()
    }



}
