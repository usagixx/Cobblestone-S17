package com.jandbdavenport.mixin.client.rendering;

import com.jandbdavenport.cobblestonehelper.features.HudLayout;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.entity.boss.BossBar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Map;
import java.util.UUID;

/** Render every received bar; vanilla stops when its stack reaches one third of the screen. */
@Mixin(BossBarHud.class)
public class AllBossBarsMixin {
    @Shadow @Final private Map<UUID, ClientBossBar> bossBars;
    @Shadow private void renderBossBar(DrawContext context, int x, int y, BossBar bar) { throw new AssertionError(); }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void s17$renderAll(DrawContext context, CallbackInfo ci) {
        ci.cancel();
        var font = MinecraftClient.getInstance().textRenderer;
        int cellWidth = 190;
        for (var bar : bossBars.values()) cellWidth = Math.max(cellWidth, font.getWidth(bar.getName()) + 8);
        var layout = HudLayout.bossLayout(bossBars.size(), cellWidth);
        if (bossBars.isEmpty()) return;
        context.createNewRootLayer();
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(layout.bounds().x(), layout.bounds().y());
        context.getMatrices().scale(layout.scale(), layout.scale());
        int i = 0;
        for (var bar : bossBars.values()) {
            int column = i / layout.rows(), row = i % layout.rows();
            int center = column * layout.cellWidth() + layout.cellWidth() / 2;
            int top = row * 19;
            context.drawTextWithShadow(font, bar.getName(), center - font.getWidth(bar.getName()) / 2, top, 0xFFFFFFFF);
            renderBossBar(context, center - 91, top + 10, bar);
            i++;
        }
        context.getMatrices().popMatrix();
    }
}
