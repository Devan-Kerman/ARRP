package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;

@Mixin (LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");

	@ModifyVariable(method = "<init>",
			at = @At (value = "HEAD"),
			argsOnly = true)
	private static List<ResourcePack> registerARRPs(List<ResourcePack> packs) throws ExecutionException, InterruptedException {
		ARRP.waitForPregen();

		ARRP_LOGGER.info("ARRP register - before vanilla");
		List<ResourcePack> before = new ArrayList<>();
		RRPCallback.BEFORE_VANILLA.invoker().insert(before);

		before.addAll(packs);

		ARRP_LOGGER.info("ARRP register - after vanilla");
		List<ResourcePack> after = new ArrayList<>();
		RRPCallback.AFTER_VANILLA.invoker().insert(after);

		before.addAll(after);


		return before;
	}
}