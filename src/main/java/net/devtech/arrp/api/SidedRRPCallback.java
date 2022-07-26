package net.devtech.arrp.api;

import java.util.List;

import net.devtech.arrp.util.IrremovableList;

import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Util;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface SidedRRPCallback {
	/**
	 * Register your resource pack at a higher priority than minecraft and mod resources, this is actually done by passing a reversed view of the
	 * resource pack list, such that List#add will actually add to the beginning of the list instead of the end.
	 */
	Event<SidedRRPCallback> BEFORE_VANILLA = EventFactory.createArrayBacked(SidedRRPCallback.class, r -> (type, rs) -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs, $ -> {
		});
		for(SidedRRPCallback callback : r) {
			callback.insert(type, packs);
		}
	});
	
	/**
	 * Register your resource pack at a lower priority than minecraft and mod resources. This is actually done by passing a view of the resource pack
	 * list, such that List#add will add to the end of the list, after default resource packs.
	 *
	 * If you want to override a vanilla resource
	 */
	Event<SidedRRPCallback> AFTER_VANILLA = EventFactory.createArrayBacked(SidedRRPCallback.class, r -> (type, rs) -> {
		IrremovableList<ResourcePack> packs = new IrremovableList<>(rs, $ -> {
		});
		for(SidedRRPCallback callback : r) {
			callback.insert(type, packs);
		}
	});
	
	/**
	 * @see LifecycledResourceManagerImpl#LifecycledResourceManagerImpl(ResourceType, List)
	 */
	void insert(ResourceType type, List<ResourcePack> resources);
	
	static Void INIT_ = Util.make(() -> {
		BEFORE_VANILLA.register((type, resources) -> RRPCallback.BEFORE_VANILLA.invoker().insert(resources));
		AFTER_VANILLA.register((type, resources) -> RRPCallback.AFTER_VANILLA.invoker().insert(resources));
		return null;
	});
}
