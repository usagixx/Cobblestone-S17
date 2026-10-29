package com.jandbdavenport.cobblestonehelper.features;

/** Pure layout calculation, shared by the renderer and geometry checks. */
public record BossBarGrid(float scale, int columns, int rows, float width, float height) {
    public static BossBarGrid fit(int count, int cellWidth, float requestedScale, int screenWidth, int screenHeight) {
        int n = Math.max(1, count);
        float maxWidth = Math.max(1, screenWidth - 8);
        float maxHeight = Math.max(20, screenHeight * 0.40f);
        float best = 0;
        int columns = 1, rows = n;
        for (int c = 1; c <= n; c++) {
            int r = (n + c - 1) / c;
            float fit = Math.min(requestedScale, Math.min(maxWidth / (c * (float)cellWidth), maxHeight / (r * 19f)));
            if (fit > best + 0.001f) { best = fit; columns = c; rows = r; }
        }
        return new BossBarGrid(best, columns, rows, columns * cellWidth * best, rows * 19 * best);
    }
}
