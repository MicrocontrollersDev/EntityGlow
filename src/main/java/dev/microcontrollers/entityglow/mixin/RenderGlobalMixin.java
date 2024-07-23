package dev.microcontrollers.entityglow.mixin;

import dev.microcontrollers.entityglow.config.EntityGlowConfig;
import dev.microcontrollers.entityglow.ducks.EntityDuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class RenderGlobalMixin implements EntityDuck {
    @Shadow
    @Final
    private Minecraft mc;

    @Redirect(method = "isRenderEntityOutlines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
    private boolean entityglow$removeSpectatorCheck(EntityPlayerSP instance) {
        if (EntityGlowConfig.entityGlow) return true;
        return instance.isSpectator();
    }

    @Redirect(method = "isRenderEntityOutlines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    private boolean entityglow$removeKeyCheck(KeyBinding instance) {
        if (EntityGlowConfig.entityGlow) return true;
        return instance.isKeyDown();
    }

    @Redirect(method = "renderEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldRenderInPass(I)Z", ordinal = 1))
    private boolean entityglow$forceInPass(Entity instance, int pass, Entity renderViewEntity, ICamera camera, float partialTicks) {
        if (pass == 1) return true;
        else return instance.shouldRenderInPass(pass);
    }

    @Redirect(method = "renderEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isInRangeToRender3d(DDD)Z", ordinal = 1))
    private boolean entityglow$forceRenderRange(Entity instance, double x, double y, double z, Entity renderViewEntity, ICamera camera, float partialTicks) {
        return instance.isInRangeToRender3d(x, y, z) && entityglow$isOutlineActive(instance, renderViewEntity, camera);
    }

    @Unique
    private boolean entityglow$isOutlineActive(Entity entityIn, Entity viewer, ICamera camera) {
        boolean flag = viewer instanceof EntityLivingBase && ((EntityLivingBase)viewer).isPlayerSleeping();
        if (entityIn == viewer && this.mc.gameSettings.thirdPersonView == 0 && !flag) {
            return false;
        } else if (((EntityDuck) entityIn).entityglow$isGlowing() && EntityGlowConfig.entityGlow) {
            return true;
        } else {
            return this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown() && entityIn instanceof EntityPlayer
                    ? entityIn.ignoreFrustumCheck
                    || camera.isBoundingBoxInFrustum(entityIn.getEntityBoundingBox())
                    || entityIn.isRiding()
                    : false;
        }
    }
}
