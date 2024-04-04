package net.multyfora.first_tutor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.multyfora.first_tutor.block.ModBlocks;
import net.multyfora.first_tutor.block.entity.ModBlockEntities;
import net.multyfora.first_tutor.entity.ModEntities;
import net.multyfora.first_tutor.entity.custom.PorcupineEntity;
import net.multyfora.first_tutor.item.ModItemGroups;
import net.multyfora.first_tutor.item.ModItems;
import net.multyfora.first_tutor.screen.ModScreenHandlers;
import net.multyfora.first_tutor.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstTutor implements ModInitializer {
	public static final String MOD_ID = "first_tutor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModLootTableModifiers.modifyLootTables();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerScreenHandlers();

		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
	}
}