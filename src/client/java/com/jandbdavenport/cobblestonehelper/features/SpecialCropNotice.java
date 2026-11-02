package com.jandbdavenport.cobblestonehelper.features;

import java.util.regex.Pattern;

/** Only the central crop reward line; ordinary chat, action bars and proc names are unaffected. */
public final class SpecialCropNotice {
    private static final Pattern REWARD = Pattern.compile(
        "(?i)^\\s*[\\d,.]+\\s*[x×]\\s*\\[\\s*\\+\\s*[\\d,.]+\\s*[kmbtq]?(\\s+[^\\]\\r\\n]+)\\]"
        + "(?:\\s*\\(\\s*\\d+\\s*/\\s*\\d+\\s*\\))?(?:\\s+GOLDEN\\s*[x×]\\s*[\\d,.]+)?\\s*$");
    private SpecialCropNotice() {}
    public static int[] cropRange(String text) {
        var match = REWARD.matcher(text);
        return match.matches() ? new int[]{match.start(1), match.end(1)} : null;
    }
}
