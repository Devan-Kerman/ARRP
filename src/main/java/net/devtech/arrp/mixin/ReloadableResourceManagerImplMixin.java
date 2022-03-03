package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.devtech.arrp.api.RRPCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Unit;

@Mixin (ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");

	@ModifyVariable(method = "reload",
			at = @At (value = "HEAD"), argsOnly = true)
	private List<ResourcePack> registerARRPs(List<ResourcePack> packs, Executor prepareExecutor,
			Executor applyExecutor,
			CompletableFuture<Unit> initialStage,
			List<ResourcePack> packs0) {
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