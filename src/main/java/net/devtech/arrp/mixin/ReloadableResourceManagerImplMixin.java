package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;

import net.devtech.arrp.api.RRPCallback;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;

@Mixin (ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {
	@Shadow @Final private static Logger LOGGER;

	@ModifyVariable (method = "beginMonitoredReload", at = @At ("HEAD"), argsOnly = true)
	private List<ResourcePack> registerARRPs(List<ResourcePack> packs) {
		packs = new ArrayList<>(packs);
		LOGGER.info("ARRP register");
		RRPCallback.EVENT.invoker()
		                 .insert(packs);
		return packs;
	}
}