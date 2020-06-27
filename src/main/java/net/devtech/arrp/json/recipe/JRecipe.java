package net.devtech.arrp.json.recipe;

public abstract class JRecipe implements Cloneable {
    protected final String type;

    protected String group;

    JRecipe(final String type) {
        this.type = type;
    }

    public static JSmithingRecipe smithing(final JIngredient base, final JIngredient addition, final JResult result) {
        return new JSmithingRecipe(base, addition, result);
    }

    public static JStonecuttingRecipe stonecutting(final JIngredient ingredient, final JStackedResult result) {
        return new JStonecuttingRecipe(ingredient, result);
    }

    // crafting

    public static JShapedRecipe shaped(final JPattern pattern, final JKeys keys, final JResult result) {
        return new JShapedRecipe(result, pattern, keys);
    }

    public static JShapelessRecipe shapeless(final JIngredients ingredients, final JResult result) {
        return new JShapelessRecipe(result, ingredients);
    }

    // cooking

    public static JBlastingRecipe blasting(final JIngredient ingredient, final JResult result) {
        return new JBlastingRecipe(ingredient, result);
    }

    public static JSmeltingRecipe smelting(final JIngredient ingredient, final JResult result) {
        return new JSmeltingRecipe(ingredient, result);
    }

    public static JCampfireRecipe campfire(final JIngredient ingredient, final JResult result) {
        return new JCampfireRecipe(ingredient, result);
    }

    public static JSmokingRecipe smoking(final JIngredient ingredient, final JResult result) {
        return new JSmokingRecipe(ingredient, result);
    }

    public JRecipe group(final String group) {
        this.group = group;

        return this;
    }

    @Override
    protected JRecipe clone() {
        try {
            return (JRecipe) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
