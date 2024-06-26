package net.multyfora.first_tutor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.multyfora.first_tutor.block.ModBlocks;
import net.multyfora.first_tutor.entity.ModEntities;
import net.multyfora.first_tutor.entity.client.ModModelLayers;
import net.multyfora.first_tutor.entity.client.PorcupineModel;
import net.multyfora.first_tutor.entity.client.PorcupineRenderer;
import net.multyfora.first_tutor.screen.GemPolishingScreen;
import net.multyfora.first_tutor.screen.ModScreenHandlers;
import net.multyfora.first_tutor.screen.ParticleAcceleratorControllerScreen;

public class FirstTutorClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);

        HandledScreens.register(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, GemPolishingScreen::new);
        HandledScreens.register(ModScreenHandlers.PARTICLE_ACCELERATOR_SCREEN_HANDLER, ParticleAcceleratorControllerScreen::new);
    }
}
