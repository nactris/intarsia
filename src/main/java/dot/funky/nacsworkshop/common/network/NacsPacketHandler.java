package dot.funky.nacsworkshop.common.network;

import dot.funky.nacsworkshop.NacsWorkshop;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NacsPacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static void init()
    {
        int id = 0;
        INSTANCE.registerMessage(id++, CurioSlotCastHexPacket.class, CurioSlotCastHexPacket::encode, CurioSlotCastHexPacket::decode,CurioSlotCastHexPacket::handle);
    }

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NacsWorkshop.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );





}
