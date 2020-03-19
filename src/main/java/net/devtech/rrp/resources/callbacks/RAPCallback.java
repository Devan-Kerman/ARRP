package net.devtech.rrp.resources.callbacks;

import net.devtech.rrp.resources.RuntimeAssetPack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * this event is called when the asset pack is reloaded
 * here you should add any resource providers you need
 */
public interface RAPCallback {
	Event<RAPCallback> EVENT = EventFactory.createArrayBacked(RAPCallback.class, l -> p -> {
		for (RAPCallback callback : l) {
			callback.register(p);
		}
	});

	void register(RuntimeAssetPack pack);
}
