package io.vortetty.sieves;

import io.vortetty.sieves.client.renderer.SieveBlockEntityRenderer;
import io.vortetty.sieves.registry.SievesContent;
import io.vortetty.sieves.registry.Entities;
import grondag.jmx.api.RetexturedModelBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class SieveModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(Entities.SIEVE, SieveBlockEntityRenderer::new);

        SievesContent.SIEVE_LIST.forEach(sieve -> {
            BlockRenderLayerMap.INSTANCE.putBlock(sieve, RenderLayer.getCutout());
        });

        // re-texture sieves
        SievesContent.SIEVE_LIST.forEach(sieve -> {
            RetexturedModelBuilder.builder("sieves:template_sieve", "sieves:" + sieve.getSieveEntry().getId() + "_sieve")
                    .mapSprite("block/oak_planks", sieve.getSieveEntry().getLegsTexture().toString())
                    .mapSprite("block/oak_log", sieve.getSieveEntry().getTopTexture().toString())
                    .mapSprite("block/cobweb", sieve.getSieveEntry().getNetTexture().toString())
                    .completeBlockWithItem();
        });
    }
}
