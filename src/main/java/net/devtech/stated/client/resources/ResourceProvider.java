package net.devtech.stated.client.resources;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.InputStream;

public interface ResourceProvider {
	boolean contains(Identifier id);
	@Nullable
	InputStream get(Identifier id) throws IOException;
}
