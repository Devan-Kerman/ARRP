package net.devtech.arrp.json.recipe;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JIngredient implements Cloneable {
    protected String item;
    protected String tag;

    JIngredient() {}

    public static JIngredient item(Item item) {
        return item(Registry.ITEM.getId(item).toString());
    }

    public static JIngredient item(String id) {
        JIngredient ingredient = new JIngredient();

        ingredient.item = id;

        return ingredient;
    }

    public static JIngredient tag(String tag) {
        JIngredient ingredient = new JIngredient();

        ingredient.tag = tag;

        return ingredient;
    }

    @Override
    protected JIngredient clone() {
        try {
            return (JIngredient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
