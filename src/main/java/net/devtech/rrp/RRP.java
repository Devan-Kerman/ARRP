package net.devtech.rrp;

import net.devtech.rrp.resources.callbacks.RAPCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static net.minecraft.util.registry.Registry.BLOCK;
import static net.minecraft.util.registry.Registry.ITEM;

// todo block models
// todo lang files
// todo sound
// remember: never use resource pack providers, fabric API clears and rebuilds the resource pack list
// last time you tried this, you spent 5 hours debugging that, so don't do it again
public class RRP implements ModInitializer {
	private static final String MOD_ID = "rrp";

	@Override
	public void onInitialize() {
		Identifier item = new Identifier(MOD_ID, "test_item");
		Identifier block = new Identifier(MOD_ID, "test_block");
		Registry.register(ITEM, item, new Item(new Item.Settings().group(ItemGroup.MISC)));
		Block testBlock = new Block(Block.Settings.of(Material.STONE));
		Registry.register(Registry.ITEM, block, new BlockItem(testBlock, new Item.Settings().group(ItemGroup.MISC)));
		Registry.register(BLOCK, block, testBlock);
		RAPCallback.EVENT.register(r -> {
			r.registerDefaultItemModel(item);
			r.registerAsyncTexture(new Identifier(MOD_ID, "item/test_item"), () -> {
				BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics2D = image.createGraphics();
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawLine(0, 0, 16, 16);
				graphics2D.setColor(Color.RED);
				graphics2D.drawLine(0, 16, 16, 0);
				return image;
			});
			r.registerDefaultBlockItemModel(block);
			r.registerCubeAllBlockModel(block);
			r.registerDefaultBlockState(block);
			r.registerAsyncTexture(new Identifier(MOD_ID, "block/test_block"), () -> {
				BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics2D = image.createGraphics();
				graphics2D.setColor(Color.WHITE);
				graphics2D.fillRect(0, 0, 16, 16);
				graphics2D.setColor(Color.BLUE);
				graphics2D.drawLine(0, 0, 16, 16);
				graphics2D.setColor(Color.RED);
				graphics2D.drawLine(0, 16, 16, 0);
				return image;
			});
		});
	}
}
