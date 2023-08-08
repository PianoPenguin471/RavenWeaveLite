package me.PianoPenguin471.mixins;

import keystrokesmod.client.tweaker.ASMEventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(priority = 995, value = Entity.class)
public abstract class EntityMixin {

    @Shadow
    public float rotationYaw;

    @Shadow
    public float rotationPitch;

    @Shadow
    public float prevRotationPitch;

    @Shadow
    public float prevRotationYaw;

    @Shadow
    public boolean noClip;

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB p_setEntityBoundingBox_1_);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    protected abstract void resetPositionToBB();

    @Shadow
    public World worldObj;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public boolean onGround;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public float stepHeight;
    @Shadow
    public boolean isCollidedHorizontally;
    @Shadow
    public boolean isCollidedVertically;
    @Shadow
    public boolean isCollided;

    @Shadow
    protected abstract void updateFallState(double p_updateFallState_1_, boolean p_updateFallState_3_,
                                            Block p_updateFallState_4_, BlockPos p_updateFallState_5_);

    @Shadow
    protected abstract boolean canTriggerWalking();

    @Shadow
    public Entity ridingEntity;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    private int nextStepDistance;

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    public abstract void playSound(String p_playSound_1_, float p_playSound_2_, float p_playSound_3_);

    @Shadow
    protected abstract String getSwimSound();

    @Shadow
    protected Random rand;

    @Shadow
    protected abstract void playStepSound(BlockPos p_playStepSound_1_, Block p_playStepSound_2_);

    @Shadow
    protected abstract void doBlockCollisions();

    @Shadow
    public abstract void addEntityCrashInfo(CrashReportCategory p_addEntityCrashInfo_1_);

    @Shadow
    public abstract boolean isWet();

    @Shadow
    protected abstract void dealFireDamage(int p_dealFireDamage_1_);

    @Shadow
    private int fire;

    @Shadow
    public abstract void setFire(int p_setFire_1_);

    @Shadow
    public int fireResistance;

    /**
     * @author mc code
     * @reason too complicated to inject without mod compatibility issues
     */
    @Overwrite
    public void moveEntity(double var1, double var3, double var5) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(var1, var3, var5));
            this.resetPositionToBB();
        } else {
            this.worldObj.theProfiler.startSection("move");
            double var7 = this.posX;
            double var9 = this.posY;
            double var11 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                var1 *= 0.25;
                var3 *= 0.05000000074505806;
                var5 *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }

            double var13 = var1;
            double var15 = var3;
            double var17 = var5;

            Minecraft mc = Minecraft.getMinecraft();
            boolean var19 = mc.thePlayer.isSneaking() && mc.thePlayer.onGround;

            if (var19) {
                double var20;
                for(var20 = 0.05; var1 != 0.0 && this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)), this.getEntityBoundingBox().offset(var1, -1.0, 0.0)).isEmpty(); var13 = var1) {
                    if (var1 < var20 && var1 >= -var20) {
                        var1 = 0.0;
                    } else if (var1 > 0.0) {
                        var1 -= var20;
                    } else {
                        var1 += var20;
                    }
                }

                for (; (var5 != 0.0) && this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)), this.getEntityBoundingBox().offset(0.0, -1.0, var5)).isEmpty(); var17 = var5) {
                    if (var5 < var20 && var5 >= -var20) {
                        var5 = 0.0;
                    } else if (var5 > 0.0) {
                        var5 -= var20;
                    } else {
                        var5 += var20;
                    }
                }

                for (; (var1 != 0.0) && (var5 != 0.0) && this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)), this.getEntityBoundingBox().offset(var1, -1.0, var5)).isEmpty(); var17 = var5) {
                    if ((var1 < var20) && (var1 >= -var20)) {
                        var1 = 0.0;
                    } else if (var1 > 0.0) {
                        var1 -= var20;
                    } else {
                        var1 += var20;
                    }

                    var13 = var1;
                    if ((var5 < var20) && (var5 >= -var20)) {
                        var5 = 0.0;
                    } else if (var5 > 0.0) {
                        var5 -= var20;
                    } else {
                        var5 += var20;
                    }
                }
            }

            List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)),
                    this.getEntityBoundingBox().addCoord(var1, var3, var5));
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();

            AxisAlignedBB axisalignedbb1;
            for (Iterator var22 = list1.iterator(); var22.hasNext(); var3 = axisalignedbb1
                    .calculateYOffset(this.getEntityBoundingBox(), var3)) {
                axisalignedbb1 = (AxisAlignedBB) var22.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, var3, 0.0));
            boolean flag1 = this.onGround || ((var15 != var3) && (var15 < 0.0));

            AxisAlignedBB axisalignedbb13;
            Iterator var55;
            for (var55 = list1.iterator(); var55.hasNext(); var1 = axisalignedbb13
                    .calculateXOffset(this.getEntityBoundingBox(), var1)) {
                axisalignedbb13 = (AxisAlignedBB) var55.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(var1, 0.0, 0.0));

            for (var55 = list1.iterator(); var55.hasNext(); var5 = axisalignedbb13
                    .calculateZOffset(this.getEntityBoundingBox(), var5)) {
                axisalignedbb13 = (AxisAlignedBB) var55.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, var5));
            if ((this.stepHeight > 0.0F) && flag1 && ((var13 != var1) || (var17 != var5))) {
                double d11 = var1;
                double d7 = var3;
                double d8 = var5;
                AxisAlignedBB axisalignedbb3 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(axisalignedbb);
                var3 = this.stepHeight;
                List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)),
                        this.getEntityBoundingBox().addCoord(var13, var3, var17));
                AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
                AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(var13, 0.0, var17);
                double d9 = var3;

                AxisAlignedBB axisalignedbb6;
                for (Iterator var35 = list.iterator(); var35
                        .hasNext(); d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9)) {
                    axisalignedbb6 = (AxisAlignedBB) var35.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(0.0, d9, 0.0);
                double d15 = var13;

                AxisAlignedBB axisalignedbb7;
                for (Iterator var37 = list.iterator(); var37
                        .hasNext(); d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15)) {
                    axisalignedbb7 = (AxisAlignedBB) var37.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(d15, 0.0, 0.0);
                double d16 = var17;

                AxisAlignedBB axisalignedbb8;
                for (Iterator var39 = list.iterator(); var39
                        .hasNext(); d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16)) {
                    axisalignedbb8 = (AxisAlignedBB) var39.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(0.0, 0.0, d16);
                AxisAlignedBB axisalignedbb14 = this.getEntityBoundingBox();
                double d17 = var3;

                AxisAlignedBB axisalignedbb9;
                for (Iterator var42 = list.iterator(); var42
                        .hasNext(); d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17)) {
                    axisalignedbb9 = (AxisAlignedBB) var42.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(0.0, d17, 0.0);
                double d18 = var13;

                AxisAlignedBB axisalignedbb10;
                for (Iterator var44 = list.iterator(); var44
                        .hasNext(); d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18)) {
                    axisalignedbb10 = (AxisAlignedBB) var44.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(d18, 0.0, 0.0);
                double d19 = var17;

                AxisAlignedBB axisalignedbb11;
                for (Iterator var46 = list.iterator(); var46
                        .hasNext(); d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19)) {
                    axisalignedbb11 = (AxisAlignedBB) var46.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(0.0, 0.0, d19);
                double d20 = (d15 * d15) + (d16 * d16);
                double d10 = (d18 * d18) + (d19 * d19);
                if (d20 > d10) {
                    var1 = d15;
                    var5 = d16;
                    var3 = -d9;
                    this.setEntityBoundingBox(axisalignedbb4);
                } else {
                    var1 = d18;
                    var5 = d19;
                    var3 = -d17;
                    this.setEntityBoundingBox(axisalignedbb14);
                }

                AxisAlignedBB axisalignedbb12;
                for (Iterator var50 = list.iterator(); var50.hasNext(); var3 = axisalignedbb12
                        .calculateYOffset(this.getEntityBoundingBox(), var3)) {
                    axisalignedbb12 = (AxisAlignedBB) var50.next();
                }

                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, var3, 0.0));
                if (((d11 * d11) + (d8 * d8)) >= ((var1 * var1) + (var5 * var5))) {
                    var1 = d11;
                    var3 = d7;
                    var5 = d8;
                    this.setEntityBoundingBox(axisalignedbb3);
                }
            }

            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.resetPositionToBB();
            this.isCollidedHorizontally = ASMEventHandler.onEntityMove((Entity) (Object) this);
            this.isCollidedVertically = var15 != var3;
            this.onGround = this.isCollidedVertically && (var15 < 0.0);
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor_double(this.posZ);
            BlockPos blockpos = new BlockPos(i, j, k);
            Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
            if (block1.getMaterial() == Material.air) {
                Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
                if ((block instanceof BlockFence) || (block instanceof BlockWall) || (block instanceof BlockFenceGate)) {
                    block1 = block;
                    blockpos = blockpos.down();
                }
            }

            this.updateFallState(var3, this.onGround, block1, blockpos);
            if (var13 != var1) {
                this.motionX = 0.0;
            }

            if (var17 != var5) {
                this.motionZ = 0.0;
            }

            if (var15 != var3) {
                block1.onLanded(this.worldObj, ((Entity) ((Object) this)));
            }

            if (this.canTriggerWalking() && !var19 && (this.ridingEntity == null)) {
                double d12 = this.posX - var7;
                double d13 = this.posY - var9;
                double d14 = this.posZ - var11;
                if (block1 != Blocks.ladder) {
                    d13 = 0.0;
                }

                if ((block1 != null) && this.onGround) {
                    block1.onEntityCollidedWithBlock(this.worldObj, blockpos, ((Entity) ((Object) this)));
                }

                this.distanceWalkedModified = (float) ((double) this.distanceWalkedModified
                        + ((double) MathHelper.sqrt_double((d12 * d12) + (d14 * d14)) * 0.6D));
                this.distanceWalkedOnStepModified = (float) ((double) this.distanceWalkedOnStepModified
                        + ((double) MathHelper.sqrt_double((d12 * d12) + (d13 * d13) + (d14 * d14)) * 0.6D));
                if ((this.distanceWalkedOnStepModified > (float) this.nextStepDistance)
                        && (block1.getMaterial() != Material.air)) {
                    this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float f = MathHelper.sqrt_double((this.motionX * this.motionX * 0.20000000298023224D)
                                + (this.motionY * this.motionY) + (this.motionZ * this.motionZ * 0.20000000298023224D))
                                * 0.35F;
                        if (f > 1.0F) {
                            f = 1.0F;
                        }

                        this.playSound(this.getSwimSound(), f,
                                1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F));
                    }

                    this.playStepSound(blockpos, block1);
                }
            }

            try {
                this.doBlockCollisions();
            } catch (Throwable var52) {
                CrashReport crashreport = CrashReport.makeCrashReport(var52, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport
                        .makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = this.isWet();
            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
                this.dealFireDamage(1);
                if (!flag2) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }

            if (flag2 && (this.fire > 0)) {
                this.playSound("random.fizz", 0.7F, 1.6F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F));
                this.fire = -this.fireResistance;
            }

            this.worldObj.theProfiler.endSection();
        }
    }
}
