package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.devtech.arrp.api.RRPCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;

@Mixin (ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
	private static final Logger LOGGER = LogManager.getLogger("ARRP");

	@Inject (method = "beginMonitoredReload",
			at = @At (value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
	private void registerARRPs(Executor prepareExecutor,
			Executor applyExecutor,
			CompletableFuture<Unit> initialStage,
			List<ResourcePack> packs,
			CallbackInfoReturnable<ResourceReloadMonitor> cir) {
		LOGGER.info("ARRP register - before vanilla");
		List<ResourcePack> pack = new ArrayList<>();
		RRPCallback.BEFORE_VANILLA.invoker().insert(pack);
		pack.forEach(this::addPack);
	}

	@Inject (method = "beginMonitoredReload",
			at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private void registerARRPsAfter(Executor prepareExecutor,
			Executor applyExecutor,
			CompletableFuture<Unit> initialStage,
			List<ResourcePack> packs,
			CallbackInfoReturnable<ResourceReloadMonitor> cir) {
		LOGGER.info("ARRP register - after vanilla");
		List<ResourcePack> pack = new ArrayList<>();
		RRPCallback.AFTER_VANILLA.invoker().insert(pack);
		pack.forEach(this::addPack);
	}

	@Shadow
	public abstract void addPack(ResourcePack resourcePack);
}
