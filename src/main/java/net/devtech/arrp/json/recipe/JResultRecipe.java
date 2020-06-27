package net.devtech.arrp.json.recipe;

public abstract class JResultRecipe extends JRecipe {
    private final JResult result;

    JResultRecipe(final String type, final JResult result) {
        super(type);

        this.result = result;
    }

    @Override
    public JResultRecipe group(final String group) {
        return (JResultRecipe) super.group(group);
    }

    @Override
    protected JResultRecipe clone() {
        return (JResultRecipe) super.clone();
    }
}
