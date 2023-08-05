package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.weavemc.loader.api.event.RenderLivingEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
   public static TickSetting ignoreBots;
   public Chams() {
      super("Chams", ModuleCategory.render);
      this.registerSetting(ignoreBots = new TickSetting("Hide bots", true));
   }

   @SubscribeEvent
   public void onPreLivingRender(RenderLivingEvent.Pre event) {
      if (event.getEntity() != mc.thePlayer) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1100000.0F);
      }
   }

   @SubscribeEvent
   public void onPostLivingRender(RenderLivingEvent.Post event) {
      if (event.getEntity() != mc.thePlayer && (!ignoreBots.isToggled() || !AntiBot.bot(event.getEntity()))) {
         GL11.glDisable(32823);
         GL11.glPolygonOffset(1.0F, 1100000.0F);
      }
   }
}
