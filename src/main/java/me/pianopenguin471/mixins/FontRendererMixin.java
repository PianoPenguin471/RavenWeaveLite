package me.pianopenguin471.mixins;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import keystrokesmod.client.tweaker.ASMEventHandler;

@Mixin(FontRenderer.class)
public abstract class FontRendererMixin {
    @ModifyVariable(method = "renderString", at = @At(value = "HEAD"), argsOnly = true)
    private String renderStringHook(String text) {
        return ASMEventHandler.getUnformattedTextForChat(text);
    }
}
