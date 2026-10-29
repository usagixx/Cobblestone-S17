package com.jandbdavenport.cobblestonehelper.gui;

import com.jandbdavenport.cobblestonehelper.ModConfig;
import com.jandbdavenport.cobblestonehelper.features.GuildQuestsManager;
import com.jandbdavenport.cobblestonehelper.features.HudLayout;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class HudEditorScreen extends Screen {
    private int selected = 0;
    private boolean dragging;
    private double offsetX, offsetY;
    private static final String[] NAMES = {"Guild HUD", "Scoreboard", "Boss Bars", "Clock / FARMED"};
    public HudEditorScreen() { super(Text.literal("S17 HUD Editor")); }

    @Override protected void init() {
        clearChildren();
        HudLayout.editing = true;
        GuildQuestsManager.WIDGET.setHudRenderingDisabled(true);
        int tabWidth = Math.min(122, (width - 16) / NAMES.length);
        int left = (width - tabWidth * NAMES.length) / 2;
        for (int i = 0; i < NAMES.length; i++) {
            final int index = i;
            addDrawableChild(ButtonWidget.builder(Text.literal((selected == i ? "● " : "") + NAMES[i]), b -> {selected = index; init();})
                .dimensions(left + i * tabWidth, height - 48, tabWidth - 2, 20).build());
        }
        int cx = width / 2;
        addDrawableChild(ButtonWidget.builder(Text.literal("−"), b -> changeScale(-0.05f)).dimensions(cx - 150, height - 24, 35, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal(String.format("%.0f%%", scale() * 100)), b -> {}).dimensions(cx - 113, height - 24, 58, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> changeScale(0.05f)).dimensions(cx - 53, height - 24, 35, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset"), b -> reset()).dimensions(cx - 12, height - 24, 70, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close()).dimensions(cx + 64, height - 24, 86, 20).build());
    }
    private float scale() { return selected == 0 ? ModConfig.gqScale : selected == 1 ? ModConfig.sidebarScale : selected == 2 ? ModConfig.bossBarScale : ModConfig.statsScale; }
    private void changeScale(float delta) {
        float value = MathHelper.clamp(Math.round((scale() + delta) * 20) / 20f, selected == 2 ? 0.35f : 0.5f, selected == 0 ? 1.5f : 2f);
        if (selected == 0) ModConfig.gqScale = value;
        else if (selected == 1) ModConfig.sidebarScale = value;
        else if (selected == 2) ModConfig.bossBarScale = value;
        else ModConfig.statsScale = value;
        init();
    }
    private HudLayout.Rect bounds(int kind) { return kind == 0 ? HudLayout.guild() : kind == 1 ? HudLayout.sidebar : kind == 2 ? HudLayout.bosses : com.jandbdavenport.cobblestonehelper.features.SessionStatsHud.bounds; }
    private void position(float x, float y) {
        var r = bounds(selected);
        var placed = HudLayout.place(x, y, r.width(), r.height());
        if (selected == 0) GuildQuestsManager.WIDGET.setHudPosition((int)placed.x(), (int)placed.y());
        else if (selected == 1) { ModConfig.sidebarX = placed.x(); ModConfig.sidebarY = placed.y(); }
        else if (selected == 2) { ModConfig.bossBarX = placed.x(); ModConfig.bossBarY = placed.y(); }
        else { ModConfig.statsX = placed.x(); ModConfig.statsY = placed.y(); }
    }
    private void reset() {
        if (selected == 0) { ModConfig.gqScale = 1; GuildQuestsManager.WIDGET.setHudPosition(5, 60); }
        else if (selected == 1) { ModConfig.sidebarScale = 1; ModConfig.sidebarX = ModConfig.sidebarY = -1; }
        else if (selected == 2) { ModConfig.bossBarScale = 1; ModConfig.bossBarX = ModConfig.bossBarY = -1; }
        else { ModConfig.statsScale = 1; ModConfig.statsX = ModConfig.statsY = -1; }
        init();
    }
    @Override public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {}
    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        GuildQuestsManager.WIDGET.setHudRenderingDisabled(false);
        GuildQuestsManager.WIDGET.renderWidgetPreview(context);
        GuildQuestsManager.WIDGET.setHudRenderingDisabled(true);
        for (int i = 0; i < NAMES.length; i++) {
            var r = bounds(i);
            int x = (int)r.x(), y = (int)r.y(), right = (int)(r.x() + r.width()), bottom = (int)(r.y() + r.height());
            int color = i == selected ? 0xFFFFAA00 : 0x66888888;
            context.fill(x - 1, y - 1, right + 1, y, color); context.fill(x - 1, bottom, right + 1, bottom + 1, color);
            context.fill(x - 1, y, x, bottom, color); context.fill(right, y, right + 1, bottom, color);
        }
        context.fill(0, height - 53, width, height, 0xDD111111);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Drag: move | Wheel: size | Esc: save"), width / 2, height - 64, 0xFFFFFFFF);
        if (selected == 2) context.drawCenteredTextWithShadow(textRenderer,
            Text.literal(HudLayout.receivedBossBars + " received • auto fit " + Math.round(HudLayout.effectiveBossScale * 100) + "%"), width / 2, height - 76, 0xFFFFDD55);
        super.render(context, mouseX, mouseY, delta);
    }
    @Override public boolean mouseClicked(Click click, boolean doubled) {
        if (super.mouseClicked(click, doubled)) return true;
        if (click.button() != 0) return false;
        for (int step = 0; step < NAMES.length; step++) {
            int kind = (selected + step) % NAMES.length;
            var r = bounds(kind);
            if (r.contains(click.x(), click.y())) {
                selected = kind; offsetX = click.x() - r.x(); offsetY = click.y() - r.y(); dragging = true; init(); return true;
            }
        }
        return false;
    }
    @Override public boolean mouseDragged(Click click, double dx, double dy) {
        if (dragging) { position((float)(click.x() - offsetX), (float)(click.y() - offsetY)); return true; }
        return super.mouseDragged(click, dx, dy);
    }
    @Override public boolean mouseReleased(Click click) { dragging = false; return super.mouseReleased(click); }
    @Override public boolean mouseScrolled(double x, double y, double horizontal, double vertical) { changeScale((float)Math.signum(vertical) * 0.05f); return true; }
    @Override public boolean keyPressed(KeyInput input) { if (input.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) { close(); return true; } return super.keyPressed(input); }
    @Override public void close() {
        ModConfig.saveConfig(); GuildQuestsManager.WIDGET.saveConfig(); super.close();
    }
    @Override public void removed() { HudLayout.editing = false; GuildQuestsManager.WIDGET.setHudRenderingDisabled(false); super.removed(); }
    @Override public boolean shouldPause() { return false; }
}

