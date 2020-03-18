package net.devtech.stated.client.resources;

import net.devtech.stated.Stated;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class RuntimeResourcePack implements ResourcePack {
	private static final Logger LOGGER = Logger.getLogger("RuntimeResourcePack");
	private final ResourceProvider[] providers;

	public RuntimeResourcePack(ResourceProvider... providers) {
		this.providers = providers;
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
			return Stated.class.getResourceAsStream("/resource/" + fileName);
		} else throw new IllegalArgumentException("File name can't be a path");
	}

	@Override
	public InputStream open(ResourceType type, Identifier id) throws IOException {
		if (type == ResourceType.CLIENT_RESOURCES) {
			for (ResourceProvider provider : this.providers) {
				if (provider.contains(id)) {
					InputStream stream = provider.get(id);
					if (stream != null) return stream;
				}
			}
			LOGGER.warning("No resource found for " + id);
		}
		return null;
	}

	@Override
	public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
		return Collections.emptyList();
	}

	@Override
	public boolean contains(ResourceType type, Identifier id) {
		if (type == ResourceType.CLIENT_RESOURCES) {
			for (ResourceProvider provider : this.providers) {
				if (provider.contains(id)) return true;
			}
			LOGGER.warning("No resource found for " + id);
		}
		return false;
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		Set<String> meta = new HashSet<>();
		meta.add(Stated.MOD_ID);
		return meta;
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
	public String getName() {
		return "Half";
	}

	@Override
	public void close() throws IOException {
		// already handled
	}
}