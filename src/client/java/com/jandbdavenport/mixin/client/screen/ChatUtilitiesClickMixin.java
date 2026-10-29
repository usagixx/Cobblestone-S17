package com.jandbdavenport.mixin.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Keep vanilla command, custom-action, dialog and URL confirmation behavior. */
@Pseudo
@Mixin(targets = "de.chatwheel.chat.window.ChatWindowManager", remap = false)
public class ChatUtilitiesClickMixin {
    @Inject(method = "handleClickEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private void s17$vanillaClick(ClickEvent event, MinecraftClient client, CallbackInfo ci) {
        // Chat Utilities supplies the chat input for suggested commands itself.
        if (event instanceof ClickEvent.SuggestCommand || client.player == null) return;
        VanillaClickBridge.dispatch(event, client);
        ci.cancel();
    }

    private static class VanillaClickBridge extends Screen {
        private VanillaClickBridge() { super(Text.empty()); }
        static void dispatch(ClickEvent event, MinecraftClient client) {
            handleClickEvent(event, client, client.currentScreen);
        }
    }
}
