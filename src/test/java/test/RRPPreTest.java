package test;

import net.devtech.rrp.RRP;
import net.devtech.rrp.api.RuntimeResourcePack;
import net.devtech.rrp.entrypoint.RRPPreGenEntrypoint;
import net.devtech.rrp.util.recipies.RecipeChoice;
import net.minecraft.util.Identifier;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class RRPPreTest implements RRPPreGenEntrypoint {
	private static final String MOD_ID = RRP.MOD_ID;
	public static final Identifier TEST_ITEM = new Identifier(MOD_ID, "test_item");
	public static final Identifier TEST_BLOCK = new Identifier(MOD_ID, "test_block");

	@Override
	public void register() {
	}

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
}
