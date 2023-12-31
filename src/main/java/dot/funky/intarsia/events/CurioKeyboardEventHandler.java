package dot.funky.intarsia.events;

import dot.funky.intarsia.Intarsia;
import dot.funky.intarsia.common.network.CurioSlotCastHexPacket;
import dot.funky.intarsia.common.network.PacketHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.lwjgl.glfw.GLFW;

public class CurioKeyboardEventHandler {

    public static final String CATEGORY = "key.category.intarsia";
    public static final KeyMapping SLOT_1 = new KeyMapping("key.intarsia.slot.1", GLFW.GLFW_KEY_X, CATEGORY);
    public static final KeyMapping SLOT_2 = new KeyMapping("key.intarsia.slot.2", GLFW.GLFW_KEY_C, CATEGORY);
    public static final KeyMapping SLOT_3 = new KeyMapping("key.intarsia.slot.3", GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyMapping SLOT_4 = new KeyMapping("key.intarsia.slot.4", GLFW.GLFW_KEY_B, CATEGORY);
    public static final KeyMapping SLOT_5 = new KeyMapping("key.intarsia.slot.5", GLFW.GLFW_KEY_N, CATEGORY);

    static boolean[] slotKeyDown = new boolean[5];
    static boolean[] slotKeyAllowed = {
            true,true,true,true,true
    };
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent()
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        Intarsia.LOGGER.info("LOGGED SOME BINDIES");
        if (ModList.get().isLoaded("curios")) {

            event.register(SLOT_1);
            event.register(SLOT_2);
            event.register(SLOT_3);
            event.register(SLOT_4);
            event.register(SLOT_5);

        }
    }
    public static void clientTick() {

        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {


            slotKeyDown[0] = SLOT_1.isDown();
            slotKeyDown[1] = SLOT_2.isDown();
            slotKeyDown[2] = SLOT_3.isDown();
            slotKeyDown[3] = SLOT_4.isDown();
            slotKeyDown[4] = SLOT_5.isDown();


            final boolean isGameFocused = !Minecraft.getInstance().isPaused();


            for (int i = 0; i < 5; i++) {
                if (slotKeyDown[i] && isGameFocused && slotKeyAllowed[i]) {
                    PacketHandler.INSTANCE.sendToServer(new CurioSlotCastHexPacket(i));
                } else if (!slotKeyDown[i] && !slotKeyAllowed[i] ) {
                    slotKeyAllowed[i] = true;
                }
            }


        }
    }

}
