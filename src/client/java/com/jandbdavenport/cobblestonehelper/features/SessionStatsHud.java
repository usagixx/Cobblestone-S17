package com.jandbdavenport.cobblestonehelper.features;

import com.jandbdavenport.cobblestonehelper.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class SessionStatsHud {
    private static final ZoneId JST = ZoneId.of("Asia/Tokyo");
    private static final DateTimeFormatter CLOCK = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(JST);
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm").withZone(JST);
    private static final FarmedTracker TRACKER = new FarmedTracker();
    private static FarmedTracker.Result last;
    private static long nextSample;
    private static long lastReadHour = -1;
    public static HudLayout.Rect bounds = new HudLayout.Rect(5, 5, 210, 27);
    public static void init() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            TRACKER.reset(); last = null; nextSample = 0; lastReadHour = -1;
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            long now = System.currentTimeMillis();
            if (now < nextSample) return;
            nextSample = now + 1000;
            // Only read the scoreboard during :00, once per clock hour.
            long hour = Math.floorDiv(now, FarmedTracker.HOUR);
            if (Instant.ofEpochMilli(now).atZone(JST).getMinute() != 0 || hour == lastReadHour) return;
            for (var objective : client.world.getScoreboard().getObjectives()) { if (sample(objective, now, client)) break; }
        });
    }
    private static boolean sample(ScoreboardObjective objective, long now, MinecraftClient client) {
        var scoreboard = objective.getScoreboard();
        for (var entry : scoreboard.getScoreboardEntries(objective)) {
            if (entry.hidden()) continue;
            String line = Team.decorateName(scoreboard.getScoreHolderTeam(entry.owner()), entry.name()).getString();
            var value = FarmedTracker.parse(line);
            if (value.isEmpty()) continue;
            var result = TRACKER.sample(value.getAsLong(), now);
            lastReadHour = Math.floorDiv(now, FarmedTracker.HOUR);
            if (result != null) {
                last = result;
                String report = "[S17] 60min " + amount(result.amount());
                client.inGameHud.getChatHud().addMessage(Text.literal(report));
                try {
                    var path = FabricLoader.getInstance().getConfigDir().resolve("s17-farmed-history.tsv");
                    Files.writeString(path, Instant.ofEpochMilli(result.start()) + "\t" + Instant.ofEpochMilli(result.end())
                        + "\t" + result.amount() + "\testimated\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (java.io.IOException ex) {
                    System.err.println("[S17] Could not save hourly FARMED history: " + ex.getMessage());
                }
            }
            return true;
        }
        return false;
    }
    private static String range(FarmedTracker.Result result) {
        return TIME.format(Instant.ofEpochMilli(result.start())) + "-" + TIME.format(Instant.ofEpochMilli(result.end()));
    }
    private static String amount(long value) { return Long.toString(value); }
    public static void render(DrawContext ctx) {
        var client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;
        String clock = "JST " + CLOCK.format(Instant.now());
        String farmed = last == null ? "60min --" : "60min " + amount(last.amount());
        float scale = ModConfig.statsScale;
        float width = (Math.max(client.textRenderer.getWidth(clock), client.textRenderer.getWidth(farmed)) + 8) * scale;
        float height = 25 * scale;
        bounds = HudLayout.place(ModConfig.statsX < 0 ? 5 : ModConfig.statsX,
            ModConfig.statsY < 0 ? ctx.getScaledWindowHeight() - height - 4 : ModConfig.statsY, width, height);
        ctx.getMatrices().pushMatrix();
        ctx.getMatrices().translate(bounds.x(), bounds.y());
        ctx.getMatrices().scale(scale, scale);
        ctx.fill(0, 0, (int)(width / scale), 25, 0x88000000);
        ctx.drawTextWithShadow(client.textRenderer, clock, 4, 3, 0xFFFFDD55);
        ctx.drawTextWithShadow(client.textRenderer, farmed, 4, 14, 0xFFFFFFFF);
        ctx.getMatrices().popMatrix();
    }
}

