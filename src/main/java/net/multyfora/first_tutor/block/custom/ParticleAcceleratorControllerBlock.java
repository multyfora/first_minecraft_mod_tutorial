package net.multyfora.first_tutor.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.multyfora.first_tutor.FirstTutor;
import net.multyfora.first_tutor.block.entity.ModBlockEntities;
import net.multyfora.first_tutor.block.entity.ParticleAcceleratorControllerBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ParticleAcceleratorControllerBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING = FacingBlock.FACING;

    public ParticleAcceleratorControllerBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ParticleAcceleratorControllerBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ParticleAcceleratorControllerBlockEntity) {
                ItemScatterer.spawn(world, pos, (ParticleAcceleratorControllerBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public static BlockPos getNextBlockPos(BlockPos pos, BlockState state) {
        BlockPos nextBlockPos = null;
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

        return nextBlockPos;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((ParticleAcceleratorControllerBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.PARTICLE_ACCELERATOR_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}