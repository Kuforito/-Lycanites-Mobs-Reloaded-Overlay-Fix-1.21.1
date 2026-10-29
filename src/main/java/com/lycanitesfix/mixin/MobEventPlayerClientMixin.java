package com.lycanitesfix.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Hace visible el cartel que sale al empezar un evento de mobs.
 * En 1.21.1 TextureManager.bindForSetup() solo hace glBindTexture; el quad se dibuja con
 * Tesselator (POSITION_TEX) sin elegir shader ni RenderSystem.setShaderTexture(0, ...),
 * asi que sale invisible. Aqui, justo despues del bind, fijamos shader y textura correctos.
 */
@Mixin(targets = "com.lycanitesmobs.client.event.mobevent.MobEventPlayerClient", remap = false)
public abstract class MobEventPlayerClientMixin {
    @Redirect(
        method = "onGUIUpdate",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindForSetup(Lnet/minecraft/resources/ResourceLocation;)V",
                 remap = false),
        remap = false, require = 0)
    private void lycanitesfix$bindWithShader(TextureManager textureManager, ResourceLocation location) {
        textureManager.bindForSetup(location);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
    }
}
