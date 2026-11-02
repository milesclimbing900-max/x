package com.example.lagless.mixin;

import com.example.lagless.LaglessClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    // Skip rendering (model, shadow, nametag) for every player except yourself.
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void lagless$skipPlayers(Entity entity, Frustum frustum, double x, double y, double z,
                                     CallbackInfoReturnable<Boolean> cir) {
        if (LaglessClient.hidePlayers
                && entity instanceof PlayerEntity
                && entity != MinecraftClient.getInstance().player) {
            cir.setReturnValue(false);
        }
    }
}
