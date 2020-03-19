package net.devtech.rrp.data.mixin;

import net.devtech.rrp.data.RuntimeDataPackProvider;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.File;

@Mixin (MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow @Final private ResourcePackManager<ResourcePackProfile> dataPackManager;


	@Inject (method = "loadWorldDataPacks",
	         at = @At ("HEAD"))
	private void registerRDP(File worldDir, LevelProperties levelProperties, CallbackInfo ci) {
		System.out.println("!!! Registering RDP !!!");
		//this.dataPackManager.registerProvider(new RuntimeDataPackProvider());
		//levelProperties.getEnabledDataPacks().add("RPP_server");
	}
}
