package com.jandbdavenport.mixin.client.rendering;

import com.jandbdavenport.cobblestonehelper.ModConfig;
import com.jandbdavenport.cobblestonehelper.features.HudLayout;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Comparator;

/** Transform the vanilla renderer, preserving server text, ordering and number formats. */
@Mixin(InGameHud.class)
public class MovableSidebarMixin {
    @Shadow @Final private static Comparator<ScoreboardEntry> SCOREBOARD_ENTRY_COMPARATOR;

    @WrapMethod(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V")
    private void s17$placeSidebar(DrawContext context, ScoreboardObjective objective, Operation<Void> original) {
        var client = MinecraftClient.getInstance();
        var font = client.textRenderer;
        var scoreboard = objective.getScoreboard();
        var format = objective.getNumberFormatOr(StyledNumberFormat.RED);
        var entries = scoreboard.getScoreboardEntries(objective).stream().filter(e -> !e.hidden())
            .sorted(SCOREBOARD_ENTRY_COMPARATOR).limit(15).toList();
        int textWidth = font.getWidth(objective.getDisplayName());
        for (var e : entries) {
            int scoreWidth = font.getWidth(e.formatted(format));
            int nameWidth = font.getWidth(Team.decorateName(scoreboard.getScoreHolderTeam(e.owner()), e.name()));
            textWidth = Math.max(textWidth, nameWidth + (scoreWidth > 0 ? font.getWidth(": ") + scoreWidth : 0));
        }
        int nativeX = context.getScaledWindowWidth() - textWidth - 5;
        int nativeY = context.getScaledWindowHeight() / 2 + entries.size() * 9 / 3 - entries.size() * 9 - 10;
        float scale = Math.min(ModConfig.sidebarScale, Math.min((context.getScaledWindowWidth() - 4f) / (textWidth + 4),
            (context.getScaledWindowHeight() - 4f) / (entries.size() * 9 + 10)));
        float width = (textWidth + 4) * scale, height = (entries.size() * 9 + 10) * scale;
        float x = ModConfig.sidebarX < 0 ? context.getScaledWindowWidth() - width - 1 : ModConfig.sidebarX;
        float y = ModConfig.sidebarY < 0 ? nativeY : ModConfig.sidebarY;
        HudLayout.sidebar = HudLayout.place(x, y, width, height);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(HudLayout.sidebar.x(), HudLayout.sidebar.y());
        context.getMatrices().scale(scale, scale);
        context.getMatrices().translate((float)-nativeX, (float)-nativeY);
        try { original.call(context, objective); }
        finally { context.getMatrices().popMatrix(); }
    }
}
