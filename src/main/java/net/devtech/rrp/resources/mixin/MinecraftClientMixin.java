package net.devtech.rrp.resources.mixin;

import net.devtech.rrp.resources.callbacks.RAPCallback;
import net.devtech.rrp.resources.RuntimeAssetPackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Environment(EnvType.CLIENT)
	@Redirect(method = "<init>", at = @At (value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;beginMonitoredReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/resource/ResourceReloadMonitor;"))
	public ResourceReloadMonitor test(ReloadableResourceManager manager, Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs) {
		this.load(packs);
		return manager.beginMonitoredReload(prepareExecutor, applyExecutor, initialStage, packs);
	}

	@Inject (method = "reloadResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;beginMonitoredReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/resource/ResourceReloadMonitor;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	public void reloadResources(CallbackInfoReturnable<CompletableFuture> info, CompletableFuture<java.lang.Void> cf, List<ResourcePack> list) {
		this.load(list);
	}

	private void load(List<ResourcePack> list) {
		System.out.println("loading/reloading runtime pack");
		// todo customize service
		RuntimeAssetPackImpl resourcePack = new RuntimeAssetPackImpl(Executors.newSingleThreadExecutor());
		RAPCallback.EVENT.invoker().register(resourcePack);
		list.add(resourcePack);
	}
}