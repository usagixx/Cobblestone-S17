package com.jandbdavenport.cobblestonehelper.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

/** One deliberate key press sends one command; holding a key never repeats it. */
public final class CookieKeys {
    private static final String[] TYPES = {"grandma", "cobbled", "suspicious"};
    private static final int[] DEFAULTS = {GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_5};
    public static void init(KeyBinding.Category category) {
        KeyBinding[] keys = new KeyBinding[TYPES.length];
        boolean[] held = new boolean[TYPES.length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cobblestonehelper.cookie." + TYPES[i], DEFAULTS[i], category));
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (int i = 0; i < keys.length; i++) {
                boolean clicked = false;
                while (keys[i].wasPressed()) clicked = true;
                boolean down = keys[i].isPressed();
                if (clicked && !held[i] && client.currentScreen == null && client.player != null
                        && client.getNetworkHandler() != null) {
                    client.getNetworkHandler().sendChatCommand("cookie " + TYPES[i]);
                }
                held[i] = down;
            }
        });
    }
}
