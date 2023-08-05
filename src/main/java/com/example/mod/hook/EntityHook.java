package com.example.mod.hook;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityHook extends Hook {
    public EntityHook() {
        super("net.minecraft.entity.Entity");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig cfg) {
        for (MethodNode methodNode : classNode.methods) {
            String n = methodNode.name;
            if (n.equalsIgnoreCase("moveEntity")) {
                AbstractInsnNode[] arr = methodNode.instructions.toArray();

                for (int i = 0; i < arr.length; ++i) {
                    AbstractInsnNode ins = arr[i];
                    if (i >= 99 && i <= 117) {
                        methodNode.instructions.remove(ins);
                    } else if (i == 118) {
                        methodNode.instructions.insertBefore(ins, this.getEventInsn());
                        return;
                    }
                }

                return;
            }
        }

    }

    private InsnList getEventInsn() {
        InsnList insnList = new InsnList();

        // grab current entity?
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));

        // pass it into the function?
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ASMEventHandler.eventHandlerClassName, "onEntityMove", "(Lnet/minecraft/entity/Entity;)Z", false));

        // store it in variable number 19?
        insnList.add(new VarInsnNode(Opcodes.ISTORE, 19));
        return insnList;
    }
}
