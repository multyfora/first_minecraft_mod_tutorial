package net.multyfora.first_tutor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.multyfora.first_tutor.block.ModBlocks;
import net.multyfora.first_tutor.item.ModItems;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);


    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter,RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,0.7f,200,"ruby");
        offerBlasting(exporter,RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,0.7f,100,"ruby");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS, ModBlocks.RUBY_BLOCK);


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC,ModItems.RAW_RUBY,1)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .input('#', Items.STONE)
                .input('@', ModItems.RUBY)
                .criterion(hasItem(ModItems.RAW_RUBY), conditionsFromItem(ModItems.RAW_RUBY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAW_RUBY)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE,ModBlocks.PARTICLE_ACCELERATOR,1)
                .pattern("idi")
                .pattern("gdg")
                .pattern("iii")
                .input('i', Items.IRON_INGOT)
                .input('d', Items.DIAMOND)
                .input('g', Items.GOLD_INGOT)
                .criterion(hasItem(Items.DIAMOND),conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PARTICLE_ACCELERATOR)));
    }
}
