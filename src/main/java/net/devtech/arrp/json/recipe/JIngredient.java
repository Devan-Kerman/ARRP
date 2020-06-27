package net.devtech.arrp.json.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class JIngredient implements Cloneable {
    protected String item;
    protected String tag;
    protected List<JIngredient> ingredients;

    JIngredient() {}

    public static JIngredient ingredient() {
        return new JIngredient();
    }

    public JIngredient item(Item item) {
        return this.item(Registry.ITEM.getId(item).toString());
    }

    public JIngredient item(String id) {
        if (this.isDefined()) {
            return this.add(JIngredient.ingredient().item(id));
        }

        this.item = id;

        return this;
    }

    public JIngredient tag(String tag) {
        if (this.isDefined()) {
            return this.add(JIngredient.ingredient().tag(tag));
        }

        this.tag = tag;

        return this;
    }

    public JIngredient add(final JIngredient ingredient) {
        if (this.ingredients == null) {
            final List<JIngredient> ingredients = new ArrayList<>();

            if (this.isDefined()) {
                ingredients.add(this.clone());
            }

            this.ingredients = ingredients;
        }

        this.ingredients.add(ingredient);

        return this;
    }

    private boolean isDefined() {
        return this.item != null || this.tag != null;
    }

    @Override
    public JIngredient clone() {
        try {
            return (JIngredient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public static class Serializer implements JsonSerializer<JIngredient> {
        @Override
        public JsonElement serialize(final JIngredient src, final Type typeOfSrc, final JsonSerializationContext context) {
            if (src.ingredients != null) {
                return context.serialize(src.ingredients);
            }

            final JsonObject object = new JsonObject();

            object.add("item", context.serialize(src.item));
            object.add("tag", context.serialize(src.tag));

            return object;
        }
    }
}
