package net.devtech.arrp.json.recipe;

public class JSmeltingRecipe extends JCookingRecipe {
    JSmeltingRecipe(final JIngredient ingredient, final JResult result) {
        super("smelting", ingredient, result);
    }

    @Override
    public JSmeltingRecipe experience(final float experience) {
        return (JSmeltingRecipe) super.experience(experience);
    }

    @Override
    public JSmeltingRecipe cookingTime(final int ticks) {
        return (JSmeltingRecipe) super.cookingTime(ticks);
    }

    @Override
    public JSmeltingRecipe group(final String group) {
        return (JSmeltingRecipe) super.group(group);
    }

    @Override
    protected JSmeltingRecipe clone() {
        return (JSmeltingRecipe) super.clone();
    }
}
