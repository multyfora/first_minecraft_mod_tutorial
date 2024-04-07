package net.multyfora.first_tutor.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.multyfora.first_tutor.FirstTutor;
import net.multyfora.first_tutor.block.ModBlocks;
import net.multyfora.first_tutor.block.custom.ParticleAcceleratorBlock;
import net.multyfora.first_tutor.block.custom.ParticleAcceleratorControllerBlock;
import net.multyfora.first_tutor.item.ModItems;
import net.multyfora.first_tutor.screen.GemPolishingScreenHandler;
import net.multyfora.first_tutor.screen.ParticleAcceleratorControllerScreenHandler;
import org.jetbrains.annotations.Nullable;

public class ParticleAcceleratorControllerBlockEntity  extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;

    private static boolean isCrafting = false;


    int tickCounter = 0;

    final int craftTime = 100;


    public ParticleAcceleratorControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PARTICLE_ACCELERATOR_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Particle Accelerator Controller");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ParticleAcceleratorControllerScreenHandler(syncId,playerInventory,this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return;
//        world.getPlayers().get(0).sendMessage(Text.of(tickCounter + ""));
        if(this.hasRecipe()){
//            world.getPlayers().get(0).sendMessage(Text.of("have recipe"));
            markDirty(world, pos, state);
            if(tickCounter >= craftTime) {
                if(!isCrafting) {
                    FirstTutor.LOGGER.info("calling crafting " + tickCounter);
//                    isCrafting = true;
                    this.craft(world, pos, state);
                    tickCounter = 0;
                }
            }
            tickCounter++;
        }
    }
    //TODO make the lighting smooth
    public void craft(World world, BlockPos pos, BlockState state) {

        BlockPos newpos = pos;

        FirstTutor.LOGGER.info("out of while, pos: " + newpos + " state: " + ModBlocks.PARTICLE_ACCELERATOR.getDefaultState() + " condition: " + ParticleAcceleratorBlock.isNextBlockPA(newpos,state,world));

        while(ParticleAcceleratorBlock.isNextBlockPA(newpos,state,world)){


            newpos = newpos.add(ParticleAcceleratorBlock.addBlockPos(state));

            FirstTutor.LOGGER.info("in while: " + newpos);

            world.setBlockState(newpos, ParticleAcceleratorBlock.setLit(world.getBlockState(newpos),true));

        }

        ItemStack result = new ItemStack(ModItems.RUBY);
        this.setStack(INPUT_SLOT, new ItemStack(result.getItem(), getStack(INPUT_SLOT).getCount() - 1));

    }

    private boolean hasRecipe() {
        boolean hasInput = getStack(INPUT_SLOT).getItem() == ModItems.RUBY;

        return hasInput && !isCrafting;
    }

}
//FIXME !!!!!!!!!!!!!!!! write the crafting logic
