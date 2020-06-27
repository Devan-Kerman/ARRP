package net.devtech.arrp.json.recipe;

public class JSmeltingRecipe extends JCookingRecipe {
    JSmeltingRecipe(final JIngredient ingredient, final JResult result) {
        super("smelting", ingredient, result);
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
