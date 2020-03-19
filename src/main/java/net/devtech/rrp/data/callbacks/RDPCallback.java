package net.devtech.rrp.data.callbacks;

import net.devtech.rrp.data.RuntimeDatapack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RDPCallback {
	Event<RDPCallback> EVENT = EventFactory.createArrayBacked(RDPCallback.class, l -> p -> {
		for (RDPCallback callback : l) {
			callback.register(p);
		}
	});

	void register(RuntimeDatapack pack);
}
