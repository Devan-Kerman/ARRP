package net.devtech.rrp.data;

import net.devtech.rrp.data.callbacks.RDPCallback;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import java.util.Map;

import static net.minecraft.resource.ResourcePackProfile.InsertionPosition.BOTTOM;

public class RuntimeDataPackProvider implements net.minecraft.resource.ResourcePackProvider {
	@Override
	public <T extends ResourcePackProfile> void register(Map<String, T> registry, ResourcePackProfile.Factory<T> factory) {
		registry.put("RPP_server", ResourcePackProfile.of("RPP_server", false, () -> {
			System.out.println("Registering runtime datapack");
			RuntimeDatapackImpl runtimeDatapack = new RuntimeDatapackImpl();
			RDPCallback.EVENT.invoker().register(runtimeDatapack);
			return runtimeDatapack;
		}, factory, BOTTOM));
	}
}
