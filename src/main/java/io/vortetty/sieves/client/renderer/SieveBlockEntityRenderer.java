package io.vortetty.sieves.client.renderer;

import io.vortetty.sieves.entity.SieveBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SieveBlockEntityRenderer extends BlockEntityRenderer<SieveBlockEntity> {

    public SieveBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SieveBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Item sievingItem = entity.getSievingItem();
        int sievingStatus = entity.getSieveProgress();

        // get stack for rendering
        ItemStack stack = new ItemStack(sievingItem);
        matrices.push();

        // 0 progress: 1 multiplier
        // 100 progress: 0 multiplier
        // translateY = .78f at 1 multiplier
        // translatey = .69 at 0 multiplier
        float sievingMultiplier = (100 - sievingStatus) * 0.01f;
        float translateY = .78f - (.09f * (1 - sievingMultiplier));
        float scaleY = .35f * sievingMultiplier;

        // get block render coordinates
        matrices.translate(0.5F, translateY, 0.5F);
        matrices.scale(1.75f, scaleY, 1.75F);

        // render item
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers);
        matrices.pop();
    }
}
