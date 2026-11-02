package com.example.lagless.mixin;

import com.example.lagless.LaglessClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    // Never create the particle at all, so nothing is ticked or rendered.
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"), cancellable = true)
    private void lagless$dropParticles(ParticleEffect effect, double x, double y, double z,
                                       double vx, double vy, double vz,
                                       CallbackInfoReturnable<Particle> cir) {
        if (LaglessClient.hideParticles) cir.setReturnValue(null);
    }
}
