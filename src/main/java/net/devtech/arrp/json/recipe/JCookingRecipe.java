package net.devtech.arrp.json.recipe;

public abstract class JCookingRecipe extends JRecipe {
    private final JIngredient ingredient;
    private final String result;

    private Float experience;
    private Integer cookingtime;

    JCookingRecipe(final String type, final JIngredient ingredient, final JResult result) {
        super(type);

        this.ingredient = ingredient;
        this.result = result.item;
    }

    public JCookingRecipe experience(final float experience) {
        this.experience = experience;

        return this;
    }

    public JCookingRecipe cookingTime(final int ticks) {
        this.cookingtime = ticks;

        return this;
    }

    @Override
    public JCookingRecipe group(final String group) {
        return (JCookingRecipe) super.group(group);
    }

    @Override
    protected JCookingRecipe clone() {
        return (JCookingRecipe) super.clone();
    }
}
