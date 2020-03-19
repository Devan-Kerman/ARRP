package net.devtech.rrp.resources;

import net.devtech.rrp.AbstractRuntimePack;
import net.devtech.rrp.resources.builders.BlockModelJsonBuilder;
import net.devtech.rrp.resources.builders.BlockStateJsonBuilder;
import net.minecraft.util.Identifier;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.lang.String.format;

public class RuntimeAssetPackImpl extends AbstractRuntimePack implements RuntimeAssetPack {
	private static final Logger LOGGER = Logger.getLogger("RuntimeResourcePack");
	private final ExecutorService service;

	public RuntimeAssetPackImpl(ExecutorService service) {this.service = service;}

	@Override
	public void registerRawStringResource(Identifier identifier, String string) {
		byte[] data = string.getBytes();
		this.registerRawResource(identifier, () -> data);
	}

	@Override
	public void registerBlockModel(Identifier identifier, BlockModelJsonBuilder builder) {
		this.registerRawStringResource(fix(identifier, "models/block", "json"), builder.build());
	}

	@Override
	public void addTemplatedResource(Identifier path, String template, Object... args) {
		this.registerRawStringResource(path, MessageFormat.format(template, args));
	}

	@Override
	public void registerBlockState(Identifier identifier, BlockStateJsonBuilder builder) {
		this.registerRawStringResource(fix(identifier, "blockstates", "json"), builder.build());
	}

	@Override
	public void registerDefaultBlockState(Identifier identifier) {
		this.registerRawStringResource(fix(identifier, "blockstates", "json"), format("{\"variants\":{\"\":{\"model\":\"%s:block/%s\"}}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void registerDefaultBlockItemModel(Identifier identifier) {
		this.registerItemModel(identifier, format("{\"parent\": \"%s:block/%s\"}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void registerCubeAllBlockModel(Identifier identifier) {
		this.registerRawStringResource(fix(identifier, "models/block", "json"), format("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"%s:block/%s\"}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void registerItemModel(Identifier identifier, String model) {
		this.registerRawStringResource(fix(identifier, "models/item", "json"), model);
	}

	@Override
	public void registerDefaultItemModel(Identifier identifier) {
		this.registerItemModel(identifier, format("{\"parent\":\"item/generated\", \"textures\": {\"layer0\":\"%s:item/%s\"}}", identifier.getNamespace(), identifier.getPath()));
	}

	@Override
	public void registerTexture(Identifier identifier, BufferedImage image) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			byte[] data = out.toByteArray();
			this.registerRawResource(fix(identifier, "textures", "png"), () -> data);
		} catch (IOException e) {
			throw new RuntimeException("Fatal error in serializing image", e);
		}
	}

	@Override
	public void registerAsyncTexture(Identifier identifier, Supplier<BufferedImage> image) {
		Future<byte[]> array = this.service.submit(() -> {
			BufferedImage img = image.get();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			return out.toByteArray();
		});
		this.registerRawResource(fix(identifier, "textures", "png"), () -> {
			try {
				return array.get();
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.severe("Fatal error when drawing or serializing image!");
				throw new RuntimeException(e);
			}
		});
	}


	@Override
	public String getName() {
		return "RRP Runtime Asset Pack";
	}
}