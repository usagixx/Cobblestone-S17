package com.jandbdavenport.cobblestonehelper.features;

import com.jandbdavenport.cobblestonehelper.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/** Inspects received display data, independent of the camera, visibility and audio settings. */
public final class RushNotifier {
    private static final String[] NAMES = {"RUSH", "ECHO"};
    private static final ProcSequenceGate[] sequences = {new ProcSequenceGate(), new ProcSequenceGate()};
    private static Object world;
    private static final List<Set<Integer>> lastSeen = List.of(new HashSet<>(), new HashSet<>());
    private static final List<Set<Integer>> completedEntities = List.of(new HashSet<>(), new HashSet<>());
    private RushNotifier() {}

    private static boolean enabled(int kind) {
        return kind == 0 ? ModConfig.rushNoticeEnabled : ModConfig.echoNoticeEnabled;
    }

    public static void init() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> reset());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (world != client.world) { reset(); world = client.world; }
            if (client.world == null || client.player == null) { reset(); return; }
            var seen = List.<Set<Integer>>of(new HashSet<>(), new HashSet<>());
            if (ModConfig.rushNoticeEnabled || ModConfig.echoNoticeEnabled) {
                for (var entity : client.world.getEntities()) {
                    if (entity.isRemoved()) continue;
                    int kinds = 0;
                    if (entity instanceof DisplayEntity.ItemDisplayEntity display) kinds |= classify(display.getItemStack());
                    if (entity instanceof ArmorStandEntity stand) {
                        for (EquipmentSlot slot : EquipmentSlot.values()) kinds |= classify(stand.getEquippedStack(slot));
                    }
                    for (int kind = 0; kind < 2; kind++) if ((kinds & (1 << kind)) != 0) seen.get(kind).add(entity.getId());
                }
            }
            for (int kind = 0; kind < 2; kind++) {
                lastSeen.get(kind).clear(); lastSeen.get(kind).addAll(seen.get(kind));
                completedEntities.get(kind).retainAll(seen.get(kind));
                if (!enabled(kind)) { sequences[kind].reset();  continue; }
                boolean fresh = false;
                for (int id : seen.get(kind)) if (!completedEntities.get(kind).contains(id)) { fresh = true; break; }
                if (sequences[kind].update(fresh)) {
                    preview(kind);
                    System.out.println("[S17 Proc] Detected " + NAMES[kind] + " display model.");
                }
            }
        });
    }

    private static int classify(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        var model = stack.get(DataComponentTypes.ITEM_MODEL);
        if (model == null) return 0;
        var data = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        Float value = data == null ? null : data.getFloat(0);
        if (RushModel.matches(model.toString(), value)) return 1;
        if (RushModel.matchesEcho(model.toString(), value)) return 2;
        return 0;
    }

    private static void reset() {
        for (int kind = 0; kind < 2; kind++) {
            sequences[kind].reset();
            lastSeen.get(kind).clear(); completedEntities.get(kind).clear();
        }
        world = null;
    }
    public static void serverMessage(Text text) { complete(ProcCompletion.chatKind(text.getString())); }
    private static void complete(int kind) {
        if (kind < 0) return;
        sequences[kind].reset();
        completedEntities.get(kind).addAll(lastSeen.get(kind));
        System.out.println("[S17 Proc] Completed " + NAMES[kind] + " chain; waiting for next chance.");

    }
    public static void preview() { preview(0); }
    public static void previewEcho() { preview(1); }
    private static void preview(int kind) {
        var client = MinecraftClient.getInstance();
        if (client.player == null || !enabled(kind)) return;
        client.inGameHud.setTitleTicks(0, 50, 10);
        // Keep the server's separate crop-reward subtitle intact.
        client.inGameHud.setTitle(ProcTitleAppearance.chance(kind));
        if (ModConfig.chanceSoundEnabled) client.getSoundManager().play(net.minecraft.client.sound.PositionedSoundInstance.master(
            net.minecraft.sound.SoundEvents.BLOCK_ANVIL_PLACE, 1.0f, 1.0f));
    }
}
