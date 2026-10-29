package com.jandbdavenport.cobblestonehelper.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class FarmData {
    public record FarmEntry(String displayName, String itemId, String commandArg) {}
    public record FarmCategory(String name, List<FarmEntry> crops) {}
    private static final Gson GSON = new Gson();
    private static final java.lang.reflect.Type TYPE = new TypeToken<List<FarmCategory>>() {}.getType();
    public static final List<FarmCategory> LIST = load();

    private static List<FarmCategory> load() {
        List<FarmCategory> defaults;
        try (var stream = FarmData.class.getResourceAsStream("/assets/cobblestonehelper/s17-farms.json")) {
            if (stream == null) throw new IllegalStateException("Missing S17 farm data");
            defaults = GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), TYPE);
            validate(defaults);
        } catch (Exception e) { throw new IllegalStateException("Cannot load bundled S17 farms", e); }
        // Optional local override allows correcting server aliases without rebuilding.
        Path override = Path.of("config", "cobblestonehelper", "s17-farms.json");
        if (Files.exists(override)) {
            try (var reader = Files.newBufferedReader(override, StandardCharsets.UTF_8)) {
                List<FarmCategory> custom = GSON.fromJson(reader, TYPE);
                validate(custom);
                return List.copyOf(custom);
            } catch (Exception e) { System.err.println("[S17] Invalid farm override; using defaults: " + e.getMessage()); }
        }
        return List.copyOf(defaults);
    }

    private static void validate(List<FarmCategory> data) {
        if (data == null || data.isEmpty()) throw new IllegalArgumentException("No farm categories");
        for (var category : data) {
            if (category == null || category.name() == null || category.crops() == null || category.crops().isEmpty())
                throw new IllegalArgumentException("Invalid category");
            for (var crop : category.crops()) {
                if (crop == null || crop.displayName() == null || crop.displayName().isBlank()
                    || crop.itemId() == null || !crop.itemId().matches("[a-z0-9_.-]+:[a-z0-9_/.-]+")
                    || crop.commandArg() == null || !crop.commandArg().matches("[a-zA-Z0-9_-]+"))
                    throw new IllegalArgumentException("Invalid farm entry");
            }
        }
    }
}
