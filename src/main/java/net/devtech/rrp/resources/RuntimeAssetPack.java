package net.devtech.rrp.resources;

import net.devtech.rrp.resources.builders.BlockModelJsonBuilder;
import net.devtech.rrp.resources.builders.BlockStateJsonBuilder;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * a resourcepack who's assets are generated during runtime
 * I don't understand block models, so you'll have to add your own jsons for those
 * <b>gasp</b>
 */
public interface RuntimeAssetPack extends ResourcePack {
	/**
	 * register an item model with the given json string as the model
	 * the id is automatically appended with "models/item" and appended
	 * with ".json"
	 *
	 * @param identifier the id of the item
	 */
	void registerItemModel(Identifier identifier, String model);

	/**
	 * register the default item model json for the item which looks like:
	 * the id is automatically appended with "models/item" and appended
	 * with ".json"
	 * <pre>
	 *     {
	 *         "parent": "item/generated",
	 *         "textures": {
	 *             "layer0": "items/item_id"
	 *         }
	 *     }
	 * </pre>
	 *
	 * @param identifier the id of the item
	 */
	void registerDefaultItemModel(Identifier identifier);

	/**
	 * register a texture under the given id, this
	 * id is automatically prepended with "textures/" and
	 * appended with ".png"
	 *
	 * @param identifier the resource id
	 * @param image the image
	 */
	void registerTexture(Identifier identifier, BufferedImage image);

	/**
	 * this registers a texture that is evaluated and converted to a byte array asynchronously
	 * it is scheduled as soon as it is registered, this method is recommended for high definition, or expensive
	 * to create textures
	 *
	 * @param identifier the id of the texture
	 * @param image the function to call for the image
	 * @see #registerTexture(Identifier, BufferedImage)
	 */
	void registerAsyncTexture(Identifier identifier, Supplier<BufferedImage> image);

	/**
	 * register a new raw resource at the raw path
	 *
	 * @deprecated use the other methods if you can
	 */
	@Deprecated
	void registerRawResource(Identifier identifier, Supplier<byte[]> supplier);

	/**
	 * register a new raw resource at the raw path
	 *
	 * @deprecated use the other methods if you can
	 */
	@Deprecated
	void registerRawStringResource(Identifier identifier, String string);

	/**
	 * register a blockstate json of a block that
	 * has no other blockstate other than it's
	 * default state
	 * <pre>
	 *     {
	 *         "variants": {
	 *             "": {
	 *                 "model": "modid:block/blockid"
	 *             }
	 *         }
	 *     }
	 * </pre>
	 *
	 * @param identifier the id of the block
	 */
	void registerDefaultBlockState(Identifier identifier);

	/**
	 * register a block model json for a block
	 * who's block model is a CubeAll model
	 * <p>
	 * created in assets/modid/models/block
	 *
	 * <pre>
	 *     {
	 *         "parent": "block/cube_all",
	 *         "textures": {
	 *             "all": "modid:blockid"
	 *         }
	 *     }
	 * </pre>
	 *
	 * @param identifier the id of the block
	 */
	void registerCubeAllBlockModel(Identifier identifier);

	/**
	 * register the default block item model
	 *
	 * <pre>
	 *     {
	 *         "parent": "tutorial:block/example_block"
	 *     }
	 * </pre>
	 * <p>
	 * created in assets/modid/models/item
	 *
	 * @param identifier register the default block item model
	 */
	void registerDefaultBlockItemModel(Identifier identifier);

	/**
	 * register a blockstate json using a blockstate builder
	 * @param identifier the id of the block
	 * @param builder the blockstate json builder
	 */
	void registerBlockState(Identifier identifier, BlockStateJsonBuilder builder);

	/**
	 * register a block model json using a blockmodel builder
	 * @param identifier the id of the block
	 * @param builder the blockmodel builder
	 */
	void registerBlockModel(Identifier identifier, BlockModelJsonBuilder builder);

	/**
	 * add a resource with a pre-made format using the java message format
	 * @see java.text.MessageFormat
	 * @param path the place to store the newly generated resource
	 * @param template the template
	 * @param args the args to pass to the message format
	 */
	void addTemplatedResource(Identifier path, String template, Object... args);
}
