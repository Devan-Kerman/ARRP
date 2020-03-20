package net.devtech.rrp.api;

import net.devtech.rrp.util.recipies.RecipeChoice;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a resource pack that contains datapack information
 * this is used by the server for loot tables and stuff
 */
public interface RuntimeDatapack {
	/**
	 * create a default block loot table, like the one shown in the fabric
	 * tutorial
	 * @param block the block loot table
	 * @param item the block item
	 */
	void addDefaultBlockLootTable(Identifier block, Identifier item);

	/**
	 * create a redstone ore / glowstone block like loot table
	 * for the block, can be picked up with silk touch and is influenced by
	 * fortune
	 * @param block the block id
	 * @param piece the "piece" id eg. redstone dust
	 * @param max the maximum number of pieces eg. 4
	 * @param min the minimum number of pieces eg. 3
	 */
	void addAggregateBlockLootTable(Identifier block, Identifier piece, int max, int min);

	/**
	 * add a block loot table resembling that of cobblestone
	 * @param block the id of the block
	 * @param blockItem the dropped id
	 * @param cobble the "cobblestone" of this block
	 */
	void addCobbleBlockLootTable(Identifier block, Identifier blockItem, Identifier cobble);

	/**
	 * add a raw loot table
	 * @param identifier the id of the table
	 * @param table the string json
	 */
	void addLootTable(Identifier identifier, String table);

	/**
	 * add a shaped crafting recipe that can only be crafted in
	 * a crafting table
	 * with the inputs that results in an amount
	 * of the item
	 * @param result the id of the result item
	 * @param amount the amount of the item the recipe yields
	 * @param choices the array of recipe choices for the input, left to right, top to bottom, may have nulls
	 */
	void addShaped9x9CraftingRecipe(Identifier id, Identifier result, int amount, @Nullable RecipeChoice...choices);

	/**
	 * add a shaped recipe that can be crafted in the player's inventory crafting grid
	 * @param id the id of the recipe
	 * @param result the id of the result item
	 * @param amount the amount of the item the recipe yields
	 * @param choices the array of recipe choices for the input, left to right, top to bottom, may have nulls
	 */
	void addShaped4x4CraftingRecipe(Identifier id, Identifier result, int amount, @Nullable RecipeChoice...choices);

	/**
	 * add an unshaped recipe
	 * @param id the id of the recipe
	 * @param result the id of the result item
	 * @param amount the amount of the item the recipe yields
	 * @param choices the array of recipe choices for the input, left to right, top to bottom all entries must not be null
	 */
	void addShapelessRecipe(Identifier id, Identifier result, int amount, @NotNull RecipeChoice...choices);
}
