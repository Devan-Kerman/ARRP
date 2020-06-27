package net.devtech.arrp.api;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

import net.devtech.arrp.impl.RuntimeResourcePackImpl;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.devtech.arrp.util.CallableFunction;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

/**
 * a resource pack who's assets and data are evaluated at runtime
 */
public interface RuntimeResourcePack extends ResourcePack {
	/**
	 * create a new runtime resource pack with the default supported resource pack version
	 */
	static RuntimeResourcePack create(String id) {
		return new RuntimeResourcePackImpl(new Identifier(id));
	}

	static RuntimeResourcePack create(String id, int version) {
		return new RuntimeResourcePackImpl(new Identifier(id), version);
	}

	static Identifier id(String string) {return new Identifier(string);}

	static Identifier id(String namespace, String string) {return new Identifier(namespace, string);}

	/**
	 * reads, clones, and recolors the texture at the given path, and puts the newly created image in the given id.
	 *
	 * <b>if your resource pack is registered at a higher priority than where you expect the texture to be in, mc will be unable to find the asset you are looking for</b>
	 *
	 * @param identifier the place to put the new texture
	 * @param target the input stream of the original texture
	 * @param pixel the pixel recolorer
	 */
	void addRecoloredImage(Identifier identifier, InputStream target, IntUnaryOperator pixel);

	/**
	 * add a lang file for the given language
	 * <p>
	 * ex. addLang(id("mymod:en_us", lang().translate("something.something", "test"))
	 */
	byte[] addLang(Identifier identifier, JLang lang);

	/**
	 * adds a loot table
	 */
	byte[] addLootTable(Identifier identifier, JLootTable table);

	/**
	 * adds an async resource, this is evaluated off-thread, this does not hold all resource retrieval unlike
	 *
	 * @see #async(Consumer)
	 */
	Future<byte[]> addAsyncResource(ResourceType type, Identifier identifier, CallableFunction<Identifier, byte[]> data);

	/**
	 * add a resource that is lazily evaluated
	 */
	void addLazyResource(ResourceType type, Identifier path, BiFunction<RuntimeResourcePack, Identifier, byte[]> data);

	/**
	 * add a raw resource
	 */
	byte[] addResource(ResourceType type, Identifier path, byte[] data);

	/**
	 * add a clientside resource
	 */
	byte[] addAsset(Identifier path, byte[] data);

	/**
	 * add a serverside resource
	 */
	byte[] addData(Identifier path, byte[] data);

	/**
	 * add a model, Items should go in item/... and Blocks in block/... ex. mymod:items/my_item ".json" is automatically appended to the path
	 */
	byte[] addModel(JModel model, Identifier path);

	/**
	 * adds a blockstate json
	 * <p>
	 * ".json" is automatically appended to the path
	 */
	byte[] addBlockState(JState state, Identifier path);

	/**
	 * adds a texture png
	 * <p>
	 * ".png" is automatically appended to the path
	 */
	byte[] addTexture(Identifier id, BufferedImage image);

	/**
	 * adds an animation json
	 * <p>
	 * ".png.mcmeta" is automatically appended to the path
	 */
	byte[] addAnimation(Identifier id, JAnimation animation);

	/**
	 * add a tag under the id
	 * <p>
	 * ".json" is automatically appended to the path
	 */
	byte[] addTag(Identifier id, JTag tag);

	/**
   * add a recipe
	 * <p>
   * ".json" is automatically appended to the path
	 *
	 * @param id the {@linkplain Identifier} identifier of the recipe and that represents its directory
	 * @param recipe the recipe to add
	 * @return the new resource
	 */
	byte[] addRecipe(Identifier id, JRecipe recipe);

	/**
	 * invokes the action on the RRP executor, RRPs are thread-safe
	 * you can create expensive assets here, all resources are blocked
	 * until all async tasks are completed
	 * invokes the action on the RRP executor, RRPs are thread-safe you can create expensive assets here, all resources are blocked until all async tasks are completed
	 * <p>
	 * calling an this function from itself will result in a infinite loop
	 *
	 * @see #addAsyncResource(ResourceType, Identifier, CallableFunction)
	 */
	Future<?> async(Consumer<RuntimeResourcePack> action);

	/**
	 * forcefully dump all assets and data
	 */
	void dump();
}
