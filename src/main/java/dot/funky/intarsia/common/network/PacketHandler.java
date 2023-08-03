package dot.funky.intarsia.common.network;

import dot.funky.intarsia.Intarsia;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static void init()
    {
        int id = 0;
        INSTANCE.registerMessage(id++, CurioSlotCastHexPacket.class, CurioSlotCastHexPacket::encode, CurioSlotCastHexPacket::decode,CurioSlotCastHexPacket::handle);
    }

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Intarsia.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );





}
