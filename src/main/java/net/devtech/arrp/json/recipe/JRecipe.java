package net.devtech.arrp.json.recipe;

public abstract class JRecipe implements Cloneable {
    protected final String type;

    protected String group;

    JRecipe(final String type) {
        this.type = type;
    }

    public static JShaped shaped(final JPattern pattern, final JKeys keys, final JResult result) {
        return new JShaped(result, pattern, keys);
    }

    public static JShapeless shapeless(final JIngredients ingredients, final JResult result) {
        return new JShapeless(result, ingredients);
    }

    public static JSmithing smithing(final JIngredient base, final JIngredient addition, final JResult result) {
        return new JSmithing(base, addition, result);
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
