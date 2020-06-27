package net.devtech.arrp.json.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JKeys implements Cloneable {
    protected final Map<String, JIngredient> keys;
    protected final Map<String, List<JIngredient>> acceptableKeys;

    JKeys() {
        this.keys = new HashMap<>(9, 1);
        this.acceptableKeys = new HashMap<>();
    }

    public static JKeys keys() {
        return new JKeys();
    }

    public JKeys key(final String key, final JIngredient value) {
        this.keys.put(key, value);

        return this;
    }

    @Override
    protected JKeys clone() {
        try {
            return (JKeys) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public static class Serializer implements JsonSerializer<JKeys> {
        @Override
        public JsonElement serialize(final JKeys src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonObject object = new JsonObject();

            src.keys.forEach((final String key, final JIngredient ingredient) -> object.add(key, context.serialize(ingredient)));
            src.acceptableKeys.forEach((final String key, final List<JIngredient> acceptableIngredients) -> object.add(key, context.serialize(acceptableIngredients)));

            return object;
        }
    }
}
