package net.devtech.arrp.mixin;

import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resource.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Mixin(FileResourcePackProvider.class)
public class FileResourcePackProviderMixin {
	@Shadow @Final private ResourceType type;
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/FileResourcePackProviderMixin");

	@Inject(method = "register", at = @At("HEAD"))
	public void register(
		Consumer<ResourcePackProfile> adder,
		CallbackInfo ci
	) throws ExecutionException, InterruptedException {
		List<ResourcePack> list = new ArrayList<>();
		ARRP.waitForPregen();
		ARRP_LOGGER.info("ARRP register - before user");
		RRPCallback.BEFORE_USER.invoker().insert(list);

		for (ResourcePack pack : list) {
			adder.accept(ResourcePackProfile.create(
				pack.getInfo(),
				new ResourcePackProfile.PackFactory() {
					@Override
					public ResourcePack open(ResourcePackInfo info) {
						return RuntimeResourcePack.create(info.id());
					}

					@Override
					public ResourcePack openWithOverlays(ResourcePackInfo info, ResourcePackProfile.Metadata metadata) {
						return RuntimeResourcePack.create(info.id());
					}
				},
				this.type,
				new ResourcePackPosition(false, ResourcePackProfile.InsertionPosition.TOP, false)
			));
		}
	}
}
