package com.jandbdavenport.cobblestonehelper.features;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/** S17 mappings transcribed in farm order from the supplied five region lists. */
public final class MobFarmMapping {
    private static final Map<String, String> FARMS = load();
    private static Map<String, String> load() {
        try (var stream = MobFarmMapping.class.getResourceAsStream("/assets/cobblestonehelper/s17-mob-farms.json")) {
            if (stream == null) throw new IllegalStateException("Missing S17 mob mappings");
            Map<String, String> values = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8),
                new TypeToken<Map<String, String>>() {}.getType());
            return Map.copyOf(values);
        } catch (Exception ex) { throw new IllegalStateException("Cannot read S17 mob mappings", ex); }
    }
    public static String farmFor(String mob) {
        if (mob == null) return "";
        return FARMS.getOrDefault(mob.replaceAll("§.", "").trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT), "");
    }
}
