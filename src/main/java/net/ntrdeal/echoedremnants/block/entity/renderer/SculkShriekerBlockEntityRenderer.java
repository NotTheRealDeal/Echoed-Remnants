package net.ntrdeal.echoedremnants.block.entity.renderer;

import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import net.ntrdeal.echoedremnants.block.ModProperties;

public class SculkShriekerBlockEntityRenderer implements BlockEntityRenderer<SculkShriekerBlockEntity> {

    private static final float scale = 0.6f;

    public SculkShriekerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SculkShriekerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int shards = entity.getCachedState().get(ModProperties.ECHO_SHARDS);

        if (shards >= 1) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            ItemStack stack = Items.ECHO_SHARD.getDefaultStack();

            matrices.push();
            matrices.scale(scale, scale, scale);
            matrices.translate(0f, scaled(0.7f), scaled(0.5f));

            for (int index = 0; index < shards; index++) {
                matrices.translate(scaled(0.5f), 0, scaled(0.5f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.push(); matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(45f)); matrices.translate(scaled(0.1f), 0, 0);
                itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
            }

            matrices.pop();
        }
    }

    private static float scaled(float input) {
        return input / scale;
    }
}
