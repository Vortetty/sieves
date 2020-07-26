package io.vortetty.sieves.entity;

import io.vortetty.sieves.block.SieveBlock;
import io.vortetty.sieves.mixin.BlockSoundGroupAccessor;
import io.vortetty.sieves.registry.Entities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SieveBlockEntity extends BlockEntity implements BlockEntityClientSerializable, SidedInventory {

    private Item sievingItem = Items.AIR;
    private int sieveProgress = 0;

    public SieveBlockEntity() {
        super(Entities.SIEVE);
    }

    public ActionResult interact(PlayerEntity player, int sieveAmount) {
        ActionResult result = ActionResult.CONSUME;

        // attempt to set the current item being sieved
        if(sievingItem == Items.AIR) {
            Item playerItem = player.getMainHandStack().getItem();
            Identifier itemID = Registry.ITEM.getId(playerItem);

            // see if the player's main hand stack can be sieved by this sieve
            if (((SieveBlock) getCachedState().getBlock()).getSieveEntry().getLootTables().containsKey(itemID)) {
                sievingItem = playerItem;

                // decrement player's held stack
                ItemStack newStack = player.getMainHandStack().copy();
                newStack.decrement(1);
                player.setStackInHand(Hand.MAIN_HAND, newStack);

                result = ActionResult.SUCCESS;
            }
        }

        // sieve contains item, further process
        if(sievingItem != Items.AIR) {
            sieve(sieveAmount);

            // play sound effect
            if(sievingItem instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) sievingItem;
                Block block = blockItem.getBlock();
                BlockState defaultState = block.getDefaultState();
                SoundEvent breakSound = ((BlockSoundGroupAccessor) block.getSoundGroup(defaultState)).getBreakSound();
                player.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), breakSound, SoundCategory.BLOCKS, 1, 1);
            }

            // check if the sieve has finished
            if(canFinishSieving()) {
                // spawn sieve drops/results in world
                spawnDrops(getDropsFromConfig(sievingItem));

                // reset progress
                clearSievingList();
                setSieveProgress(0);
            }

            result = ActionResult.SUCCESS;
        }

        // update data to client
        sendClientSync();
        return result;
    }

    public Item getSievingItem() {
        return sievingItem;
    }

    /**
     * Syncs NBT data to client.
     * markDirty does not need to be called.
     */
    private void sendClientSync() {
        if (world != null) {
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    /**
     * Returns whether this {@link SieveBlockEntity} is finished sieving its currently held item.
     * <p>
     * Internally, 100+ progress counts as "finished". External mods that increment this counter should work within a maximum value of 100.
     *
     * @return whether or not this sieve has finished sieving its currently held item
     */
    private boolean canFinishSieving() {
        return getSieveProgress() >= 100;
    }

    /**
     * Returns a list of item drops for the given item from the config file.
     * If the item doesn't have any associated drops, an empty list is returned.
     * @param item item to get a drop list for
     * @return drop list from loot table
     */
    private ArrayList<ItemStack> getDropsFromConfig(Item item) {
        Identifier lootID = ((SieveBlock) getCachedState().getBlock()).getSieveEntry().getLootTables().get(Registry.ITEM.getId(item));

        if (lootID != null) {
            return getDrops(lootID);
        }

        return new ArrayList<>();
    }

    /**
     * Retrieves the loot table drops for the given Identifier.
     * If no loot table exists at the given Identifier, an empty list is returned.
     * @param identifier loot table Identifier
     * @return list of drops generated from the loot table
     */
    private ArrayList<ItemStack> getDrops(Identifier identifier) {
        ArrayList<ItemStack> output = new ArrayList<>();

        if (world != null && !world.isClient) {
            // set up loot objects
            LootTable supplier = Objects.requireNonNull(world.getServer()).getLootManager().getTable(identifier);
            LootContext.Builder builder =
                    new LootContext.Builder((ServerWorld) world)
                            .random(world.random)
                            .parameter(LootContextParameters.POSITION, pos);

            // build & add loot to output
            List<ItemStack> stacks = supplier.generateLoot(builder.build(LootContextTypes.CHEST));
            output.addAll(stacks);
        }

        return output;
    }

    void spawnDrops(List<ItemStack> drops) {
        for (ItemStack stack : drops) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1.25, pos.getZ(), stack);
            if (world != null) {
                world.spawnEntity(itemEntity);
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("sievingStatus", sieveProgress);
        tag.putString("sievingItem", Registry.ITEM.getId(sievingItem).toString());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        sieveProgress = tag.getInt("sievingStatus");
        sievingItem = Registry.ITEM.get(new Identifier(tag.getString("sievingItem")));
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        fromTag(this.getCachedState(), compoundTag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        return toTag(compoundTag);
    }

    public int getSieveProgress() {
        return sieveProgress;
    }

    public void setSieveProgress(int status) {
        this.sieveProgress = status;
    }

    public void sieve(int amount) {
        sieveProgress += amount;
    }

    public void clearSievingList() {
        sievingItem = Items.AIR;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[1];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return sievingItem == Items.AIR;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return sievingItem != Items.AIR;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return sievingItem == Items.AIR;
    }

    @Override
    public ItemStack getStack(int slot) {
        return new ItemStack(sievingItem);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack ret = new ItemStack(sievingItem);
        sievingItem = Items.AIR;
        return ret;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack ret = new ItemStack(sievingItem);
        sievingItem = Items.AIR;
        return ret;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        sievingItem = stack.getItem();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        sievingItem = Items.AIR;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        sync();
    }
}
