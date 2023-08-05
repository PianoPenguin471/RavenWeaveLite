package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class FPSSpoofer extends Module {
    public static DescriptionSetting desc;
    public static DoubleSliderSetting fps;

    public int ticksPassed;

    private final Field fpsField;

    public FPSSpoofer() {
        super("FPSSpoof", ModuleCategory.other);
        this.registerSetting(desc = new DescriptionSetting("Spoofs your fps"));
        this.registerSetting(fps = new DoubleSliderSetting("FPS", 99860, 100000, 0, 100000, 100));

        fpsField = ReflectionUtils.findField(Minecraft.class,"fpsCounter");
        fpsField.setAccessible(true);
    }

    @Override
    public boolean canBeEnabled() {
        return fpsField != null;
    }

    public void onEnable(){
        ticksPassed = 0;
    }

    @SubscribeEvent
    public void onTick(TickEvent.Pre event) {
        guiUpdate();

        try {
            int fpsN = ThreadLocalRandom.current().nextInt((int) fps.getInputMin(), (int) fps.getInputMax() + 1);
            fpsField.set(mc, fpsN);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            this.disable();
        }
        ticksPassed = 0;
        // }
        ticksPassed++;
    }
}
