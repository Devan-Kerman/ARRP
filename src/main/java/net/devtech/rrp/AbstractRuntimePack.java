package net.devtech.rrp;

import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

public abstract class AbstractRuntimePack implements ResourcePack {
	private static final Logger LOGGER = Logger.getLogger("RuntimeResourcePack");
	public final Map<Identifier, Supplier<byte[]>> registered = new HashMap<>();
	public final Set<String> namespaces = new HashSet<>();

	public void registerRawResource(Identifier identifier, Supplier<byte[]> supplier) {
		this.registered.put(identifier, supplier);
		this.namespaces.add(identifier.getNamespace());
	}

	public void registerTemplatedResource(Identifier path, String template, Object... args) {
		this.registerRawStringResource(path, String.format(template, args));
	}

	public void registerRawStringResource(Identifier identifier, String data) {
		byte[] bytes = data.getBytes();
		this.registerRawResource(identifier, () -> bytes);
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

	@Override
	public void close() throws IOException {}
}
