package com.jandbdavenport.cobblestonehelper.features;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/** Normalizes server formatting before matching quest labels and mob names. */
public final class QuestText {
    private static final String SMALL = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡхʏᴢ";
    private static final String ASCII = "abcdefghijklmnopqrstuvwxyz";
    private static final Pattern ACTION = Pattern.compile("(?i)\\b(?:kill|slay|defeat|farm|harvest|collect)\\s+[\\d,]+\\s+(.+)");

    public static String plain(String text) {
        String value = Normalizer.normalize(text == null ? "" : text, Normalizer.Form.NFKC)
            .replaceAll("§.", "").replace('\u00a0', ' ');
        StringBuilder result = new StringBuilder();
        for (char c : value.toCharArray()) {
            int index = SMALL.indexOf(c);
            result.append(index < 0 ? c : ASCII.charAt(index));
        }
        return result.toString().trim().replaceAll("\\s+", " ");
    }

    public static String key(String text) {
        return plain(text).toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9 ]", "").trim().replaceAll("\\s+", " ");
    }

    public static String target(String line) {
        var match = ACTION.matcher(plain(line));
        return match.find() ? match.group(1).trim() : "";
    }

    private QuestText() {}
}
