package net.devtech.arrp.json.recipe;

public class JSmokingRecipe extends JCookingRecipe {
    JSmokingRecipe(final JIngredient ingredient, final JResult result) {
        super("smoking", ingredient, result);
    }

    @Override
    public JSmokingRecipe group(final String group) {
        return (JSmokingRecipe) super.group(group);
    }

    @Override
    protected JSmokingRecipe clone() {
        return (JSmokingRecipe) super.clone();
    }
}
