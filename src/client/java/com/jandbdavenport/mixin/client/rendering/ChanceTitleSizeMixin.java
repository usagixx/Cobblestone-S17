package com.jandbdavenport.mixin.client.rendering;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/** Keep vanilla anchoring; reduce only the mod's chance titles from 4x to 3x. */
@Mixin(InGameHud.class)
public class ChanceTitleSizeMixin {
    @Shadow private Text title;

    @ModifyArgs(method = "renderTitleAndSubtitle", at = @At(value = "INVOKE",
        target = "Lorg/joml/Matrix3x2fStack;scale(FF)Lorg/joml/Matrix3x2f;", ordinal = 0))
    private void s17$chanceSize(Args args) {
        if (title == null) return;
        String name = title.getString();
        if (name.equals("RUSH CHANCE!") || name.equals("ECHO CHANCE!")) {
            args.set(0, 3f);
            args.set(1, 3f);
        }
    }
}
