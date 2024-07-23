package dev.microcontrollers.entityglow.mixin;

import dev.microcontrollers.entityglow.ducks.EntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {
    @Shadow
    protected abstract boolean getFlag(int flag);
    @Shadow
    public World worldObj;

    @Shadow
    protected abstract void setFlag(int flag, boolean set);

    @Unique
    private boolean entityglow$glowing;

    @Override
    public boolean entityglow$isGlowing() {
        return this.entityglow$glowing || this.worldObj.isRemote && this.getFlag(6);
    }

    @Unique
    public void entityglow$setGlowing(boolean glowingIn) {
        this.entityglow$glowing = glowingIn;
        if (!this.worldObj.isRemote) {
            this.setFlag(6, this.entityglow$glowing);
        }
    }

    @Inject(method = "readFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;)Z", shift = At.Shift.BEFORE))
    private void entityglow$setGlowingState(NBTTagCompound tagCompund, CallbackInfo ci) {
        entityglow$setGlowing(tagCompund.getBoolean("Glowing"));
    }
}