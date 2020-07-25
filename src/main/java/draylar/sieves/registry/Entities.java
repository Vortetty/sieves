package draylar.sieves.registry;

import draylar.sieves.entity.SieveBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Entities {

    public static final BlockEntityType<SieveBlockEntity> SIEVE = register("sieve", BlockEntityType.Builder.create(SieveBlockEntity::new, SievesContent.SIEVE_LIST.toArray(new Block[0])).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> be) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("sieves", name), be);
    }

    public static void init() {

    }
}
