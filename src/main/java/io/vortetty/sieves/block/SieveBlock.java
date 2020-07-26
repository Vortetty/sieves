package io.vortetty.sieves.block;

import io.vortetty.sieves.entity.SieveBlockEntity;
import io.vortetty.sieves.config.SieveEntry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SieveBlock extends BlockWithEntity implements InventoryProvider {

    private final SieveEntry sieveEntry;
    private static final VoxelShape SHAPE;

    static {
        VoxelShape top = Block.createCuboidShape(0.0D, 11.0D, 0.0D, 16.0D, 14.0D, 16.0D);
        VoxelShape bottom = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);
        SHAPE = VoxelShapes.union(top, bottom);
    }

    public SieveBlock(Settings settings, SieveEntry sieveEntry) {
        super(settings);
        this.sieveEntry = sieveEntry;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new SieveBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(hand == Hand.MAIN_HAND && !world.isClient) {
            SieveBlockEntity sieveBlockEntity = (SieveBlockEntity) world.getBlockEntity(pos);

            if(sieveBlockEntity != null) {
                return sieveBlockEntity.interact(player, 10);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        super.onPlaced(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1);
    }

    public SieveEntry getSieveEntry() {
        return sieveEntry;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return ((SieveBlockEntity) world.getBlockEntity(pos));
    }
}
