package net.devtech.rrp.util.recipies;

import net.minecraft.util.Identifier;
import java.util.Map;

public class ShapedRecipe {
	private final String type;
	private final Map<String, RecipeChoice> key;
	private final Result result;
	private final String[] pattern;

	public ShapedRecipe(Map<String, RecipeChoice> choices, Identifier result, int amount, String[] pattern) {
		this.type = "minecraft:crafting_shaped";
		this.key = choices;
		this.result = new Result(result, amount);
		this.pattern = pattern;
	}

	static class Result {
		final String item;
		final int amount;

		Result(Identifier identifier, int amount) {
			this.item = identifier.toString();
			this.amount = amount;
		}
	}
}
