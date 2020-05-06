package net.devtech.arrp.api;

import net.devtech.arrp.util.IrremovableList;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resource.ResourcePack;
import java.util.List;

public interface RRPCallback {
	Event<RRPCallback> EVENT = EventFactory.createArrayBacked(RRPCallback.class, r -> rs -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs);
		for (RRPCallback callback : r) {
			callback.insert(packs);
		}
	});

	/**
	 * you can only add resource packs to this list, you may not remove them
	 */
	void insert(List<ResourcePack> resources);
}
