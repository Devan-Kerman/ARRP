package net.devtech.rrp;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.devtech.rrp.entrypoint.RRPPreGenEntrypoint;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.entrypoint.minecraft.hooks.EntrypointUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * the entrypoint used for pre-generating textures, the pregen entrypoints
 * invoked here so textures can be generated as the game first starts up
 */
public class RRPPre implements PreLaunchEntrypoint {
	public static final AtomicBoolean PRE_GEN_LOCK = new AtomicBoolean(true);

	@Override
	public void onPreLaunch() {
		RuntimeResourcePack.EXECUTOR_SERVICE.submit(() -> {
			EntrypointUtils.invoke("rrp_pre", RRPPreGenEntrypoint.class, RRPPreGenEntrypoint::register);
			PRE_GEN_LOCK.set(false);
		});
	}
}
