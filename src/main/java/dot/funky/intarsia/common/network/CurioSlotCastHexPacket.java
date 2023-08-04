package dot.funky.intarsia.common.network;

import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.spell.SpellList;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.ListIota;
import at.petrak.hexcasting.api.spell.iota.PatternIota;
import at.petrak.hexcasting.common.items.ItemStaff;
import dot.funky.intarsia.Intarsia;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CurioSlotCastHexPacket {
    int slot;

    public CurioSlotCastHexPacket(int i) {
        slot = i;
    }

    public static CurioSlotCastHexPacket decode(FriendlyByteBuf buf) {

        return new CurioSlotCastHexPacket(buf.readInt());
    }

    public static void handle(CurioSlotCastHexPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet

            List<SlotResult> casterStack = CuriosApi.getCuriosHelper().findCurios(sender, stack -> stack.getItem() instanceof IotaHolderItem);
            for (SlotResult result : casterStack) {
                Item item = result.stack().getItem();
                if (result.slotContext().index() == msg.slot && (item instanceof IotaHolderItem)) {
                    use(result.stack(), sender);
                    break;
                }
            }


        });


        ctx.get().setPacketHandled(true);
    }

    public static void use(ItemStack stack, ServerPlayer player) {


        if (stack.getItem() instanceof IotaHolderItem && !(player.getCooldowns().isOnCooldown(stack.getItem()))) {
            List<Iota> instrs = new ArrayList<Iota>();
            Iota iota = ((IotaHolderItem) stack.getItem()).readIota(stack, (ServerLevel) player.level);
            if (iota instanceof ListIota) {
                SpellList.SpellListIterator it = ((ListIota) iota).getList().iterator();
                while (it.hasNext()) {
                    Iota i = it.next();
                    instrs.add(i);

                }
            } else if (iota instanceof PatternIota) {
                instrs.add(iota);

            }

            InteractionHand usedHand = player.getMainHandItem().getItem() instanceof ItemStaff ? InteractionHand.MAIN_HAND : player.getOffhandItem().getItem() instanceof ItemStaff ? InteractionHand.OFF_HAND : null;
            CastingContext ctx = new CastingContext(player, usedHand, CastingContext.CastSource.STAFF);
            CastingHarness harness = new CastingHarness(ctx);
            harness.executeIotas(instrs, player.getLevel());
            player.getCooldowns().addCooldown(stack.getItem(), 5);


        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(slot);
    }


}
