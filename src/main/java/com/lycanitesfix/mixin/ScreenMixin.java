package com.lycanitesfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Lycanites Mobs crea un BaseOverlayScreen sin inicializarlo (nunca pasa por init()),
 * asi que Screen.minecraft es null y getMinecraft() devuelve null.
 * Si pasa eso, devolvemos Minecraft.getInstance() (es el mismo objeto).
 * require = 0: si la version de NeoForge no tiene este metodo, no pasa nada.
 */
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "getMinecraft", at = @At("RETURN"), cancellable = true, remap = false, require = 0)
    private void lycanitesfix$nonNullMinecraft(CallbackInfoReturnable<Minecraft> cir) {
        if (cir.getReturnValue() == null) {
            cir.setReturnValue(Minecraft.getInstance());
        }
    }
}
