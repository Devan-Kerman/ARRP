package net.devtech.arrp;

import java.util.logging.Logger;

import net.devtech.arrp.api.RRPPreGenEntrypoint;
import net.devtech.arrp.impl.RuntimeResourcePackImpl;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ARRP implements PreLaunchEntrypoint {
	private static final Logger LOGGER = Logger.getLogger("ARRP");

	@Override
	public void onPreLaunch() {
		LOGGER.severe("I used the json to destroy the json");
		FabricLoader loader = FabricLoader.getInstance();
		for (RRPPreGenEntrypoint entrypoint : loader.getEntrypoints("rrp:pregen", RRPPreGenEntrypoint.class)) {
			RuntimeResourcePackImpl.EXECUTOR_SERVICE.submit(entrypoint::pregen);
		}
	}
}
