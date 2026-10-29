package com.lycanitesfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Causa real del crash: BaseOverlayScreen extiende Screen pero su constructor ignora el
 * parametro Minecraft y nunca se llama a init(), asi que Screen.minecraft queda null.
 * Aqui lo rellenamos al terminar el constructor. (Heredar de Screen solo sirve para poder
 * acceder al campo protegido 'minecraft'; el constructor de esta clase nunca se ejecuta.)
 */
@Mixin(targets = "com.lycanitesmobs.client.gui.screen.base.BaseOverlayScreen", remap = false)
public abstract class BaseOverlayScreenCtorMixin extends Screen {
    protected BaseOverlayScreenCtorMixin(Component title) {
        super(title);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false, require = 0)
    private void lycanitesfix$fillMinecraft(CallbackInfo ci) {
        if (this.minecraft == null) {
            this.minecraft = Minecraft.getInstance();
        }
    }
}
