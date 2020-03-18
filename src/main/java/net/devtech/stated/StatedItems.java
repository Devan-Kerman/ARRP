package net.devtech.stated;

import net.devtech.stated.client.resources.items.ItemResourceable;
import net.devtech.stated.items.Default;
import net.devtech.stated.items.Id;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StatedItems {
	public static final Map<Identifier, ItemResourceable> ITEM_RESOURCE_MAP = new HashMap<>();

	@Default
	@Id("stated:test_item")
	public static final Item TEST_ITEM = new Item(new Item.Settings().group(ItemGroup.MISC));
}
