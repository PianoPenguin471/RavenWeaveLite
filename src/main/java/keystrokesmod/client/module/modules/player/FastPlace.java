package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.ReflectionUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

import java.lang.reflect.Field;

public class FastPlace extends Module {
   public static SliderSetting delaySlider;
   public static TickSetting blockOnly;

   public final static Field rightClickDelayTimerField;

   static {
      rightClickDelayTimerField = ReflectionUtils.findField(Minecraft.class, "rightClickDelayTimer");

      if (rightClickDelayTimerField != null) {
         rightClickDelayTimerField.setAccessible(true);
      }
   }

   public FastPlace() {
      super("FastPlace", ModuleCategory.player);
      this.registerSetting(delaySlider = new SliderSetting("Delay", 0.0D, 0.0D, 4.0D, 1.0D));
      this.registerSetting(blockOnly = new TickSetting("Blocks only", true));
   }

   @Override
   public boolean canBeEnabled() {
      return rightClickDelayTimerField != null;
   }

   @SubscribeEvent
   public void onPlayerTick(TickEvent.Post event) {
      if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus && rightClickDelayTimerField != null) {
         if (blockOnly.isToggled()) {
            ItemStack item = mc.thePlayer.getHeldItem();
            if (item == null || !(item.getItem() instanceof ItemBlock)) {
               return;
            }
         }

         try {
            int c = (int) delaySlider.getInput();
            if (c == 0) {
               rightClickDelayTimerField.set(mc, 0);
            } else {
               if (c == 4) {
                  return;
               }

               int d = rightClickDelayTimerField.getInt(mc);
               if (d == 4) {
                  rightClickDelayTimerField.set(mc, c);
               }
            }
         } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
         }
      }
   }
}
