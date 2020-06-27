package net.devtech.arrp.json.recipe;

public class JShapeless extends JResultRecipe {
    protected final JIngredients ingredients;

    JShapeless(final JResult result, final JIngredients ingredients) {
        super("minecraft:crafting_shapeless", result);

        this.ingredients = ingredients;
    }

    @Override
    public JShapeless group(final String group) {
        return (JShapeless) super.group(group);
    }

    @Override
    protected JShapeless clone() {
        return (JShapeless) super.clone();
    }
}
