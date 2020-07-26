package io.vortetty.sieves.registry;

import io.vortetty.sieves.SieveMod;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class Items {

    public static final Item SIEVE_NET = register("sieve_net", new Item(new Item.Settings().group(SieveMod.GROUP)));
    public static final Item REINFORCED_SIEVE_NET = register("reinforced_sieve_net", new Item(new Item.Settings().group(SieveMod.GROUP)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, SieveMod.id(name), item);
    }

    public static void init() {
        // NO-OP
    }

    private Items() {
        // NO-OP
    }
}
