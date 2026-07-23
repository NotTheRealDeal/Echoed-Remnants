package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.ntrdeal.echoedremnants.component.echolocation.EcholocationComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder, ItemInstance {
    @Shadow public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> type, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag);

    @WrapOperation(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/PatchedDataComponentMap;size()I"))
    private int ntrdeal$hideEchoed(
            PatchedDataComponentMap map, Operation<Integer> original,
            Item.TooltipContext context, TooltipDisplay display, @Nullable Player player
    ) {
        return !this.has(ModDataComponents.ECHOED_FOOD) || EcholocationComponent.isActive(player) ? original.call(map) : original.call(map) - 1;
    }

    @Inject(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 0))
    private void ntrdeal$addEchoed(
            Item.TooltipContext context, TooltipDisplay display, @Nullable Player player,
            TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci
    ) {
        if (EcholocationComponent.isActive(player)) this.addToTooltip(ModDataComponents.ECHOED_FOOD, context, display, builder, tooltipFlag);
    }
}
