package keystrokesmod.client.clickgui.raven.components;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.modules.client.GuiModule;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BindComponent implements Component {
    private boolean isBinding;
    private final ModuleComponent moduleComponent;
    private int offset;
    private int x;
    private int y;

    public BindComponent(ModuleComponent moduleComponent, int offset) {
        this.moduleComponent = moduleComponent;
        this.x = moduleComponent.category.getX() + moduleComponent.category.getWidth();
        this.y = moduleComponent.category.getY() + moduleComponent.o;
        this.offset = offset;
    }

    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        this.dr(this.isBinding ? BindStage.binding : BindStage.bind + ": " + Keyboard.getKeyName(this.moduleComponent.mod.getKeycode()));
        GL11.glPopMatrix();
    }

    @Override
    public void update(int mousePosX, int mousePosY) {
        boolean h = this.i(mousePosX, mousePosY);
        this.y = this.moduleComponent.category.getY() + this.offset;
        this.x = this.moduleComponent.category.getX();
    }

    public void mouseDown(int x, int y, int b) {
        if (this.i(x, y) && b == 0 && this.moduleComponent.po) {
            this.isBinding = !this.isBinding;
        }

    }

    @Override
    public void mouseReleased(int x, int y, int m) {

    }

    @Override
    public void keyTyped(char t, int k) {
        if (!this.moduleComponent.mod.getName().equalsIgnoreCase("AutoConfig")) {
            if (this.isBinding) {
                if (k == 11) {
                    if (this.moduleComponent.mod instanceof GuiModule) {
                        this.moduleComponent.mod.setbind(54);
                    } else {
                        this.moduleComponent.mod.setbind(0);
                    }
                } else {
                    this.moduleComponent.mod.setbind(k);
                }

                this.isBinding = false;
            }

        }
    }

    @Override
    public void setComponentStartAt(int n) {
        this.offset = n;
    }

    public boolean i(int x, int y) {
        return x > this.x && x < this.x + this.moduleComponent.category.getWidth() && y > this.y - 1 && y < this.y + 12;
    }

    public int getHeight() {
        return 16;
    }

    private void dr(String s) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(s, (float)((this.moduleComponent.category.getX() + 4) * 2), (float)((this.moduleComponent.category.getY() + this.offset + 3) * 2), Color.HSBtoRGB((float)(System.currentTimeMillis() % 3750L) / 3750.0F, 0.8F, 0.8F));
    }
}
