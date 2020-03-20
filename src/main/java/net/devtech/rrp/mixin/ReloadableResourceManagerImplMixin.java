package net.devtech.rrp.mixin;

import net.devtech.rrp.RRPPre;
import net.devtech.rrp.api.RuntimeResourcePack;
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

	@Inject (method = "beginMonitoredReload",
	         at = @At ("HEAD"))
	private void registerRDP(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReloadMonitor> cir) {
		LOGGER.info("loading/reloading runtime data pack");
		if (RRPPre.PRE_GEN_LOCK.get()) LOGGER.warn("Pre-Datapacks were not generated in time! Usually not a big deal, but if you have more cores you can add extra threads in the config to speed up the load process for mods that use RRP");
		// @formatter:off
		while (RRPPre.PRE_GEN_LOCK.get()); // waiting
		// @formatter:on
		packs.add(1, RuntimeResourcePack.INSTANCE);
	}

}
