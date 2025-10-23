package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.ntrdeal.echoedremnants.misc.EchoedHolder;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, EchoedHolder {
    @Unique private static final TrackedData<Boolean> ECHOED = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique private static final TrackedData<Boolean> VIBRATED = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private int vibratedTicks = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void ntrdeal$trackEchoed(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(ECHOED, false);
        builder.add(VIBRATED, false);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$pendant(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.SONIC_BOOM) && Functions.wearingPendant(this)) cir.setReturnValue(false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void ntrdeal$saveData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("vibratedTicks", this.vibratedTicks);
        System.out.println("Nbt vibratedTicks: " + nbt.getInt("vibratedTicks") + " | Code vibrationTicks: " + this.vibratedTicks);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void ntrdeal$readData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("vibratedTicks")) this.vibratedTicks = nbt.getInt("vibratedTicks");
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void ntrdeal$tickVibrated(CallbackInfo ci) {
        this.tickEchoed();
    }

    @Override
    public void ntrdeal$setEchoed(boolean echoed) {
        this.dataTracker.set(ECHOED, echoed);
    }

    @Override
    public void ntrdeal$setVibrated(boolean vibrated) {
        this.dataTracker.set(VIBRATED, vibrated);
    }

    @Override
    public boolean ntrdeal$getEchoed() {
        return this.dataTracker.get(ECHOED);
    }

    @Override
    public boolean ntrdeal$getVibrated() {
        return this.dataTracker.get(VIBRATED);
    }

    @Override
    public void ntrdeal$setVibratedTicks(int ticks) {
        this.vibratedTicks = ticks;
    }

    @Unique private void tickEchoed() {
        if (!this.getWorld().isClient()) {
            if (!Functions.wearingPendant(this)) {
                boolean echoed = Functions.hasEcho(this);
                boolean vibrated = this.ntrdeal$getVibrated();
                if (ntrdeal$getEchoed() != echoed) ntrdeal$setEchoed(echoed);
                if (this.vibratedTicks > 0) {
                    if (!vibrated) this.ntrdeal$setVibrated(true);
                    this.vibratedTicks--;
                } else if (vibrated != echoed) this.ntrdeal$setVibrated(echoed);
            } else {
                if (this.ntrdeal$getEchoed()) this.ntrdeal$setEchoed(false);
                if (this.ntrdeal$getVibrated()) this.ntrdeal$setVibrated(false);
            }
        }
    }
}
