package me.dekuscrub.amoebaorigin;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginRegistry;
import io.github.apace100.origins.registry.ModComponents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Predicate;

public class Amoebaorigin implements ModInitializer {

    public static final PowerType<Power> MANIFEST = new PowerTypeReference<Power>(new Identifier("amoeba_origin", "manifest"));

    @Override
    public void onInitialize() {
    }

    public static void resetOrigin(ServerPlayerEntity player) {
        if (MANIFEST.isActive(player)) {
            ModComponents.ORIGIN.get(player).setOrigin(OriginLayers.getLayer(Identifier.tryParse("origins:origin")), OriginRegistry.get(Identifier.tryParse("origins:human")));
            OriginComponent.sync(player);
        }
    }

    public static void updateOrigin(ServerPlayerEntity player) {
        if (!player.hasStatusEffect(StatusEffects.LUCK)) {
            Amoebaorigin.resetOrigin(player);
        }
    }


    public static void bitePlayer(ServerPlayerEntity player, Entity target) {
        OriginLayer layer = OriginLayers.getLayer(Identifier.tryParse("origins:origin"));
        OriginComponent component = ModComponents.ORIGIN.get(player);

        if (player.isPlayer()) {
            if (MANIFEST.isActive(player)) {
                if (player.getInventory().getMainHandStack() == ItemStack.EMPTY) {
                    if (Screen.hasAltDown()) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 100));
                        if (target.isPlayer()) {
                            Origin origin = ModComponents.ORIGIN.get(target).getOrigin(layer);
                            component.setOrigin(layer, origin);
                            OriginComponent.sync(player);
                        }
                    }
                }
            }
        }
    }
}