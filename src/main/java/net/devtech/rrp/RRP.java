package net.devtech.rrp;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.devtech.rrp.util.recipies.RecipeChoice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
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
import java.util.HashMap;
import java.util.logging.Logger;

import static net.minecraft.util.registry.Registry.BLOCK;
import static net.minecraft.util.registry.Registry.ITEM;

// todo block models
// todo sound
// todo recipes
// remember: never use resource pack providers for assets, fabric API clears and rebuilds the resource pack list
// last time you tried this, you spent 5 hours debugging that, so don't do it again
public class RRP implements ModInitializer {
	private static final String MOD_ID = "runtime_resource_pack";
	public static final Identifier TEST_ITEM = new Identifier(MOD_ID, "test_item");
	public static final Identifier TEST_BLOCK = new Identifier(MOD_ID, "test_block");

	private void addRDP() {
		RuntimeResourcePack.INSTANCE.addAggregateBlockLootTable(TEST_BLOCK, TEST_ITEM, 3, 1);
	}

	private void addRAP() {
		RuntimeResourcePack.INSTANCE.addDefaultItemModel(TEST_ITEM);
		RuntimeResourcePack.INSTANCE.addAsyncTexture(new Identifier(MOD_ID, "item/test_item"), () -> {
			BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			graphics2D.setColor(new Color(0x80ffffff, true));
			graphics2D.drawLine(0, 0, 16, 16);
			graphics2D.setColor(Color.RED);
			graphics2D.drawLine(0, 16, 16, 0);
			return image;
		});

		RuntimeResourcePack.INSTANCE.addDefaultBlockItemModel(TEST_BLOCK);
		RuntimeResourcePack.INSTANCE.addCubeAllBlockModel(TEST_BLOCK);
		RuntimeResourcePack.INSTANCE.addDefaultBlockState(TEST_BLOCK);
		RuntimeResourcePack.INSTANCE.addAsyncTexture(new Identifier(MOD_ID, "block/test_block"), () -> {
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

		HashMap<String, String> map = new HashMap<>();
		map.put("item."+MOD_ID+".test_item", "Test Item");
		map.put("block."+MOD_ID+".test_block", "Test Block");
		RuntimeResourcePack.INSTANCE.addLangFile(MOD_ID, "en_us", map);

		RecipeChoice recipeChoice = new RecipeChoice.Item(TEST_ITEM);
		RuntimeResourcePack.INSTANCE.addShapelessRecipe(new Identifier(MOD_ID, "recipe"), TEST_BLOCK, 1, recipeChoice, recipeChoice, recipeChoice, recipeChoice);
		RuntimeResourcePack.INSTANCE.addShaped4x4CraftingRecipe(new Identifier(MOD_ID, "recipe0"), TEST_BLOCK, 1, recipeChoice, null, recipeChoice, null);
	}

	@Override
	public void onInitialize() {
		Logger.getLogger("RRP").severe("Run from it... Dread it... but RRP still arrives.");
		// example code
		/*Registry.register(ITEM, TEST_ITEM, new Item(new Item.Settings().group(ItemGroup.MISC)));
		Block testBlock = new Block(FabricBlockSettings.of(Material.METAL).breakByHand(true).build());
		Registry.register(BLOCK, TEST_BLOCK, testBlock);
		Registry.register(ITEM, TEST_BLOCK, new BlockItem(testBlock, new Item.Settings().group(ItemGroup.MISC)));
		this.addRDP();
		this.addRAP();*/
	}
}