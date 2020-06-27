package net.devtech.arrp.json.recipe;

public class JBlastingRecipe extends JCookingRecipe {
    JBlastingRecipe(final JIngredient ingredient, final JResult result) {
        super("blasting", ingredient, result);
    }

    @Override
    public JBlastingRecipe group(final String group) {
        return (JBlastingRecipe) super.group(group);
    }

    @Override
    protected JBlastingRecipe clone() {
        return (JBlastingRecipe) super.clone();
    }
}
