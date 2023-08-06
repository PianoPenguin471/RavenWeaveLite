package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class Sprint extends Module {
   public static TickSetting omni;

   public Sprint() {
      super("Sprint", ModuleCategory.movement);
      this.registerSetting(omni = new TickSetting("OmniSprint", false));
   }

   @SubscribeEvent
   public void onTick(TickEvent e) {
      if (this.enabled) {
         if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus) {
            EntityPlayerSP p = mc.thePlayer;
            if (omni.isToggled()) {
               if (Utils.Player.isMoving() && p.getFoodStats().getFoodLevel() > 6) {
                  p.setSprinting(true);
               }
            } else {
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
            }
         }
      }
   }
}
