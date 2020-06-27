package net.devtech.arrp.json.recipe;

public class JShapedRecipe extends JResultRecipe {
    protected final JPattern pattern;
    protected final JKeys key;

    JShapedRecipe(JResult result, JPattern pattern, JKeys keys) {
        super("minecraft:crafting_shaped", result);

        this.pattern = pattern;
        this.key = keys;
    }

    @Override
    public JShapedRecipe group(final String group) {
        return (JShapedRecipe) super.group(group);
    }

    @Override
    protected JShapedRecipe clone() {
        return (JShapedRecipe) super.clone();
    }
}
