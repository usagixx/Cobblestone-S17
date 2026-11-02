package com.jandbdavenport.cobblestonehelper.features;

import java.text.Normalizer;
import java.util.regex.Pattern;

/** Only final chain summaries. Central proc titles may occur on every chain step. */
public final class ProcCompletion {
    private static final Pattern COLORS = Pattern.compile("(?i)§[0-9a-fk-orx]");
    private static final Pattern CHAIN = Pattern.compile(
        "(?i)^\\s*[^\\p{L}\\p{N}]*\\s*(?:(echo)\\s+chain\\s*\\(\\d+\\s+steps?\\)"
        + "|(rush)\\s+chain\\s*\\([\\d.]+s\\))\\s*[•·]\\s*[\\d,.]+[x×]\\s*\\[\\+[^\\]\\r\\n]+\\]\\s*$");
    private ProcCompletion() {}
    private static String clean(String text) {
        return COLORS.matcher(Normalizer.normalize(text, Normalizer.Form.NFKC)).replaceAll("").strip();
    }
    public static int chatKind(String text) {
        var matcher = CHAIN.matcher(clean(text));
        return matcher.matches() ? (matcher.group(1) != null ? 1 : 0) : -1;
    }
}
