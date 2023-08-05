package com.example.mod.hook;

import keystrokesmod.client.tweaker.interfaces.IThrowableItem;
import net.minecraft.item.ItemStack;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class ThrowableHook extends Hook {
    public ThrowableHook(String target, String... extraTargets) {
        super("net.minecraft.item.ItemEgg",
                "net.minecraft.item.ItemEnderEye",
                "net.minecraft.item.ItemEnderPearl",
                "net.minecraft.item.ItemSnowball",
                "net.minecraft.item.ItemPotion",
                "net.minecraft.item.ItemExpBottle");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig cfg) {
        // only called for classes listed in getClassName();

        // add IThrowableItem interface
        classNode.interfaces.add(IThrowableItem.class.getName().replace(".", "/"));

        // implement the method
        try {
            MethodNode methodInsnNode = new MethodNode(
                    ACC_PUBLIC,
                    "isThrowable",
                    Type.getMethodDescriptor(
                            IThrowableItem.class.getMethod("isThrowable", ItemStack.class)
                    ),
                    null,
                    new String[]{}
            );

            InsnList insnList = new InsnList();
            if (!classNode.name.equals("net/minecraft/item/ItemPotion")) {
                // ICONST_I (aka true) if the item isn't a potion
                insnList.add(new InsnNode(ICONST_1));
            } else {
                // load the specified ItemStack (passed as method param)
                insnList.add(new VarInsnNode(ALOAD, 1));

                // get metadata of the loaded ItemStack
                insnList.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getMetadata", "()I", false));

                // check if it's a splash potion with its metadata
                insnList.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/item/ItemPotion", "isSplash", "(I)Z", false));
            }

            // return the result (boolean)
            insnList.add(new InsnNode(IRETURN));
            methodInsnNode.instructions.add(insnList);

            // add the method
            classNode.methods.add(methodInsnNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
