package net.devtech.stated.client.resources.items;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.awt.image.BufferedImage;

/**
 * an item with a texture, so u don't need to make an extra file
 */
public interface ItemResourceable {
	/**
	 * the json model of the item
	 * @param identifier the resource location
	 */
	@NotNull
	String getModel(Identifier identifier);
}
