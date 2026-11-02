package com.jandbdavenport.mixin.client.rendering;

import com.jandbdavenport.cobblestonehelper.features.GuildQuestsManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects Guild Quests HUD rendering into the game's HUD render cycle.
 */
@Mixin(InGameHud.class)
public class GuildQuestsHudMixin {
    @Inject(method = "setTitle", at = @At("HEAD"))
    private void s17$rememberProcColors(net.minecraft.text.Text text, CallbackInfo ci) {
        com.jandbdavenport.cobblestonehelper.features.ProcTitleAppearance.observe(text);
    }
	@Inject(method = "render", at = @At("TAIL"))
	private void renderGuildQuestsHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		GuildQuestsManager.renderHud(context);
        com.jandbdavenport.cobblestonehelper.features.SessionStatsHud.render(context);
	}
}
