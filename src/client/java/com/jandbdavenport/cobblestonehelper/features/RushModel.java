package com.jandbdavenport.cobblestonehelper.features;

/** The server pack's fishing_rings range_dispatch uses 15=Rush wave, 16=Rush arrow, 17=Echo. */
public final class RushModel {
    private RushModel() {}
    public static boolean matches(String model, Float data) {
        if ("cobblestone:rush_wave".equals(model) || "cobblestone:rush_arrow".equals(model)) return true;
        return "cobblestone:fishing_rings".equals(model) && data != null
            && Float.isFinite(data) && data >= 15 && data < 17;
    }
    public static boolean matchesEcho(String model, Float data) {
        if ("cobblestone:echo_arrow".equals(model)) return true;
        // Echo is the final entry in the pack's range_dispatch (no upper threshold).
        return "cobblestone:fishing_rings".equals(model) && data != null
            && Float.isFinite(data) && data >= 17;
    }
}
