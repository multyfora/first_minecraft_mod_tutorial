package net.multyfora.first_tutor.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()){
            BlockPos positionCLicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionCLicked.getY() + 64; i++){
                BlockState state = context.getWorld().getBlockState(positionCLicked.down(i));

                if(isValueableBlock(state)){
                    outputValuableCoordinates(positionCLicked.down(i), player, state.getBlock());
                    foundBlock = true;

                    break;
                }
            }

            if(!foundBlock){
                player.sendMessage(Text.literal("no valuables found"),true);
            }

        }

        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

        return ActionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block) {

            player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at (" +
                    blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), true); //FIXME: overlay true would be cool

    }

    private boolean isValueableBlock(BlockState state) {
        return state.isOf(Blocks.DIAMOND_ORE) || state.isOf(Blocks.IRON_ORE);
    }
}
