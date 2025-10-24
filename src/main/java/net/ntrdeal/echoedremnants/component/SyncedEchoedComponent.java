package net.ntrdeal.echoedremnants.component;

import net.fabricmc.api.EnvType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.ModItems;
import net.ntrdeal.echoedremnants.item.component.EchoedFoodComponent;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class SyncedEchoedComponent implements Component, AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    private final LivingEntity entity;

    private int duration = 0;
    private int delay = 0;
    private int vibratingTicks = 0;
    private boolean echoed = false;
    private boolean vibrating = false;
    private boolean wearingPendant = false;
    private boolean wearingMonocle = false;

    public SyncedEchoedComponent(LivingEntity entity) {
        this.entity = entity;
    }

    public void setFromComponent(EchoedFoodComponent component) {
        if (this.delay <= 0) {
            if (component.delay() <= 0) this.delay = 1;
            else this.delay = component.delay();

            this.duration = component.duration();
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.duration = nbt.getInt("duration");
        this.delay = nbt.getInt("delay");
        this.vibratingTicks = nbt.getInt("vibratingTicks");
        this.echoed = nbt.getBoolean("echoed");
        this.vibrating = nbt.getBoolean("vibrating");
        this.wearingPendant = nbt.getBoolean("wearingPendant");
        this.wearingMonocle = nbt.getBoolean("wearingMonocle");
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putInt("duration", this.duration);
        nbt.putInt("delay", this.delay);
        nbt.putInt("vibratingTicks", this.vibratingTicks);
        nbt.putBoolean("echoed", this.echoed);
        nbt.putBoolean("vibrating", this.vibrating);
        nbt.putBoolean("wearingPendant", this.wearingPendant);
        nbt.putBoolean("wearingMonocle", this.wearingMonocle);
    }

    private void tick(EnvType environment) {
        if (environment == EnvType.SERVER) {
            this.wearingPendant = this.entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.PENDANT);
            this.wearingMonocle = this.entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.MONOCLE);
            this.echoed = !this.wearingPendant && this.entity.hasStatusEffect(ModEffects.ECHOED);
            this.vibrating = !this.wearingPendant && (this.vibratingTicks > 0 || this.echoed);
            ModComponents.ECHOED.sync(this.entity);
        }

        if (this.wearingPendant) {
//            if (this.entity.hasStatusEffect(ModEffects.ECHOED)) this.entity.removeStatusEffect(ModEffects.ECHOED);
            if (this.delay > 0) this.delay = 0;
            if (this.vibratingTicks > 0) this.vibratingTicks = 0;
            if (this.duration > 0) this.duration = 0;
        }

        if (this.vibratingTicks > 0) {
            this.vibratingTicks--;
        }

        if (this.delay > 0) {
            if (this.delay == 1) {
                this.entity.addStatusEffect(new StatusEffectInstance(ModEffects.ECHOED, this.duration));
                this.duration = 0;
            }
            this.delay--;
        }
    }

    @Override
    public void serverTick() {
        tick(EnvType.SERVER);
    }

    @Override
    public void clientTick() {
        tick(EnvType.CLIENT);
    }

    public boolean echoed() {
        return this.echoed;
    }

    public boolean vibrating() {
        return this.vibrating;
    }

    public boolean wearingPendant() {
        return this.wearingPendant;
    }

    public boolean wearingMonocle() {
        return this.wearingMonocle;
    }

    public void setVibratingTicks(int ticks) {
        if (!this.wearingPendant) this.vibratingTicks = ticks;
    }
}
