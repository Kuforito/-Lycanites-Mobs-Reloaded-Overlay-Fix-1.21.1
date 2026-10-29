package com.lycanitesfix.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Por si BaseOverlayScreen declara su propio getMinecraft() en vez de heredarlo de Screen.
 * Se apunta por nombre (targets) para no depender del jar de Lycanites al compilar.
 * require = 0 y remap = false: si el metodo no existe aqui, simplemente no hace nada.
 */
@Mixin(targets = "com.lycanitesmobs.client.gui.screen.base.BaseOverlayScreen", remap = false)
public abstract class BaseOverlayScreenMixin {
    @Inject(method = "getMinecraft", at = @At("RETURN"), cancellable = true, remap = false, require = 0)
    private void lycanitesfix$nonNullMinecraft(CallbackInfoReturnable<Minecraft> cir) {
        if (cir.getReturnValue() == null) {
            cir.setReturnValue(Minecraft.getInstance());
        }
    }
}
