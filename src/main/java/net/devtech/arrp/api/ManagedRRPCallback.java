package net.devtech.arrp.api;

import net.devtech.arrp.util.IrremovableList;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;

import java.util.List;
import java.util.function.Function;

public interface ManagedRRPCallback {
	Function<ManagedRRPCallback[], ManagedRRPCallback> CALLBACK_FUNCTION = r -> (manager, rs) -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs, $ -> {});
		for (ManagedRRPCallback callback : r) {
			callback.insert(manager, packs);
		}
	};

	/**
	 * Register your resource pack at a lower priority than minecraft and mod resources
	 */
	Event<ManagedRRPCallback> BEFORE_VANILLA = EventFactory.createArrayBacked(ManagedRRPCallback.class, CALLBACK_FUNCTION);

	/**
	 * Register your resource pack between minecraft and mod resources
	 */
	Event<ManagedRRPCallback> BETWEEN_VANILLA_AND_MODS = EventFactory.createArrayBacked(ManagedRRPCallback.class, CALLBACK_FUNCTION);

	/**
	 * Register your resource pack between mod resources and user resources. This is similar to the BEFORE_USER event,
	 * but is always enabled and not visible in the resource pack selection screen.
	 */
	Event<ManagedRRPCallback> BETWEEN_MODS_AND_USER = EventFactory.createArrayBacked(ManagedRRPCallback.class, CALLBACK_FUNCTION);

	/**
	 * Register your resource pack at a higher priority than minecraft and mod resources
	 */
	Event<ManagedRRPCallback> AFTER_VANILLA = EventFactory.createArrayBacked(ManagedRRPCallback.class, CALLBACK_FUNCTION);

	/**
	 * you can only add resource packs to this list, you may not remove them
	 */
	void insert(ResourceManager manager, List<ResourcePack> resources);
}
