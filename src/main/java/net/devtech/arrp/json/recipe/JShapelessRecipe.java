package net.devtech.arrp.json.recipe;

public class JShapelessRecipe extends JResultRecipe {
    protected final JIngredients ingredients;

    JShapelessRecipe(final JResult result, final JIngredients ingredients) {
        super("minecraft:crafting_shapeless", result);

        this.ingredients = ingredients;
    }

    @Override
    public JShapelessRecipe group(final String group) {
        return (JShapelessRecipe) super.group(group);
    }

    @Override
    protected JShapelessRecipe clone() {
        return (JShapelessRecipe) super.clone();
    }
}
