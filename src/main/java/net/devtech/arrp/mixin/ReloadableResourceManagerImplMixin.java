package net.devtech.arrp.mixin;

import net.devtech.arrp.api.RRPCallback;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin (ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {
	@Shadow @Final private static Logger LOGGER;

	@Inject (method = "beginMonitoredReload", at = @At ("HEAD"))
	private void registerARRPs(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReloadMonitor> cir) {
		LOGGER.info("[ARRP] reloading");
		RRPCallback.EVENT.invoker()
		                 .insert(packs);
	}
}