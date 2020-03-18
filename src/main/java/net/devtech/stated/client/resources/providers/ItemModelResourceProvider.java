package net.devtech.stated.client.resources.providers;

import net.devtech.stated.client.io.StringInputStream;
import net.devtech.stated.client.resources.ResourceProvider;
import net.devtech.stated.client.resources.items.ItemResourceable;
import net.minecraft.util.Identifier;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ItemModelResourceProvider implements ResourceProvider {
	private final Map<Identifier, ItemResourceable> items;

	public ItemModelResourceProvider(Map<Identifier, ItemResourceable> items) {this.items = items;}

	@Override
	public boolean contains(Identifier id) {
		String path = id.getPath();
		if ((path.endsWith(".json") && path.startsWith("models/item"))) {
			return this.items.containsKey(id);
		}
		return false;
	}


	@Override
	public InputStream get(Identifier id) throws IOException {
		String path = id.getPath();
		if (path.endsWith(".json") && path.startsWith("models/item")) {
			String model = this.items.get(id).getModel(id);
			return new StringInputStream(model);
		}
		return null;
	}
}
