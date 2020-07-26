package io.vortetty.sieves;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vortetty.sieves.config.IdentifierDeserializer;
import io.vortetty.sieves.registry.SievesContent;
import io.vortetty.sieves.registry.Entities;
import io.vortetty.sieves.config.SievesConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class SieveMod implements ModInitializer {

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Identifier.class, new IdentifierDeserializer()).create();
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Items.SAND));
	public static final SievesConfig CONFIG = AutoConfig.register(SievesConfig.class, (definition, configClass) -> new GsonConfigSerializer<>(definition, configClass, GSON)).getConfig();

	@Override
	public void onInitialize() {
		SievesContent.init();
		Entities.init();
		io.vortetty.sieves.registry.Items.init();
	}

	public static Identifier id(String name) {
		return new Identifier("sieves", name);
	}
}
