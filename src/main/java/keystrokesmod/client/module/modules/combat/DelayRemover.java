package keystrokesmod.client.module.modules.combat;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.ReflectionUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

import java.lang.reflect.Field;

public class DelayRemover extends Module {
   public static DescriptionSetting desc;

   private final Field leftClickCounterField;

   public DelayRemover() {
      super("Delay Remover", ModuleCategory.combat);
      withEnabled(true);

      this.registerSetting(desc = new DescriptionSetting("Gives you 1.7 hitreg."));
      this.leftClickCounterField = ReflectionUtils.findField(Minecraft.class, "leftClickCounter");
      if (this.leftClickCounterField != null) this.leftClickCounterField.setAccessible(true);
   }

   @Override
   public boolean canBeEnabled() {
      return this.leftClickCounterField != null;
   }

   @SubscribeEvent
   public void playerTickEvent(TickEvent event) {
      if (Utils.Player.isPlayerInGame() && this.leftClickCounterField != null) {
         if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
            return;
         }

         try {
            this.leftClickCounterField.set(mc, 0);
         } catch (IllegalAccessException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            this.disable();
         }
      }
   }
}