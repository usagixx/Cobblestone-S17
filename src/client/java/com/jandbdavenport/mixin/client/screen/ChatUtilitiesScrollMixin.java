package com.jandbdavenport.mixin.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Offset is counted from newest: keep the visible lines anchored on arrival. */
@Pseudo
@Mixin(targets = "de.chatwheel.chat.window.ChatWindow", remap = false)
public abstract class ChatUtilitiesScrollMixin {
    @Shadow private int scrollOffset;
    @Shadow private boolean newestOnTop;
    @Unique private int s17$linesBefore;
    @Unique private boolean s17$anchored;

    @Inject(method = "scrollToBottom", at = @At("HEAD"), cancellable = true, remap = false)
    private void s17$keepPositionOnOpen(CallbackInfo ci) {
        // The companion's InGameHud mixin invokes this whenever chat reopens.
        if (scrollOffset > 0) ci.cancel();
    }

    @Unique private int s17$wrappedLines() {
        try {
            var overlay = Class.forName("de.chatwheel.gui.window.ChatWindowHudOverlay");
            var window = Class.forName("de.chatwheel.chat.window.ChatWindow");
            return (Integer) overlay.getMethod("getTotalWrappedLines", window, MinecraftClient.class)
                .invoke(null, this, MinecraftClient.getInstance());
        } catch (ReflectiveOperationException ex) {
            return -1;
        }
    }

    @Inject(method = "addMessage", at = @At("HEAD"), remap = false)
    private void s17$rememberAnchor(Text message, CallbackInfo ci) {
        s17$anchored = scrollOffset > 0;
        s17$linesBefore = s17$anchored ? s17$wrappedLines() : -1;
    }

    @Inject(method = "addMessage", at = @At("RETURN"), remap = false)
    private void s17$keepAnchor(Text message, CallbackInfo ci) {
        if (!s17$anchored || s17$linesBefore < 0) return;
        int after = s17$wrappedLines();
        if (after >= 0) scrollOffset = Math.max(0, scrollOffset + after - s17$linesBefore);
    }
}
