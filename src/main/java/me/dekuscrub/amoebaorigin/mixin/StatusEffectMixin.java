package me.dekuscrub.amoebaorigin.mixin;

import me.dekuscrub.amoebaorigin.Amoebaorigin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffect.class)
public class StatusEffectMixin {

    @Inject(method = "onRemoved", at = @At("HEAD"))
    private void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier, CallbackInfo ci) {
        if (entity.isPlayer()) {
            Amoebaorigin.updateOrigin((ServerPlayerEntity) entity);
        }
    }
}
