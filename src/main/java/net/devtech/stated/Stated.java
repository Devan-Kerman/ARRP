package net.devtech.stated;

import net.devtech.stated.client.resources.items.DefaultItemResourceable;
import net.devtech.stated.items.Default;
import net.devtech.stated.items.Id;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.ModContainer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.lang.reflect.Field;
import java.util.Map;

import static net.minecraft.util.registry.Registry.ITEM;

public class Stated implements ModInitializer {
	private static final Field MOD_MAP;
	public static final String MOD_ID = "stated";
	public static net.fabricmc.loader.api.ModContainer container;
	static {
		try {
			MOD_MAP = FabricLoader.class.getDeclaredField("modMap");
			MOD_MAP.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new IllegalStateException("ohno");
		}
	}

	@Override
	public void onInitialize() {
		try {
			for (net.fabricmc.loader.api.ModContainer mod : FabricLoader.INSTANCE.getAllMods()) {
				if(mod.getMetadata().getId().equals(MOD_ID))
					container = mod;
			}
			container = ((Map<String, ModContainer>) MOD_MAP.get(FabricLoader.INSTANCE)).get(MOD_ID);
			for (Field field : StatedItems.class.getFields()) {
				Id id = field.getAnnotation(Id.class);
				if(id != null) {
					Object item = field.get(null);
					// skip item resource map
					Identifier ID = new Identifier(id.value());
					if (item instanceof Item) Registry.register(ITEM, ID, (Item) item);

					if (field.isAnnotationPresent(Default.class)) {
						StatedItems.ITEM_RESOURCE_MAP.put(new Identifier(MOD_ID, "models/item/" + ID.getPath() + ".json"), new DefaultItemResourceable() {});
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
