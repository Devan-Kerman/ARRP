package net.devtech.arrp.mixin;

import net.devtech.arrp.api.RRPCallback;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin (ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {
	@Inject (method = "beginMonitoredReload",
	         at = @At ("HEAD"))
	private void registerRDP(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReloadMonitor> cir) {
		RRPCallback.EVENT.invoker().insert(packs);
	}
}