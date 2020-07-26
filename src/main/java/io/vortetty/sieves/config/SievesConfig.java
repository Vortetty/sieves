package io.vortetty.sieves.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@Config(name = "sieves")
public class SievesConfig implements ConfigData {

    public List<SieveEntry> sieveList = new ArrayList<>();

    public SievesConfig() {
        sieveList.add(
                new SieveEntry(
                        "oak",
                        5,
                        10,
                        new Identifier("block/oak_planks"),
                        new Identifier("block/oak_planks"),
                                new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "spruce",
                        5,
                        10,
                        new Identifier("block/spruce_planks"),
                        new Identifier("block/spruce_planks"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "birch",
                        5,
                        10,
                        new Identifier("block/birch_planks"),
                        new Identifier("block/birch_planks"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "acacia",
                        5,
                        10,
                        new Identifier("block/acacia_planks"),
                        new Identifier("block/acacia_planks"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "jungle",
                        5,
                        10,
                        new Identifier("block/jungle_planks"),
                        new Identifier("block/jungle_planks"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "dark_oak",
                        5,
                        10,
                        new Identifier("block/dark_oak_planks"),
                        new Identifier("block/dark_oak_planks"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "soul",
                        5,
                        10,
                        new Identifier("block/soul_sand"),
                        new Identifier("block/soul_sand"),
                        new Identifier("sieves:item/sieve_net")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "fiery",
                        5,
                        10,
                        new Identifier("block/magma"),
                        new Identifier("block/obsidian"),
                        new Identifier("block/lava_flow")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );

        sieveList.add(
                new SieveEntry(
                        "end",
                        5,
                        10,
                        new Identifier("block/end_stone_bricks"),
                        new Identifier("block/end_stone_bricks"),
                        new Identifier("entity/end_portal")
                ).addLootTable(new Identifier("sand"), new Identifier("testdata", "sieves/sand"))
        );
    }
}
