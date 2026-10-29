package com.jandbdavenport.cobblestonehelper.features;

import java.util.OptionalLong;
import java.util.regex.Pattern;

/** Pure counter logic; an hour starts with the first usable server sample. */
public final class FarmedTracker {
    public static final long HOUR = 3_600_000;
    private static final Pattern NUMBER = Pattern.compile("([0-9]+(?:[,.][0-9]+)*)\\s*([KMBT]?)", Pattern.CASE_INSENSITIVE);
    public record Result(long start, long end, long amount) {}
    private long start = -1, baseline, previous, lastSample = -1;
    public void reset() { start = lastSample = -1; }
    public long startedAt() { return start; }
    public Result sample(long value, long now) {
        if (start < 0 || now < lastSample || now - lastSample > 300_000 || value < previous) {
            start = now; baseline = value;
        }
        previous = value; lastSample = now;
        if (now - start < HOUR) return null;
        Result result = new Result(start, now, value - baseline);
        start = now; baseline = value;
        return result;
    }
    public static OptionalLong parse(String line) {
        String text = line.replaceAll("§.", "").replace("ꜰᴀʀᴍᴇᴅ", "FARMED").replace("ғᴀʀᴍᴇᴅ", "FARMED").toUpperCase(java.util.Locale.ROOT);
        text = text.replace("ꜰᴀʀᴍᴇᴅ", "FARMED").replace("ғᴀʀᴍᴇᴅ", "FARMED");
        int label = text.indexOf("FARMED");
        if (label < 0) return OptionalLong.empty();
        var m = NUMBER.matcher(text.substring(label + 6));
        if (!m.find()) return OptionalLong.empty();
        try {
            double value = Double.parseDouble(m.group(1).replace(",", ""));
            double scale = switch (m.group(2).toUpperCase(java.util.Locale.ROOT)) {
                case "K" -> 1_000; case "M" -> 1_000_000; case "B" -> 1_000_000_000;
                case "T" -> 1_000_000_000_000d; default -> 1;
            };
            double result = value * scale;
            return Double.isFinite(result) && result >= 0 && result < Long.MAX_VALUE
                ? OptionalLong.of(Math.round(result)) : OptionalLong.empty();
        } catch (NumberFormatException ex) { return OptionalLong.empty(); }
    }
}

