package com.example.mod.hook;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class GuiUtilRenderComponentsHook extends Hook {
    public GuiUtilRenderComponentsHook() {
        super("net.minecraft.client.gui.GuiUtilRenderComponents");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig cfg) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equalsIgnoreCase("splitText")) {
                ListIterator<AbstractInsnNode> i = methodNode.instructions.iterator();

                while (true) {
                    MethodInsnNode mn;
                    do {
                        do {
                            AbstractInsnNode ne;
                            do {
                                if (!i.hasNext()) {
                                    return;
                                }

                                ne = i.next();
                            } while (!(ne instanceof MethodInsnNode));

                            mn = (MethodInsnNode) ne;
                        } while (!mn.owner.equals("net/minecraft/util/IChatComponent") && !mn.owner.equals("eu"));
                    } while (!mn.name.equals("getUnformattedTextForChat") && !mn.name.equals("e"));

                    if (mn.desc.equals("()Ljava/lang/String;")) {
                        i.add(this.getEventInsn());
                    }
                }
            }
        }
    }

    private AbstractInsnNode getEventInsn() {
        return new MethodInsnNode(184, ASMEventHandler.eventHandlerClassName, "getUnformattedTextForChat", "(Ljava/lang/String;)Ljava/lang/String;", false);
    }
}
