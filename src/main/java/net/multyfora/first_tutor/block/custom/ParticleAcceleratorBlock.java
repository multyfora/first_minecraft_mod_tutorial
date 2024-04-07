package net.multyfora.first_tutor.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.multyfora.first_tutor.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.ToIntFunction;

public class ParticleAcceleratorBlock extends FacingBlock {
    public static final MapCodec<ParticleAcceleratorBlock> CODEC = ParticleAcceleratorBlock.createCodec(ParticleAcceleratorBlock::new);
    //public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
    //if I don't declare this it's the same. it uses the facing from FacingBlock class
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    public static BlockPos nextBlockPos;


    public ParticleAcceleratorBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
        this.setDefaultState((this.stateManager.getDefaultState()).with(LIT, false));
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    public static BlockState setLit(BlockState state, boolean lit) {
        return state.with(LIT, lit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }


    /**
     * gets the next block depending on where the block is facing
     */
    public static BlockPos getNextBlockPos(BlockPos pos, BlockState state) {
        BlockPos nextBlockPos = null;
        if(!state.getOrEmpty(FACING).equals(Optional.empty())) {
            if (state.get(FACING).equals(Direction.NORTH)) {
                nextBlockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
            } else if (state.get(FACING).equals(Direction.EAST)) {
                nextBlockPos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
            } else if (state.get(FACING).equals(Direction.SOUTH)) {
                nextBlockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            } else if (state.get(FACING).equals(Direction.WEST)) {
                nextBlockPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
            } else if (state.get(FACING).equals(Direction.UP)) {
                nextBlockPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            } else if (state.get(FACING).equals(Direction.DOWN)) {
                nextBlockPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            }
        }
        return nextBlockPos;
    }

    public static BlockPos addBlockPos(BlockState state) {
        BlockPos nextBlockPos = null;
        if(state.get(FACING).equals(Direction.NORTH)){
            nextBlockPos = new BlockPos(0,0,-1);
        } else if (state.get(FACING).equals(Direction.EAST)) {
            nextBlockPos = new BlockPos(1,0,0);
        } else if (state.get(FACING).equals(Direction.SOUTH)) {
            nextBlockPos = new BlockPos(0,0,1);
        } else if (state.get(FACING).equals(Direction.WEST)) {
            nextBlockPos = new BlockPos(-1,0,0);
        } else if (state.get(FACING).equals(Direction.UP)) {
            nextBlockPos = new BlockPos(0,1,0);
        } else if (state.get(FACING).equals(Direction.DOWN)) {
            nextBlockPos = new BlockPos(0,-1,0);
        }

        return nextBlockPos;
    }



    public static BlockState getState(World world, BlockPos pos){
        return world.getBlockState(pos);
    }

    public static boolean isNextBlockPA(BlockPos pos, BlockState state, World world){
        return world.getBlockState(getNextBlockPos(pos,state)).getBlock().equals(ModBlocks.PARTICLE_ACCELERATOR.getDefaultState().getBlock());
                //world.getBlockState(getNextBlockPos(pos,state)).getOrEmpty(FACING).equals(ModBlocks.PARTICLE_ACCELERATOR.getDefaultState().getOrEmpty(FACING));
        //if the block is the same and they are facing the same way
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,LIT);
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }
}
