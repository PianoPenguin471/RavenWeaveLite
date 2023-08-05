package com.example.mod.hook;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.weavemc.loader.api.Hook;
import org.objectweb.asm.tree.*;

public class FontRendererHook extends Hook {
    public FontRendererHook() {
        super("net.minecraft.client.gui.FontRenderer");
    }

    public void transform(ClassNode classNode, AssemblerConfig cfg) {
        for (MethodNode methodNode: classNode.methods) {
            String name = methodNode.name;
            if (name.equalsIgnoreCase("renderStringAtPos") || name.equalsIgnoreCase("getStringWidth")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.getEventInsn());
            }
        }
    }

    private InsnList getEventInsn() {
        InsnList i = new InsnList();
        i.add(new VarInsnNode(25, 1));
        i.add(new MethodInsnNode(184, ASMEventHandler.eventHandlerClassName, "getUnformattedTextForChat", "(Ljava/lang/String;)Ljava/lang/String;", false));
        i.add(new VarInsnNode(58, 1));
        return i;
    }
}
