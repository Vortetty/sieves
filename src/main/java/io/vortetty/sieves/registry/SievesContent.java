package io.vortetty.sieves.registry;

import io.vortetty.sieves.SieveMod;
import io.vortetty.sieves.block.SieveBlock;
import io.vortetty.sieves.config.SieveEntry;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.loot.JLootTable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static net.devtech.arrp.json.loot.JLootTable.loot;
import static net.devtech.arrp.json.loot.JLootTable.pool;

public class SievesContent {

    private static final Block TEMPLATE_SIEVE = registerBlock("template_sieve", new SieveBlock(FabricBlockSettings.of(Material.WOOD), null));
    private static final Item TEMPLATE_SIEVE_ITEM = registerItem("template_sieve", new BlockItem(TEMPLATE_SIEVE, new Item.Settings()));
    public static final List<SieveBlock> SIEVE_LIST = new ArrayList<>();
    public static final RuntimeResourcePack loot_table_pack = RuntimeResourcePack.create("sieves:loot_tables");

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
            SieveBlock block = new SieveBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakByHand(true).hardness(2).resistance(3), entry);
            registerBlock(entry.getId() + "_sieve", block);
            registerItem(entry.getId() + "_sieve", block);
            loot_table_pack.addLootTable(RuntimeResourcePack.id("sieves:"+entry.getId()+"_sieve"),loot("minecraft:block").pool(pool().rolls(1).entry(JLootTable.entry().type("minecraft:item").name("sieves:"+entry.getId()+"_sieve"))));
            SIEVE_LIST.add(block);
        }
        RRPCallback.EVENT.register(a -> a.add(loot_table_pack));
    }
}
