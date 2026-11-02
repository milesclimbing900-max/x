package com.example.lagless;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class LaglessClient implements ClientModInitializer {
    public static boolean hidePlayers = true;
    public static boolean hideParticles = true;

    private static KeyBinding playersKey;
    private static KeyBinding particlesKey;

    @Override
    public void onInitializeClient() {
        playersKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lagless.players", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.lagless"));
        particlesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lagless.particles", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.lagless"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (playersKey.wasPressed()) {
                hidePlayers = !hidePlayers;
                msg(client, "Hide players: " + (hidePlayers ? "ON" : "OFF"));
            }
            while (particlesKey.wasPressed()) {
                hideParticles = !hideParticles;
                msg(client, "Hide particles: " + (hideParticles ? "ON" : "OFF"));
            }
        });
    }

    private static void msg(net.minecraft.client.MinecraftClient c, String s) {
        if (c.player != null) c.player.sendMessage(Text.literal("[Lagless] " + s), true);
    }
}
