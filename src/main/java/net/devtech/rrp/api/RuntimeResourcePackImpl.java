package net.devtech.rrp.api;

import com.google.gson.Gson;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;
import net.devtech.rrp.RRP;
import net.devtech.rrp.util.BlockModelJsonBuilder;
import net.devtech.rrp.util.BlockStateJsonBuilder;
import net.devtech.rrp.util.recipies.RecipeChoice;
import net.devtech.rrp.util.recipies.ShapedRecipe;
import net.devtech.rrp.util.recipies.ShapelessRecipe;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.lang.String.format;

public class RuntimeResourcePackImpl implements RuntimeResourcePack {
	private static final Gson GSON = new Gson();
	private static final Logger LOGGER = Logger.getLogger("RuntimeResourcePack");
	private final ExecutorService service;
	public final Map<Identifier, Supplier<byte[]>> registered = new HashMap<>();
	public final Set<String> namespaces = new HashSet<>();

	public RuntimeResourcePackImpl(ExecutorService service) {this.service = service;}

	static int maxThreads() {
		File config = new File("config/RuntimeRP.properties");
		Properties properties = new Properties();
		try {
			if (!config.exists()) {
				if (!config.getParentFile().exists() && !config.getParentFile().mkdirs()) throw new IOException("Unable to create config file : " + config);
				properties.setProperty("service_threads", "2");
				properties.store(new FileWriter(config), "how many threads to use for generating resources");

			} else {
				properties.load(new FileReader(config));
			}

			return Integer.parseInt(properties.getProperty("service_threads"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NumberFormatException e) {
			LOGGER.severe("Invalid config!! delete the config and relaunch the game, it will create a config for you");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addRawResource(Identifier identifier, Supplier<byte[]> supplier) {
		this.registered.put(identifier, supplier);
		this.namespaces.add(identifier.getNamespace());
	}

	@Override
	public void addTemplatedResource(Identifier path, String template, Object... args) {
		this.addRawStringResource(path, String.format(template, args));
	}

	@Override
	public void addAsyncAsset(Identifier path, Callable<byte[]> assetMaker) {
		Future<byte[]> array = this.service.submit(assetMaker);
		this.addRawResource(path, () -> {
			try {
				return array.get();
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.severe("Fatal error when drawing or serializing image!");
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void addRawStringResource(Identifier identifier, String data) {
		byte[] bytes = data.getBytes();
		this.addRawResource(identifier, () -> bytes);
	}

	@Override
	public void addBlockModel(Identifier identifier, BlockModelJsonBuilder builder) {
		this.addRawStringResource(fix(identifier, "models/block", "json"), builder.build());
	}

	@Override
	public void addLangFile(String modId, String language, Map<String, String> translations) {
		this.addRawStringResource(fix(new Identifier(modId, language), "lang", "json"), GSON.toJson(translations));
	}


	@Override
	public void addBlockState(Identifier identifier, BlockStateJsonBuilder builder) {
		this.addRawStringResource(fix(identifier, "blockstates", "json"), builder.build());
	}

	@Override
	public void addDefaultBlockState(Identifier identifier) {
		this.addRawStringResource(fix(identifier, "blockstates", "json"), format("{\"variants\":{\"\":{\"model\":\"%s:block/%s\"}}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void addDefaultBlockItemModel(Identifier identifier) {
		this.addItemModel(identifier, format("{\"parent\": \"%s:block/%s\"}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void addCubeAllBlockModel(Identifier identifier) {
		this.addRawStringResource(fix(identifier, "models/block", "json"), format("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"%s:block/%s\"}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void addItemModel(Identifier identifier, String model) {
		this.addRawStringResource(fix(identifier, "models/item", "json"), model);
	}

	@Override
	public void addDefaultItemModel(Identifier identifier) {
		this.addItemModel(identifier, format("{\"parent\":\"item/generated\", \"textures\": {\"layer0\":\"%s:item/%s\"}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void addTexture(Identifier identifier, BufferedImage image) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			byte[] data = out.toByteArray();
			this.addRawResource(fix(identifier, "textures", "png"), () -> data);
		} catch (IOException e) {
			throw new RuntimeException("Fatal error in serializing image", e);
		}
	}

	@Override
	public void addAsyncTexture(Identifier identifier, Supplier<BufferedImage> image) {
		this.addAsyncAsset(fix(identifier, "textures", "png"), () -> {
			BufferedImage img = image.get();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			return out.toByteArray();
		});
	}

	@Override
	public void addDefaultBlockLootTable(Identifier block, Identifier item) {
		this.addTemplatedResource(fix(block, "loot_tables/blocks", "json"), "{\"type\":\"minecraft:block\",\"pools\":[{\"rolls\":1,\"entries\":[{\"type\":\"minecraft:item\",\"name\":\"%s\"}],\"conditions\":[{\"condition\":\"minecraft" +
		                                                                    ":survives_explosion\"}]}]}", item);
	}

	@Override
	public void addAggregateBlockLootTable(Identifier block, Identifier piece, int max, int min) {
		this.addTemplatedResource(fix(block, "loot_tables/blocks", "json"), getTemplate("aggregate"), block, min, max, piece);
	}

	@Override
	public void addCobbleBlockLootTable(Identifier block, Identifier blockItem, Identifier cobble) {
		this.addTemplatedResource(fix(block, "loot_tables/blocks", "json"), getTemplate("cobble"), blockItem, cobble);
	}

	@Override
	public void addLootTable(Identifier identifier, String table) {
		this.addRawStringResource(fix(identifier, "loot_tables", "json"), table);
	}

	@Override
	public void addShaped9x9CraftingRecipe(Identifier id, Identifier result, int amount, @Nullable RecipeChoice... choices) {
		if (Objects.requireNonNull(choices, "the array can't be null, but the ingredients can").length > 9) throw new IllegalArgumentException("Recipie cannot be larger than 9");
		this.addRawStringResource(fix(id, "recipes", "json"), createShapedRecipeJson(choices, result, amount, 3));
	}

	@Override
	public void addShaped4x4CraftingRecipe(Identifier id, Identifier result, int amount, @Nullable RecipeChoice... choices) {
		if (Objects.requireNonNull(choices, "the array can't be null, but the ingredients can").length > 4) throw new IllegalArgumentException("Recipie cannot be larger than 4");
		this.addRawStringResource(fix(id, "recipes", "json"), createShapedRecipeJson(choices, result, amount, 2));
	}



	@Override
	public void addShapelessRecipe(Identifier id, Identifier result, int amount, @NotNull RecipeChoice... choices) {
		if (Objects.requireNonNull(choices, "the array can't be null, but the ingredients can").length > 9) throw new IllegalArgumentException("Recipie cannot be larger than 9");
		String json = GSON.toJson(new ShapelessRecipe(choices, result, amount));
		this.addRawStringResource(fix(id, "recipes", "json"), json);
	}

	private static char[] keyChars = "ABCDEFGHI".toCharArray();

	private static Map<RecipeChoice, Character> computeKeys(RecipeChoice[] choices) {
		Map<RecipeChoice, Character> keys = new HashMap<>();
		int index = 0;
		for (RecipeChoice choice : choices) {
			if (!keys.containsKey(choice)) keys.put(choice, keyChars[index++]);
		}
		return keys;
	}

	private static String createShapedRecipeJson(RecipeChoice[] choices, Identifier result, int amount, int height) {
		Map<RecipeChoice, Character> keys = computeKeys(choices);
		Map<String, RecipeChoice> jsonKeys = new HashMap<>();
		for (Map.Entry<RecipeChoice, Character> entry : keys.entrySet()) {
			jsonKeys.put(entry.getValue().toString(), entry.getKey());
		}
		return GSON.toJson(new ShapedRecipe(jsonKeys, result, amount, createPattern(keys, choices, height)));
	}

	private static String[] createPattern(Map<RecipeChoice, Character> keys, RecipeChoice[] choices, int height) {
		String[] array = new String[height];
		Arrays.fill(array, "");
		for (int i = 0; i < height*height; i++) {
			RecipeChoice choice = i < choices.length ? choices[i] : null;
			if (choice != null) array[i / height] += keys.get(choices[i]);
			else array[i / height] += ' ';
		}
		return array;
	}


	private static String getTemplate(String temp) {
		try (InputStream stream = RRP.class.getResourceAsStream("/templates/" + temp + ".temp")) {
			return new String(read(stream));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return "Runtime Resource Pack";
	}

	protected static Identifier fix(Identifier identifier, String prefix, String append) {
		return new Identifier(identifier.getNamespace(), prefix + '/' + identifier.getPath() + '.' + append);
	}

	/**
	 * pack.png and that's about it I think/hope
	 *
	 * @param fileName the name of the file, can't be a path tho
	 * @return the pack.png image as a stream
	 * @throws IOException when I forget to remove the / before building the jar
	 */
	@Override
	public InputStream openRoot(String fileName) throws IOException {
		if (!fileName.contains("/") && !fileName.contains("\\")) {
			return RRP.class.getResourceAsStream("/resource/" + fileName);
		} else throw new IllegalArgumentException("File name can't be a path");
	}

	/**
	 * open a stream for the given data
	 */
	@Override
	public InputStream open(ResourceType type, Identifier id) throws IOException {
		Supplier<byte[]> supplier = this.registered.get(id);
		if (supplier == null) {
			LOGGER.warning("No resource found for " + id);
			return null;
		}
		return new ByteArrayInputStream(supplier.get());
	}

	@Override
	public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
		List<Identifier> identifiers = new ArrayList<>();
		for (Identifier identifier : this.registered.keySet()) {
			if (identifier.getNamespace().equals(namespace)) {
				if (identifier.getPath().startsWith(prefix)) {
					if (pathFilter.test(identifier.getPath())) {
						identifiers.add(identifier);
					}
				}
			}
		}
		return identifiers;
	}

	@Override
	public boolean contains(ResourceType type, Identifier id) {
		return this.registered.containsKey(id);
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		return this.namespaces;
	}

	@Override
	public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
		try {
			InputStream inputStream = this.openRoot("pack.mcmeta");
			Throwable var3 = null;

			Object var4;
			try {
				var4 = AbstractFileResourcePack.parseMetadata(metaReader, inputStream);
			} catch (Throwable var14) {
				var3 = var14;
				throw var14;
			} finally {
				if (inputStream != null) {
					if (var3 != null) {
						try {
							inputStream.close();
						} catch (Throwable var13) {
							var3.addSuppressed(var13);
						}
					} else {
						inputStream.close();
					}
				}

			}

			return (T) var4;
		} catch (FileNotFoundException | RuntimeException var16) {
			return null;
		}
	}

	protected static final ThreadLocal<byte[]> CACHES = ThreadLocal.withInitial(() -> new byte[1024]);

	protected static byte[] read(InputStream stream) throws IOException {
		ByteList list = new ByteArrayList(stream.available());
		byte[] cache = CACHES.get();
		int read;
		while ((read = stream.read(cache)) != -1) {
			list.addElements(list.size(), cache, 0, read);
		}
		stream.close();
		return list.toByteArray();
	}

	@Override
	public void close() throws IOException {}
}
