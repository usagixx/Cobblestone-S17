package com.jandbdavenport.cobblestonehelper.features;

/** Notify once per sequence, tolerating missing displays between chained effects. */
public final class ProcSequenceGate {
    private static final int QUIET_TICKS = 200;
    private boolean active;
    private int missing;

    public boolean update(boolean detected) {
        if (detected) {
            boolean first = !active;
            active = true;
            missing = 0;
            return first;
        }
        if (missing < QUIET_TICKS) missing++;
        if (missing >= QUIET_TICKS) active = false;
        return false;
    }

    public void reset() { active = false; missing = 0; }
}
