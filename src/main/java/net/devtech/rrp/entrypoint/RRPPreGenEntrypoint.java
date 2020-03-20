package net.devtech.rrp.entrypoint;

/**
 * this is called before the RRP pregen stage,
 * here all resources are generated on their own thread
 * so while the game is initializing, you can generate
 * your textures, using this would eliminate most of the overhead
 * involved with generating your textures/resources on the fly
 * not all resources can be generated at PreLaunch however,
 * so you may still register your resources in your normal mod
 * init, or any other stage
 */
public interface RRPPreGenEntrypoint {
	/**
	 * register anything you want to pregenerate in this method to
	 * @see net.devtech.rrp.api.RuntimeResourcePack#INSTANCE
	 */
	void register();
}
