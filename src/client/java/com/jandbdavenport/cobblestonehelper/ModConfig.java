package com.jandbdavenport.cobblestonehelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Centralized configuration system for all mod features.
 * Handles reading/writing to config/cobblestonehelper.json with proper merging.
 */
public class ModConfig {
	// Feature toggles
	public static boolean guildQuestsEnabled = true;


	// Farm Warps display toggles
	public static boolean farmWarpsGuildQuestHighlight = true;

	// Auto Respawn
	public static boolean autoRespawnEnabled = false;
	public static float autoRespawnDelaySeconds = 3.0f;

	// Item Glow
	public static boolean itemGlowEnabled = true;
	public static int itemGlowColor = 0xFFFFFFFF;



	// Guild Quests colors + scale
	public static int gqColorHeader  = 0xFFFFAA00;
	public static int gqColorLabel   = 0xFFFFFF55;
	public static int gqColorTarget  = 0xFFFFFFFF;
	public static int gqColorUnknown = 0xFFAAAAAA;
	public static float gqScale = 1.0f;
    // Negative coordinates retain the vanilla/default anchor until moved.
    public static float sidebarX = -1, sidebarY = -1, sidebarScale = 1;
    public static float bossBarX = -1, bossBarY = -1, bossBarScale = 1;


	// Config Menu UI colors (used for config screen styling)
	public static int fwColorBackground = 0xF2111111;
	public static int fwColorAccent     = 0xFFFF8C00;
	public static int fwColorSlot       = 0xFF222222;

	// Farm Warps Menu colors (separate from config menu)
	public static int fwMenuColorBackground = 0xF2111111;
	public static int fwMenuColorAccent     = 0xFFFF8C00;
	public static int fwMenuColorSlot       = 0xFF222222;

	private static final String CONFIG_DIR = "config";
	private static final String CONFIG_FILE = "cobblestonehelper.json";
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void init() {
		System.out.println("[ModConfig] Initializing ModConfig...");
		loadConfig();
		System.out.println("[ModConfig] ✓ ModConfig initialized");
	}

	public static void loadConfig() {
		try {
			Path configPath = Paths.get(CONFIG_DIR, CONFIG_FILE);
			if (!Files.exists(configPath)) {
				System.out.println("[ModConfig] Config file not found, using defaults");
				return;
			}

			try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
				JsonObject json = GSON.fromJson(reader, JsonObject.class);
				if (json == null) {
					System.out.println("[ModConfig] Config file is empty, using defaults");
					return;
				}

				// Load feature toggles
				guildQuestsEnabled = getBoolean(json, "guildQuestsEnabled", true);


				// Load Farm Warps toggles
				farmWarpsGuildQuestHighlight = getBoolean(json, "farmWarpsGuildQuestHighlight", true);

				// Load Auto Respawn
				autoRespawnEnabled = getBoolean(json, "autoRespawnEnabled", false);
				autoRespawnDelaySeconds = getFloat(json, "autoRespawnDelaySeconds", 3.0f);

				// Load Item Glow
				itemGlowEnabled = getBoolean(json, "itemGlowEnabled", true);
				itemGlowColor = getArgbColor(json, "itemGlowColor", 0xFFFFFFFF);



				// Load Guild Quests colors + scale
				gqColorHeader  = getArgbColor(json, "gqColorHeader", 0xFFFFAA00);
				gqColorLabel   = getArgbColor(json, "gqColorLabel", 0xFFFFFF55);
				gqColorTarget  = getArgbColor(json, "gqColorTarget", 0xFFFFFFFF);
				gqColorUnknown = getArgbColor(json, "gqColorUnknown", 0xFFAAAAAA);
				gqScale = getFloat(json, "gqScale", 1.0f);
                sidebarX = getFloat(json, "sidebarX", -1);
                sidebarY = getFloat(json, "sidebarY", -1);
                sidebarScale = Math.max(0.5f, Math.min(2, getFloat(json, "sidebarScale", 1)));
                bossBarX = getFloat(json, "bossBarX", -1);
                bossBarY = getFloat(json, "bossBarY", -1);
                bossBarScale = Math.max(0.35f, Math.min(2, getFloat(json, "bossBarScale", 1)));

                // Upgrade the original green defaults while preserving custom colors.
                if (!json.has("gqStyleVersion")) {
                    if (gqColorHeader == 0xFF55FF55) gqColorHeader = 0xFFFFAA00;
                    if (gqColorLabel == 0xFF55FF55) gqColorLabel = 0xFFFFFF55;
                    if (gqColorUnknown == 0xFFFFFF00) gqColorUnknown = 0xFFAAAAAA;
                }


				// Load Farm Warps colors (config menu)
				fwColorBackground = getArgbColor(json, "fwColorBackground", 0xF2111111);
				fwColorAccent     = getArgbColor(json, "fwColorAccent", 0xFFFF8C00);
				fwColorSlot       = getArgbColor(json, "fwColorSlot", 0xFF222222);

				// Load Farm Warps Menu colors (farm warp screen)
				fwMenuColorBackground = getArgbColor(json, "fwMenuColorBackground", 0xF2111111);
				fwMenuColorAccent     = getArgbColor(json, "fwMenuColorAccent", 0xFFFF8C00);
				fwMenuColorSlot       = getArgbColor(json, "fwMenuColorSlot", 0xFF222222);

				System.out.println("[ModConfig] ✓ Config loaded successfully");
			}
		} catch (Exception e) {
			System.out.println("[ModConfig] ✗ Error loading config: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void saveConfig() {
		try {
			Path configDir = Paths.get(CONFIG_DIR);
			Files.createDirectories(configDir);

			Path configPath = configDir.resolve(CONFIG_FILE);

			// Read existing JSON or create empty object
			JsonObject json = new JsonObject();
			if (Files.exists(configPath)) {
				try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
					JsonObject existing = GSON.fromJson(reader, JsonObject.class);
					if (existing != null) {
						json = existing;
					}
				}
			}

			// Update only ModConfig's keys (preserve other managers' keys)
			json.addProperty("guildQuestsEnabled", guildQuestsEnabled);


			json.addProperty("farmWarpsGuildQuestHighlight", farmWarpsGuildQuestHighlight);

			// Auto Respawn
			json.addProperty("autoRespawnEnabled", autoRespawnEnabled);
			json.addProperty("autoRespawnDelaySeconds", autoRespawnDelaySeconds);

			// Item Glow
			json.addProperty("itemGlowEnabled", itemGlowEnabled);
			json.addProperty("itemGlowColor", String.format("%08X", itemGlowColor));



			// Guild Quests
			json.addProperty("gqColorHeader", String.format("%08X", gqColorHeader));
			json.addProperty("gqColorLabel", String.format("%08X", gqColorLabel));
			json.addProperty("gqColorTarget", String.format("%08X", gqColorTarget));
			json.addProperty("gqColorUnknown", String.format("%08X", gqColorUnknown));
			json.addProperty("gqScale", gqScale);
            json.addProperty("sidebarX", sidebarX);
            json.addProperty("sidebarY", sidebarY);
            json.addProperty("sidebarScale", sidebarScale);
            json.addProperty("bossBarX", bossBarX);
            json.addProperty("bossBarY", bossBarY);
            json.addProperty("bossBarScale", bossBarScale);

            json.addProperty("gqStyleVersion", 1);

			// Farm Warps (config menu colors)
			json.addProperty("fwColorBackground", String.format("%08X", fwColorBackground));
			json.addProperty("fwColorAccent", String.format("%08X", fwColorAccent));
			json.addProperty("fwColorSlot", String.format("%08X", fwColorSlot));

			// Farm Warps Menu colors
			json.addProperty("fwMenuColorBackground", String.format("%08X", fwMenuColorBackground));
			json.addProperty("fwMenuColorAccent", String.format("%08X", fwMenuColorAccent));
			json.addProperty("fwMenuColorSlot", String.format("%08X", fwMenuColorSlot));

			// Write back to file
			Files.writeString(configPath, GSON.toJson(json), StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("[ModConfig] ✗ Error saving config: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static boolean getBoolean(JsonObject json, String key, boolean defaultValue) {
		return json.has(key) ? json.get(key).getAsBoolean() : defaultValue;
	}

	private static float getFloat(JsonObject json, String key, float defaultValue) {
		float value = json.has(key) ? json.get(key).getAsFloat() : defaultValue;
        return Float.isFinite(value) ? value : defaultValue;
	}

	private static int getArgbColor(JsonObject json, String key, int defaultValue) {
		if (!json.has(key)) {
			return defaultValue;
		}
		try {
			String hexString = json.get(key).getAsString();
			return (int) Long.parseLong(hexString, 16);
		} catch (Exception e) {
			System.out.println("[ModConfig] ✗ Error parsing color " + key + ": " + e.getMessage());
			return defaultValue;
		}
	}
}
