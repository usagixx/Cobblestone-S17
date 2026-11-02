package com.jandbdavenport.mixin.client.rendering;

import com.jandbdavenport.cobblestonehelper.features.SpecialCropNotice;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import java.util.Optional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class HideSpecialCropNoticeMixin {
    @WrapOperation(method = "renderTitleAndSubtitle", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithBackground(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIII)V"))
    private void s17$hideCropReward(DrawContext context, TextRenderer renderer, Text text,
                                   int x, int y, int width, int color, Operation<Void> original) {
        int[] range = SpecialCropNotice.cropRange(text.getString());
        if (range == null) { original.call(context, renderer, text, x, y, width, color); return; }
        Text shortened = s17$withoutCrop(text, range[0], range[1]);
        int newWidth = renderer.getWidth(shortened);
        original.call(context, renderer, shortened, -newWidth / 2, y, newWidth, color);
    }

    @Unique private static Text s17$withoutCrop(Text text, int start, int end) {
        var result = Text.empty();
        int[] offset = {0};
        text.visit((style, part) -> {
            int from = offset[0], to = from + part.length();
            if (from < start) {
                String before = part.substring(0, Math.min(part.length(), start - from));
                if (!before.isEmpty()) result.append(Text.literal(before).setStyle(style));
            }
            if (to > end) {
                String after = part.substring(Math.max(0, end - from));
                if (!after.isEmpty()) result.append(Text.literal(after).setStyle(style));
            }
            offset[0] = to;
            return Optional.empty();
        }, Style.EMPTY);
        return result;
    }
}
