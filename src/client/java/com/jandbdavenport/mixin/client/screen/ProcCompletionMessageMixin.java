package com.jandbdavenport.mixin.client.screen;

import com.jandbdavenport.cobblestonehelper.features.RushNotifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Observe on the game thread before a chat replacement can cancel vanilla delivery. */
@Mixin(value = ClientPlayNetworkHandler.class, priority = 2000)
public class ProcCompletionMessageMixin {
    @Inject(method = "onGameMessage", at = @At("HEAD"))
    private void s17$completed(GameMessageS2CPacket packet, CallbackInfo ci) {
        if (MinecraftClient.getInstance().isOnThread() && !packet.overlay()) RushNotifier.serverMessage(packet.content());
    }
}
