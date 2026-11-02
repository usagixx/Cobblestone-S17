package com.jandbdavenport.cobblestonehelper.features;

import net.minecraft.text.Text;
import net.minecraft.text.Style;
import java.util.ArrayList;
import java.util.Optional;
import java.text.Normalizer;

/** Match the server proc palette, with screenshot-based defaults before a result is received. */
public final class ProcTitleAppearance {
    private static final int[][] PALETTES = {
        {0xE8C74A, 0xFFF0CF, 0xB88A24},
        {0x9873DD, 0xC56BD5, 0xF675BE}
    };
    private static final Style[][] STYLES = {
        {Style.EMPTY.withBold(true)}, {Style.EMPTY.withBold(true)}
    };
    private ProcTitleAppearance() {}

    public static void observe(Text text) {
        String name = Normalizer.normalize(text.getString(), Normalizer.Form.NFKC)
            .replaceAll("(?i)§[0-9a-fk-orx]", "").replaceAll("[^A-Za-z]", "");
        int kind = name.equalsIgnoreCase("rush") ? 0 : name.equalsIgnoreCase("echo") ? 1 : -1;
        if (kind < 0) return;
        var colors = new ArrayList<Integer>();
        var styles = new ArrayList<Style>();
        text.visit((style, part) -> {
            for (int i = 0; i < part.length(); i++) {
                char c = part.charAt(i);
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                    styles.add(style);
                    if (style.getColor() != null) colors.add(style.getColor().getRgb());
                }
            }
            return Optional.empty();
        }, Style.EMPTY);
        if (styles.size() == 4) STYLES[kind] = styles.toArray(Style[]::new);
        if (colors.size() == 4 && colors.stream().distinct().count() > 1) {
            PALETTES[kind] = colors.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    public static Text chance(int kind) {
        String label = kind == 0 ? "RUSH CHANCE!" : "ECHO CHANCE!";
        int[] palette = PALETTES[kind];
        Style[] styles = STYLES[kind];
        var result = Text.empty();
        for (int i = 0; i < label.length(); i++) {
            float at = i * (palette.length - 1f) / (label.length() - 1);
            int left = Math.min((int)at, palette.length - 2);
            float mix = at - left;
            int color = 0;
            for (int shift : new int[]{16, 8, 0}) {
                int a = (palette[left] >> shift) & 255, b = (palette[left + 1] >> shift) & 255;
                color |= Math.round(a + (b - a) * mix) << shift;
            }
            int styleIndex = Math.round(i * (styles.length - 1f) / (label.length() - 1));
            result.append(Text.literal(label.substring(i, i + 1)).setStyle(styles[styleIndex].withColor(color)));
        }
        return result;
    }
}
