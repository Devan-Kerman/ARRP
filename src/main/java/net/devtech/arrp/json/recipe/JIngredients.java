package net.devtech.arrp.json.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JIngredients {
    protected final List<JIngredient> ingredients;
    protected final List<List<JIngredient>> acceptableIngredients;

    JIngredients() {
        this.ingredients = new ArrayList<>();
        this.acceptableIngredients = new ArrayList<>();
    }

    public static JIngredients ingredients() {
        return new JIngredients();
    }

    public JIngredients add(final JIngredient ingredient) {
        this.ingredients.add(ingredient);

        return this;
    }

    public JIngredients addMatchAnyOf(final JIngredient... acceptableIngredients) {
        this.acceptableIngredients.add(Arrays.asList(acceptableIngredients));

        return this;
    }

    public static class Serializer implements JsonSerializer<JIngredients> {
        @Override
        public JsonElement serialize(final JIngredients src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonArray array = new JsonArray();

            array.addAll((JsonArray) context.serialize(src.ingredients.toArray()));
            array.addAll((JsonArray) context.serialize(src.acceptableIngredients.toArray()));

            return array;
        }
    }
}
