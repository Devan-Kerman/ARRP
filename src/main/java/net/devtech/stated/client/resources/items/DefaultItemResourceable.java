package net.devtech.stated.client.resources.items;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import java.awt.image.BufferedImage;

/**
 * represents an item with one texture, and one texture only
 */
public interface DefaultItemResourceable extends ItemResourceable {
	@NotNull
	@Override
	default String getModel(Identifier identifier) {
		String modid = identifier.getNamespace();
		String myId = identifier.getPath();
		// last path seperator
		int from = myId.lastIndexOf('/') + 1;
		// last file extension
		int to = myId.lastIndexOf('.');
		return String.format("{\"parent\":\"item/generated\", \"textures\": {\"layer0\":\"%s:item/%s\"}}", modid, myId.substring(from, to));
	}
}
