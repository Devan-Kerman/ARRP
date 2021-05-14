package net.devtech.arrp.api;

import java.util.List;

import net.devtech.arrp.util.IrremovableList;

import net.minecraft.resource.ResourcePack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RRPCallback {
	Event<RRPCallback> BEFORE_VANILLA = EventFactory.createArrayBacked(RRPCallback.class, r -> rs -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs, $ -> {});
		for (RRPCallback callback : r) {
			callback.insert(packs);
		}
	});

	Event<RRPCallback> AFTER_VANILLA = EventFactory.createArrayBacked(RRPCallback.class, r -> rs -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs, $ -> {});
		for (RRPCallback callback : r) {
			callback.insert(packs);
		}
	});

	/**
	 * @deprecated use {@link #BEFORE_VANILLA} instead
	 */
	@Deprecated
	Event<RRPCallback> EVENT = AFTER_VANILLA;

	/**
	 * you can only add resource packs to this list, you may not remove them
	 */
	void insert(List<ResourcePack> resources);
}
