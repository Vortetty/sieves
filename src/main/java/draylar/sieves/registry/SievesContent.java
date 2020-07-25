package draylar.sieves.registry;

import draylar.sieves.SieveMod;
import draylar.sieves.block.SieveBlock;
import draylar.sieves.config.SieveEntry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class SievesContent {

    private static final Block TEMPLATE_SIEVE = registerBlock("template_sieve", new SieveBlock(FabricBlockSettings.of(Material.WOOD), null));
    private static final Item TEMPLATE_SIEVE_ITEM = registerItem("template_sieve", new BlockItem(TEMPLATE_SIEVE, new Item.Settings()));
    public static final List<SieveBlock> SIEVE_LIST = new ArrayList<>();

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier("sieves", name), block);
    }

    private static Item registerItem(String name, Block block) {
        return Registry.register(Registry.ITEM, new Identifier("sieves", name), new BlockItem(block, new Item.Settings().group(SieveMod.GROUP)));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("sieves", name), item);
    }

    public static void init() {
        for (SieveEntry entry : SieveMod.CONFIG.sieveList) {
            SieveBlock block = new SieveBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque(), entry);
            registerBlock(entry.getId() + "_sieve", block);
            registerItem(entry.getId() + "_sieve", block);
            SIEVE_LIST.add(block);
        }
    }
}
