package net.devtech.rrp.api;

import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * a resource pack who's assets/data is generated at runtime
 */
public interface RuntimeResourcePack extends ResourcePack, RuntimeDatapack, RuntimeAssetPack {
	/**
	 * the executor used for generating
	 */
	ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(0, RuntimeResourcePackImpl.maxThreads(), 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

	/**
	 * this is the runtime resource pack instance, you may
	 * reference this field any time you want at runtime
	 * and register resources at any point, however you should
	 * register assets <b>before</b> they are queried
	 * (unless you know what you're doing), so
	 * your mod init is fine, or the RRPre init works as well
	 */
	RuntimeResourcePack INSTANCE = new RuntimeResourcePackImpl(EXECUTOR_SERVICE);

	/**
	 * add a raw resource, this is like an artificial file
	 *
	 * @param identifier the id of the file
	 * @param data the byte data
	 */
	void addRawResource(Identifier identifier, Supplier<byte[]> data);

	/**
	 * @see #addRawResource(Identifier, Supplier)
	 */
	void addRawStringResource(Identifier identifier, String data);

	/**
	 * add a resource with a pre-made format using the java string.format format
	 *
	 * @param path the place to store the newly generated resource
	 * @param template the template
	 * @param args the args to pass to the message format
	 * @see String#format(String, Object...)
	 */
	void addTemplatedResource(Identifier path, String template, Object... args);

	/**
	 * this adds a texture that is evaluated and converted to a byte array asynchronously
	 * it is scheduled as soon as it is added
	 *
	 * @param path the path to the texture
	 * @param assetMaker the function to call for the asset
	 */
	void addAsyncResource(Identifier path, Callable<byte[]> assetMaker);
}
