package net.multyfora.first_tutor.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.multyfora.first_tutor.FirstTutor;
import net.multyfora.first_tutor.block.ModBlocks;

public class ModBlockEntities {

    public static final BlockEntityType<GemPolishingStationBlockEntity> GEM_POLISHING_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstTutor.MOD_ID,"gem_polishing_be"),
                    FabricBlockEntityTypeBuilder.create(GemPolishingStationBlockEntity::new,
                            ModBlocks.GEM_POLISHING_STATION).build());


    public static void registerBlockEntities(){
        FirstTutor.LOGGER.info("Registering Block entities for " + FirstTutor.MOD_ID);
    }

}
