package com.jandbdavenport.cobblestonehelper.features;

import com.jandbdavenport.cobblestonehelper.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

/** Shared screen-space bounds for rendering and the drag editor. */
public final class HudLayout {
    public record Rect(float x, float y, float width, float height) {
        public boolean contains(double px, double py) { return px >= x && py >= y && px < x + width && py < y + height; }
    }
    public record BossLayout(Rect bounds, float scale, int columns, int rows, int cellWidth) {}
    public static Rect sidebar = new Rect(280, 40, 140, 150);
    public static Rect bosses = new Rect(100, 5, 200, 38);
    public static int receivedBossBars = 0;
    public static float effectiveBossScale = 1;
    public static boolean editing = false;

    public static Rect place(float x, float y, float width, float height) {
        var window = MinecraftClient.getInstance().getWindow();
        return new Rect(MathHelper.clamp(x, 0, Math.max(0, window.getScaledWidth() - width)),
            MathHelper.clamp(y, 0, Math.max(0, window.getScaledHeight() - height)), width, height);
    }

    public static Rect guild() {
        var w = GuildQuestsManager.WIDGET;
        return place(w.getHudX(), w.getHudY(), w.getWidgetWidth(), w.getWidgetHeight());
    }

    public static BossLayout bossLayout(int count, int cellWidth) {
        var window = MinecraftClient.getInstance().getWindow();
        var grid = BossBarGrid.fit(count, cellWidth, ModConfig.bossBarScale, window.getScaledWidth(), window.getScaledHeight());
        float best = grid.scale(), width = grid.width(), height = grid.height();
        float x = ModConfig.bossBarX < 0 ? (window.getScaledWidth() - width) / 2 : ModConfig.bossBarX;
        float y = ModConfig.bossBarY < 0 ? 5 : ModConfig.bossBarY;
        bosses = place(x, y, width, height);
        receivedBossBars = count;
        effectiveBossScale = best;
        return new BossLayout(bosses, best, grid.columns(), grid.rows(), cellWidth);
    }
}
