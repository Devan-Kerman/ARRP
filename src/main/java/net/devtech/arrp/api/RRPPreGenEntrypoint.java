package net.devtech.arrp.api;

/**
 * an entrypoint called on preLaunch asynchronously
 */
public interface RRPPreGenEntrypoint {
	/**
	 * pregenerate assets here and put them in a runtime resource pack, don't forget to register a callback
	 *
	 * @see RRPCallback
	 */
	void pregen();
}
