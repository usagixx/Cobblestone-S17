package com.jandbdavenport.cobblestonehelper.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class FishData {
    public static final FarmData.FarmCategory CATEGORY = load();
    private static FarmData.FarmCategory load() {
        try (var stream = FishData.class.getResourceAsStream("/assets/cobblestonehelper/s17-fish.json")) {
            if (stream == null) throw new IllegalStateException("Missing fish data");
            List<FarmData.FarmEntry> fish = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8),
                new TypeToken<List<FarmData.FarmEntry>>() {}.getType());
            if (fish == null || fish.size() != 20) throw new IllegalStateException("Expected 20 fish");
            for (var entry : fish) if (!entry.commandArg().matches("[A-Za-z ]+"))
                throw new IllegalStateException("Invalid fish name");
            return new FarmData.FarmCategory("Fish", List.copyOf(fish));
        } catch (Exception ex) { throw new IllegalStateException("Cannot load fish list", ex); }
    }
}
