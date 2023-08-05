package com.example.mod.hook;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.*;

public class EntityPlayerHook extends Hook {
    public EntityPlayerHook() {
        super("net.minecraft.entity.player.EntityPlayer");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig cfg) {
        for (MethodNode methodNode : classNode.methods) {
            String mappedMethodName = methodNode.name;
            if (mappedMethodName.equalsIgnoreCase("attackTargetEntityWithCurrentItem")) {
                AbstractInsnNode[] arr = methodNode.instructions.toArray();

                for (int i = 0; i < arr.length; ++i) {
                    AbstractInsnNode ins = arr[i];
                    if (i == 232) {
                        methodNode.instructions.insert(ins, this.h());
                    } else if (i >= 233 && i <= 248) {
                        methodNode.instructions.remove(ins);
                    } else if (i == 249) {
                        return;
                    }
                }

                return;
            }
        }

    }

    private InsnList h() {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(25, 1));
        insnList.add(new MethodInsnNode(184, ASMEventHandler.eventHandlerClassName, "onAttackTargetEntityWithCurrentItem", "(Lnet/minecraft/entity/Entity;)V", false));
        return insnList;
    }
}
