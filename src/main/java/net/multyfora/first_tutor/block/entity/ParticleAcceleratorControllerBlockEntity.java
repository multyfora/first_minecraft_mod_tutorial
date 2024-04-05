package net.multyfora.first_tutor.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.multyfora.first_tutor.block.ModBlocks;
import net.multyfora.first_tutor.screen.GemPolishingScreenHandler;
import net.multyfora.first_tutor.screen.ParticleAcceleratorControllerScreenHandler;
import org.jetbrains.annotations.Nullable;

public class ParticleAcceleratorControllerBlockEntity  extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;


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
        craft();
    }

    public static void craft(){

    }

        //FIXME !!!!!!!!!!!!!!!! write the crafting logic
}
