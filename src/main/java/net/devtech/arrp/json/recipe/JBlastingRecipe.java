package net.devtech.arrp.json.recipe;

public class JBlastingRecipe extends JCookingRecipe {
    JBlastingRecipe(final JIngredient ingredient, final JResult result) {
        super("blasting", ingredient, result);
    }

    @Override
    public JBlastingRecipe experience(final float experience) {
        return (JBlastingRecipe) super.experience(experience);
    }

    @Override
    public JBlastingRecipe cookingTime(final int ticks) {
        return (JBlastingRecipe) super.cookingTime(ticks);
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
