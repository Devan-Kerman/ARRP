package net.devtech.rrp.api;

import net.devtech.rrp.util.BlockModelJsonBuilder;
import net.devtech.rrp.util.BlockStateJsonBuilder;
import net.minecraft.util.Identifier;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * a resourcepack who's assets are generated during runtime
 * I don't understand block models, so you'll have to add your own jsons for those
 * <b>gasp</b>
 */
public interface RuntimeAssetPack {
	/**
	 * add an item model with the given json string as the model
	 * the id is automatically appended with "models/item" and appended
	 * with ".json"
	 *
	 * @param identifier the id of the item
	 */
	void addItemModel(Identifier identifier, String model);

	/**
	 * add the default item model json for the item which looks like:
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
	void addDefaultItemModel(Identifier identifier);

	/**
	 * add a texture under the given id, this
	 * id is automatically prepended with "textures/" and
	 * appended with ".png"
	 *
	 * @param identifier the resource id
	 * @param image the image
	 */
	void addTexture(Identifier identifier, BufferedImage image);

	/**
	 * this adds a texture that is evaluated and converted to a byte array asynchronously
	 * it is scheduled as soon as it is added, this method is recommended for high definition, or expensive
	 * to create textures, this method is special because drawing images usually takes a bit and converting
	 * buffered images to byte arrays is painful, so I decided
	 * to make it seperate from addAsyncAsset
	 *
	 * @param identifier the id of the texture
	 * @param image the function to call for the image
	 * @see #addTexture(Identifier, BufferedImage)
	 */
	void addAsyncTexture(Identifier identifier, Supplier<BufferedImage> image);

	/**
	 * add a blockstate json of a block that
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
	void addDefaultBlockState(Identifier identifier);

	/**
	 * add a block model json for a block
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
	void addCubeAllBlockModel(Identifier identifier);

	/**
	 * add the default block item model
	 *
	 * <pre>
	 *     {
	 *         "parent": "tutorial:block/example_block"
	 *     }
	 * </pre>
	 * <p>
	 * created in assets/modid/models/item
	 *
	 * @param identifier add the default block item model
	 */
	void addDefaultBlockItemModel(Identifier identifier);

	/**
	 * add a blockstate json using a blockstate builder
	 * @param identifier the id of the block
	 * @param builder the blockstate json builder
	 */
	void addBlockState(Identifier identifier, BlockStateJsonBuilder builder);

	/**
	 * add a block model json using a blockmodel builder
	 * @param identifier the id of the block
	 * @param builder the blockmodel builder
	 */
	void addBlockModel(Identifier identifier, BlockModelJsonBuilder builder);

	/**
	 * add a lang file for the given language
	 * @param modId the mod id
	 * @param language the language
	 * @param translations the translations
	 */
	void addLangFile(String modId, String language, Map<String, String> translations);
}
