package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.SidedManagedRRPCallback;
import net.minecraft.resource.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;

@Mixin(LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");
	
	@ModifyVariable(
			method = "<init>",
			at = @At( // after empty super()
					value = "INVOKE",
					target = "Ljava/lang/Object;<init>()V",
					shift = At.Shift.AFTER
			),
			argsOnly = true
	)
	private List<ResourcePack> registerARRPs(List<ResourcePack> packs, ResourceType type, List<ResourcePack> packs0) throws ExecutionException, InterruptedException {
		List<ResourcePack> copy = new ArrayList<>(packs);
		ARRP.waitForPregen();
		ARRP_LOGGER.info("ARRP register - before vanilla");
		SidedManagedRRPCallback.BEFORE_VANILLA.invoker().insert((ResourceManager) this, type, Lists.reverse(copy));

		OptionalInt optionalInt = IntStream.range(0, copy.size()).filter(i -> copy.get(i).getName().equals("fabric")).findFirst();

		if (optionalInt.isPresent()) {
			ARRP_LOGGER.info("ARRP register - between vanilla and mods");
			int initialCopyLength = copy.size();
			SidedManagedRRPCallback.BETWEEN_VANILLA_AND_MODS.invoker().insert((ResourceManager) this, type, copy.subList(0, optionalInt.getAsInt()));
			ARRP_LOGGER.info("ARRP register - between mods and user");
			int finalCopyLength = copy.size();
			SidedManagedRRPCallback.BETWEEN_MODS_AND_USER.invoker().insert((ResourceManager) this, type, copy.subList(0, optionalInt.getAsInt()+1+(finalCopyLength-initialCopyLength)));
		}
		
		ARRP_LOGGER.info("ARRP register - after vanilla");
		SidedManagedRRPCallback.AFTER_VANILLA.invoker().insert((ResourceManager) this, type, copy);
		return copy;
	}
}