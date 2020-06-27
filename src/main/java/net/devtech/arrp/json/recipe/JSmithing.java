package net.devtech.arrp.json.recipe;

public class JSmithing extends JResultRecipe {
    private final JIngredient base;
    private final JIngredient addition;

    JSmithing(final JIngredient base, final JIngredient addition, final JResult result) {
        super("smithing", result);

        this.base = base;
        this.addition = addition;
    }

    @Override
    public JSmithing group(final String group) {
        return (JSmithing) super.group(group);
    }

    @Override
    protected JSmithing clone() {
        return (JSmithing) super.clone();
    }
}
