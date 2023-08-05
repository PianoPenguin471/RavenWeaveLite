package com.example.mod.hook;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.*;

public class MinecraftHook extends Hook {
    public MinecraftHook() {
        super("net/minecraft/client/Minecraft");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        for (MethodNode m : classNode.methods) {
            String n = m.name;
            if (n.equalsIgnoreCase("runTick") ) {
                AbstractInsnNode[] arr = m.instructions.toArray();

                for (int i = 0; i < arr.length; ++i) {
                    AbstractInsnNode ins = arr[i];
                    if (i == 39) {
                        m.instructions.insert(ins, this.getEventInsn());
                    } else if (i >= 40 && i <= 45) {
                        m.instructions.remove(ins);
                    } else if (i == 46) {
                        return;
                    }
                }

                return;
            }
        }

    }

    private InsnList getEventInsn() {
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(184, ASMEventHandler.eventHandlerClassName, "onTick", "()V", false));
        return insnList;
    }
}
