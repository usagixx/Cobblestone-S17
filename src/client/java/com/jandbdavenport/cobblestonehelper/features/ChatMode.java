package com.jandbdavenport.cobblestonehelper.features;

/** Optional companion integration; preserve its layout/filter settings when toggled. */
public final class ChatMode {
    private static Object config() throws ReflectiveOperationException {
        return Class.forName("de.chatwheel.config.ChatUtilitiesConfig").getMethod("getInstance").invoke(null);
    }
    public static boolean available() {
        return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("chatwheel");
    }
    public static boolean enabled() {
        try { var c = config(); return c.getClass().getField("chatWindowEnabled").getBoolean(c); }
        catch (ReflectiveOperationException ex) { return false; }
    }
    public static boolean set(boolean enabled) {
        if (!available()) return false;
        try {
            var c = config();
            c.getClass().getField("chatWindowEnabled").setBoolean(c, enabled);
            c.getClass().getMethod("save").invoke(c);
            return true;
        } catch (ReflectiveOperationException ex) {
            System.err.println("[S17] Could not change chat mode: " + ex.getMessage());
            return false;
        }
    }
}
