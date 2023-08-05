package keystrokesmod.client.tweaker.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public interface Transformer extends Opcodes {
   String[] getClassName();

   void transform(ClassNode classNode, String transformedName);

   default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
      return methodNode.name;
   }
}
