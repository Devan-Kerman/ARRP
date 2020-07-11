package net.devtech.arrp.impl;

import static java.lang.String.valueOf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.devtech.arrp.util.CallableFunction;
import net.devtech.arrp.util.CountingInputStream;
import net.devtech.arrp.util.UnsafeByteArrayOutputStream;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;


/**
 * @see RuntimeResourcePack
 */
public class RuntimeResourcePackImpl implements RuntimeResourcePack, ResourcePack {
	public static final ExecutorService EXECUTOR_SERVICE;
	public static final boolean DUMP;
	public static final boolean DEBUG_PERFORMANCE;

	// @formatter:off
	private static final Gson GSON = new GsonBuilder()
									 .setPrettyPrinting()
									 .disableHtmlEscaping()
									 .registerTypeAdapter(JMultipart.class, new JMultipart.Serializer())
									 .registerTypeAdapter(JWhen.class, new JWhen.Serializer())
									 .registerTypeAdapter(JState.class, new JState.Serializer())
									 .registerTypeAdapter(JVariant.class, new JVariant.Serializer())
									 .registerTypeAdapter(JTextures.class, new JTextures.Serializer())
									 .registerTypeAdapter(JAnimation.class, new JAnimation.Serializer())
									 .registerTypeAdapter(JFunction.class, new JFunction.Serializer())
									 .registerTypeAdapter(JPool.class, new JPool.Serializer())
									 .registerTypeAdapter(JPattern.class, new JPattern.Serializer())
									 .registerTypeAdapter(JKeys.class, new JKeys.Serializer())
									 .registerTypeAdapter(JIngredient.class, new JIngredient.Serializer())
									 .registerTypeAdapter(JIngredients.class, new JIngredients.Serializer())
									 .create();
	// @formatter:on
	private static final Logger LOGGER = Logger.getLogger("RRP");

	static {
		Properties properties = new Properties();
		int processors = Math.max(Runtime.getRuntime()
		                                 .availableProcessors() / 2 - 1, 1);
		boolean dump = false;
		boolean performance = false;
		properties.setProperty("threads", valueOf(processors));
		properties.setProperty("dump assets", "false");
		properties.setProperty("debug performance", "false");

		File file = new File("config/rrp.properties");
		try (FileReader reader = new FileReader(file)) {
			properties.load(reader);
			processors = Integer.parseInt(properties.getProperty("threads"));
			dump = Boolean.parseBoolean(properties.getProperty("dump assets"));
			performance = Boolean.parseBoolean(properties.getProperty("debug performance"));
		} catch (Throwable t) {
			LOGGER.warning("Invalid config, creating new one!");
			file.getParentFile()
			    .mkdirs();
			try (FileWriter writer = new FileWriter(file)) {
				properties.store(writer, "number of threads RRP should use for generating resources");
			} catch (IOException ex) {
				LOGGER.severe("Unable to write to RRP config!");
				ex.printStackTrace();
			}
		}
		EXECUTOR_SERVICE = Executors.newFixedThreadPool(processors);
		DUMP = dump;
		DEBUG_PERFORMANCE = performance;
	}

	public final int packVersion;
	private final Identifier id;
	private final Lock waiting = new ReentrantLock();
	private final Map<Identifier, Supplier<byte[]>> data = new ConcurrentHashMap<>();
	private final Map<Identifier, Supplier<byte[]>> assets = new ConcurrentHashMap<>();

	public RuntimeResourcePackImpl(Identifier id) {
		this(id, 5);
	}

	public RuntimeResourcePackImpl(Identifier id, int version) {
		this.packVersion = version;
		this.id = id;
	}

	@Override
	public void addRecoloredImage(Identifier identifier, InputStream target, IntUnaryOperator operator) {
		this.addLazyResource(ResourceType.CLIENT_RESOURCES, fix(identifier, "textures", "png"), (i, r) -> {
			try {

				// optimize buffer allocation, input and output image after recoloring should be roughly the same size
				CountingInputStream is = new CountingInputStream(target);
				// repaint image
				BufferedImage base = ImageIO.read(is);
				BufferedImage recolored = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
				for (int y = 0; y < base.getHeight(); y++) {
					for (int x = 0; x < base.getWidth(); x++) {
						recolored.setRGB(x, y, operator.applyAsInt(base.getRGB(x, y)));
					}
				}
				// write image
				UnsafeByteArrayOutputStream baos = new UnsafeByteArrayOutputStream(is.bytes());
				ImageIO.write(recolored, "png", baos);
				return baos.getBytes();
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public byte[] addLang(Identifier identifier, JLang lang) {
		return this.addAsset(fix(identifier, "lang", "json"), serialize(lang.getLang()));
	}

	@Override
	public byte[] addLootTable(Identifier identifier, JLootTable table) {
		return this.addData(fix(identifier, "loot_tables", "json"), serialize(table));
	}

	@Override
	public Future<byte[]> addAsyncResource(ResourceType type, Identifier path, CallableFunction<Identifier, byte[]> data) {
		Future<byte[]> future = EXECUTOR_SERVICE.submit(() -> data.get(path));
		this.getSys(type)
		    .put(path, () -> {
			    try {
				    return future.get();
			    } catch (InterruptedException | ExecutionException e) {
				    throw new RuntimeException(e);
			    }
		    });
		return future;
	}

	@Override
	public void addLazyResource(ResourceType type, Identifier path, BiFunction<RuntimeResourcePack, Identifier, byte[]> func) {
		this.getSys(type)
		    .put(path, new Supplier<byte[]>() {
			    private byte[] data;

			    @Override
			    public byte[] get() {
				    if (this.data == null) {
					    this.data = func.apply(RuntimeResourcePackImpl.this, path);
				    }
				    return this.data;
			    }
		    });
	}


	@Override
	public byte[] addResource(ResourceType type, Identifier path, byte[] data) {
		this.getSys(type)
		    .put(path, () -> data);
		return data;
	}

	@Override
	public byte[] addAsset(Identifier path, byte[] data) {
		return this.addResource(ResourceType.CLIENT_RESOURCES, path, data);
	}

	@Override
	public byte[] addData(Identifier path, byte[] data) {
		return this.addResource(ResourceType.SERVER_DATA, path, data);
	}

	@Override
	public byte[] addModel(JModel model, Identifier path) {
		return this.addAsset(fix(path, "models", "json"), serialize(model));
	}

	@Override
	public byte[] addBlockState(JState state, Identifier path) {
		return this.addAsset(fix(path, "blockstates", "json"), serialize(state));
	}

	@Override
	public byte[] addTexture(Identifier id, BufferedImage image) {
		UnsafeByteArrayOutputStream ubaos = new UnsafeByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", ubaos);
		} catch (IOException e) {
			throw new RuntimeException("impossible.", e);
		}
		return this.addAsset(fix(id, "textures", "png"), ubaos.getBytes());
	}

	@Override
	public byte[] addAnimation(Identifier id, JAnimation animation) {
		return this.addAsset(fix(id, "textures", "png.mcmeta"), serialize(animation));
	}

	@Override
	public byte[] addTag(Identifier id, JTag tag) {
		return this.addData(fix(id, "tags", "json"), serialize(tag));
	}

	@Override
	public byte[] addRecipe(Identifier id, JRecipe recipe) {
	    return this.addData(fix(id, "recipes", "json"), serialize(recipe));
	}

	@Override
	public Future<?> async(Consumer<RuntimeResourcePack> action) {
		this.lock();
		return EXECUTOR_SERVICE.submit(() -> {
			action.accept(this);
			this.waiting.unlock();
		});
	}

	private void lock() {
		if (!this.waiting.tryLock()) {
			if (DEBUG_PERFORMANCE) {
				long start = System.currentTimeMillis();
				this.waiting.lock();
				long end = System.currentTimeMillis();
				LOGGER.warning("waited " + (end - start) + "ms for lock in RRP: " + this.id);
			} else {
				this.waiting.lock();
			}
		}
	}

	@Override
	public void dump() {
		LOGGER.info("dumping " + this.id + "'s assets and data");
		// data dump time
		File folder = new File("rrp.debug/" + this.id.toString()
		                                             .replace(':', ';') + "/");
		File assets = new File(folder, "assets");
		assets.mkdirs();
		for (Map.Entry<Identifier, Supplier<byte[]>> entry : this.assets.entrySet()) {
			this.write(assets,
			           entry.getKey(),
			           entry.getValue()
			                .get()
			);
		}

		File data = new File(folder, "data");
		data.mkdir();
		for (Map.Entry<Identifier, Supplier<byte[]>> entry : this.data.entrySet()) {
			this.write(data,
			           entry.getKey(),
			           entry.getValue()
			                .get()
			);
		}

	}

	private static byte[] serialize(Object object) {
		UnsafeByteArrayOutputStream ubaos = new UnsafeByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(ubaos);
		GSON.toJson(object, writer);
		try {
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ubaos.getBytes();
	}

	private static Identifier fix(Identifier identifier, String prefix, String append) {
		return new Identifier(identifier.getNamespace(), prefix + '/' + identifier.getPath() + '.' + append);
	}

	private Map<Identifier, Supplier<byte[]>> getSys(ResourceType side) {
		return side == ResourceType.CLIENT_RESOURCES ? this.assets : this.data;
	}

	/**
	 * pack.png and that's about it I think/hope
	 *
	 * @param fileName the name of the file, can't be a path tho
	 * @return the pack.png image as a stream
	 */
	@Override
	public InputStream openRoot(String fileName) {
		if (!fileName.contains("/") && !fileName.contains("\\")) {
			return ARRP.class.getResourceAsStream("/resource/" + fileName);
		} else {
			throw new IllegalArgumentException("File name can't be a path");
		}
	}

	@Override
	public InputStream open(ResourceType type, Identifier id) {
		this.lock();
		Supplier<byte[]> supplier = this.getSys(type)
		                                .get(id);
		if (supplier == null) {
			LOGGER.warning("No resource found for " + id);
			this.waiting.unlock();
			return null;
		}
		this.waiting.unlock();
		return new ByteArrayInputStream(supplier.get());
	}


	@Override
	public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
		this.lock();
		Set<Identifier> identifiers = new HashSet<>();
		for (Identifier identifier : this.getSys(type)
		                                 .keySet()) {
			if (identifier.getNamespace()
			              .equals(namespace) && identifier.getPath()
			                                              .startsWith(prefix) && pathFilter.test(identifier.getPath())) {
				identifiers.add(identifier);
			}
		}
		this.waiting.unlock();
		return identifiers;
	}

	@Override
	public boolean contains(ResourceType type, Identifier id) {
		this.lock();
		boolean contains = this.getSys(type)
		                       .containsKey(id);
		this.waiting.unlock();
		return contains;
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		this.lock();
		Set<String> namespaces = new HashSet<>();
		for (Identifier identifier : this.getSys(type)
		                                 .keySet()) {
			namespaces.add(identifier.getNamespace());
		}
		this.waiting.unlock();
		return namespaces;
	}

	// if it works, don't touch it
	@Override
	public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) {
		if (metaReader.getKey()
		              .equals("pack")) {
			JsonObject object = new JsonObject();
			object.addProperty("pack_format", this.packVersion);
			object.addProperty("description", "runtime resource pack");
			return metaReader.fromJson(object);
		}
		LOGGER.info("'" + metaReader.getKey() + "' is an unsupported metadata key!");
		return metaReader.fromJson(new JsonObject());
	}

	@Override
	public String getName() {
		return "Runtime Resource Pack" + this.id;
	}

	@Override
	public void close() {
		LOGGER.warning("closing rrp " + this.id);

		// lock
		this.lock();
		if (DUMP) {
			this.dump();
		}

		// unlock
		this.waiting.unlock();
	}

	private void write(File dir, Identifier identifier, byte[] data) {
		try {
			File file = new File(dir, identifier.getPath());
			file.getParentFile()
			    .mkdirs();
			FileOutputStream output = new FileOutputStream(file);
			output.write(data);
			output.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
