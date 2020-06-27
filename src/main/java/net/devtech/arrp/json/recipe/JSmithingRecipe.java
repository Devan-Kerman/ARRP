package net.devtech.arrp.json.recipe;

public class JSmithingRecipe extends JResultRecipe {
    private final JIngredient base;
    private final JIngredient addition;

    JSmithingRecipe(final JIngredient base, final JIngredient addition, final JResult result) {
        super("smithing", result);

        this.base = base;
        this.addition = addition;
    }

    @Override
    public JSmithingRecipe group(final String group) {
        return (JSmithingRecipe) super.group(group);
    }

    @Override
    protected JSmithingRecipe clone() {
        return (JSmithingRecipe) super.clone();
    }
}
