package net.devtech.arrp.json.recipe;

public class JCampfireRecipe extends JCookingRecipe {
    JCampfireRecipe(final JIngredient ingredient, final JResult result) {
        super("campfire_cooking", ingredient, result);
    }

    @Override
    public JCampfireRecipe experience(final float experience) {
        return (JCampfireRecipe) super.experience(experience);
    }

    @Override
    public JCampfireRecipe cookingTime(final int ticks) {
        return (JCampfireRecipe) super.cookingTime(ticks);
    }

    @Override
    public JCampfireRecipe group(final String group) {
        return (JCampfireRecipe) super.group(group);
    }

    @Override
    protected JCampfireRecipe clone() {
        return (JCampfireRecipe) super.clone();
    }
}
