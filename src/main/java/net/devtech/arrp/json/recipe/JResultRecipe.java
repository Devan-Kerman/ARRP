package net.devtech.arrp.json.recipe;

public abstract class JResultRecipe extends JRecipe {
    protected final JResult result;

    JResultRecipe(final String type, final JResult result) {
        super(type);

        this.result = result;
    }
}
