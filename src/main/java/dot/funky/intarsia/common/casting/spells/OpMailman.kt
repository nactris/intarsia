package dot.funky.intarsia.common.casting.spells


import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getVec3
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadOffhandItem
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.phys.Vec3
import thedarkcolour.kotlinforforge.forge.vectorutil.plus

object OpMailman : SpellAction {


    override val argc = 1
    override val isGreat = true

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val delta = args.getVec3(0, argc)

        val stack = ctx.caster.getItemInHand(ctx.otherHand)

        if (stack.isEmpty) throw MishapBadOffhandItem(stack, ctx.otherHand, "intarsia.send".asTranslatedComponent)

        val cost = MediaConstants.CRYSTAL_UNIT * stack.count / stack.maxStackSize
        val targetMiddlePos = ctx.caster.position().add(0.0, ctx.caster.eyeHeight / 2.0, 0.0)
        return Triple(Spell(stack, delta), cost, listOf(ParticleSpray.cloud(targetMiddlePos, 2.0), ParticleSpray.burst(targetMiddlePos.add(delta), 2.0)))


    }


    private class Spell(val stack: ItemStack, val delta: Vec3) : RenderedSpell {
        override fun cast(ctx: CastingContext) {

            var list = listOf<ItemStack>()

            if (!stack.hasTag() || !stack.tag!!.contains("BlockEntityTag") || !stack.tag!!.getCompound("BlockEntityTag").contains("Items")) {

                list = list.plus(stack)

            } else {
                var itemlist = stack.tag!!.getCompound("BlockEntityTag").getList("Items", 10).copy()
                for (i in 0..itemlist.size) {

                    list = list.plus(ItemStack.of(itemlist.getCompound(i)))
                    stack.tag!!.getCompound("BlockEntityTag").getList("Items", 10).remove(itemlist.getCompound(i))

                }
            }
            val pos = ctx.caster.eyePosition + this.delta
            val block = ctx.caster.level.getBlockEntity(BlockPos(pos))


            for (s in list) {

                if (block != null && block as BaseContainerBlockEntity != null) {


                    for (i in 0..block.containerSize - 1) {
                        var item = block.getItem(i)
                        if (item.isEmpty && block.canPlaceItem(i, s)) {
                            item = s.copy()
                            s.count = 0
                            block.setItem(i, item)
                            break
                        } else if (item.`is`(s.item) && block.canPlaceItem(i, s) && item.hasTag() == s.hasTag() && item.tag == s.tag && item.count + s.count <= item.maxStackSize) {
                            item.count += s.count
                            s.count = 0

                            block.setItem(i, item)
                            break

                        } else if (item.`is`(s.item) && block.canPlaceItem(i, s) && item.hasTag() == s.hasTag() && item.tag == s.tag && item.count != item.maxStackSize) {
                            s.count -= item.maxStackSize - item.count
                            item.count = item.maxStackSize
                        }
                    }

                }
                if (s.count > 0) {

                    val delta = ctx.caster.lookAngle.scale(0.5)
                    val entity = ItemEntity(ctx.world, pos.x, pos.y, pos.z, s.copy(), delta.x + (Math.random() - 0.5) * 0.1, delta.y + (Math.random() - 0.5) * 0.1, delta.z + (Math.random() - 0.5) * 0.1)
                    entity.setPickUpDelay(40)
                    s.count = 0
                    ctx.world.addWithUUID(entity)
                }
            }
        }
    }
}
