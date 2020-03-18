package net.devtech.stated.client.mixin;

import net.devtech.stated.Stated;
import net.devtech.stated.StatedItems;
import net.devtech.stated.client.resources.RuntimeResourcePack;
import net.devtech.stated.client.resources.providers.ItemModelResourceProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.MetricsData;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow @Final public MetricsData metricsData;

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
		System.out.println("reloading runtime pack");
		ModNioResourcePack parentPack = null;
		for (int i = 0, size = list.size(); i < size; i++) {
			ResourcePack pack = list.get(i);
			if (pack instanceof ModNioResourcePack) {
				parentPack = ((ModNioResourcePack) pack);
				if(parentPack.getFabricModMetadata().getId().equals(Stated.MOD_ID)) {
					System.out.println("hhhh");
					break;
				}
			}
		}
		ItemModelResourceProvider provider = new ItemModelResourceProvider(StatedItems.ITEM_RESOURCE_MAP);
		list.add(0, new RuntimeResourcePack(provider)); // todo add resource providers
		System.out.println(list);
	}
}