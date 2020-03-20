package net.devtech.rrp.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * this callback is invoked when textures are reloaded
 */
public interface ReloadCallback {
	Event<ReloadCallback> EVENT = EventFactory.createArrayBacked(ReloadCallback.class, rs -> () -> {
		for (ReloadCallback callback : rs) {
			callback.reload();
		}
	});

	/**
	 * here you should handle reloading of textures, most of the time
	 * you wont need this if you don't rely on other assets being reloaded
	 */
	void reload();
}
