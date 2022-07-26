package net.devtech.arrp.mixin;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Lists;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.SidedRRPCallback;
import net.devtech.arrp.util.IrremovableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;

@Mixin (LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");

	@Inject(method = "<init>",
			at = @At (value = "HEAD"))
	private static void registerARRPs(ResourceType type, List<ResourcePack> packs, CallbackInfo ci) throws ExecutionException, InterruptedException {
		ARRP.waitForPregen();
		ARRP_LOGGER.info("ARRP register - before vanilla");
		SidedRRPCallback.BEFORE_VANILLA.invoker().insert(type, Lists.reverse(packs));

		ARRP_LOGGER.info("ARRP register - after vanilla");
		SidedRRPCallback.AFTER_VANILLA.invoker().insert(type, packs);
	}
}