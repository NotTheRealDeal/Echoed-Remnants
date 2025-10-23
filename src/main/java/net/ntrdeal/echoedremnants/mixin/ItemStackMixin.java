package net.ntrdeal.echoedremnants.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.item.ItemStack;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack {
    @ModifyVariable(method = "getTooltip", at = @At("STORE"), ordinal = 0)
    private int ntrdeal$hideEchoed(int original) {
        int modifier = 0;
        if (this.contains(ModDataComponents.ECHOED_FOOD)) modifier--;
        return original + modifier;
    }
}