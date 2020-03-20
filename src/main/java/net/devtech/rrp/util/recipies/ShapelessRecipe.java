package net.devtech.rrp.util.recipies;

import net.minecraft.util.Identifier;

public class ShapelessRecipe {
	private final String type = "minecraft:crafting_shapeless";
	private final RecipeChoice[] ingredients;
	private final ShapedRecipe.Result result;

	public ShapelessRecipe(RecipeChoice[] ingredients, Identifier identifier, int amount) {
		this.ingredients = ingredients;
		this.result = new ShapedRecipe.Result(identifier, amount);
	}
}
