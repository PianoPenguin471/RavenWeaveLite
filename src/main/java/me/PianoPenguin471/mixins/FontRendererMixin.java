package me.PianoPenguin471.mixins;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FontRenderer.class)
public class FontRendererMixin {
    /*
    This causes a crash and I can't be bothered to fix it bc I don't care for the feature it provides
    @ModifyArg(method = "renderString", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"), index = 0)
    public String fixStringInputAtPos(String string) {
        return ASMEventHandler.getUnformattedTextForChat(string);
    }
     */
}
