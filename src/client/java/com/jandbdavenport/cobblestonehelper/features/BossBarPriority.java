package com.jandbdavenport.cobblestonehelper.features;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/** Presentation-only ordering; equal priorities retain the server's insertion order. */
public final class BossBarPriority {
    private static final Pattern FORMATTING = Pattern.compile("§[0-9A-FK-ORX]", Pattern.CASE_INSENSITIVE);
    private static final Pattern WEATHER = Pattern.compile(
        "\\b(weather|rain|rainy|storm|thunderstorm|thunder|sunny|sunshine|snow|snowy|blizzard|fog|foggy|wind|windy|eclipse|heatwave)\\b|shimmering night|blood moon|clear skies");

    private BossBarPriority() {}

    public static int rank(String title) {
        String name = FORMATTING.matcher(Normalizer.normalize(title, Normalizer.Form.NFKC))
            .replaceAll("").toLowerCase(Locale.ROOT);
        if (name.contains("celestial") || name.contains("セレスティアル")) return 0;
        if (WEATHER.matcher(name).find() || name.contains("ウェザー") || name.contains("天候")) return 1;
        if (name.contains("global") || name.contains("グローバル")) return 2;
        if (name.contains("cookie") || name.contains("クッキー")) return 3;
        return 4;
    }
}
