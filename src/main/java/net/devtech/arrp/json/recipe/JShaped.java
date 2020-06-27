package net.devtech.arrp.json.recipe;

public class JShaped extends JResultRecipe {
    protected final JPattern pattern;
    protected final JKeys key;

    JShaped(JResult result, JPattern pattern, JKeys keys) {
        super("minecraft:crafting_shaped", result);

        this.pattern = pattern;
        this.key = keys;
    }

    @Override
    public JShaped group(final String group) {
        return (JShaped) super.group(group);
    }

    @Override
    protected JShaped clone() {
        return (JShaped) super.clone();
    }
}
