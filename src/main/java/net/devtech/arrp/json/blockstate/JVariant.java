package net.devtech.arrp.json.blockstate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public final class JVariant implements Cloneable {
	private final Map<String, JBlockModel> models = new HashMap<>();

	/**
	 * @see JState#variant()
	 */
	public JVariant() {}

	public JVariant put(String key, JBlockModel model) {
		this.models.put(key, model);
		return this;
	}

	/**
	 * boolean block properties
	 */
	public JVariant put(String property, boolean value, JBlockModel builder) {
		this.models.put(property + '=' + value, builder);
		return this;
	}

	/**
	 * int block properties
	 */
	public JVariant put(String property, int value, JBlockModel builder) {
		this.models.put(property + '=' + value, builder);
		return this;
	}

	/**
	 * other block properties
	 *
	 * @see Direction
	 */
	public JVariant put(String property, StringIdentifiable value, JBlockModel builder) {
		this.models.put(property + '=' + value.asString(), builder);
		return this;
	}

	/**
	 * everything else
	 */
	public JVariant put(String property, String value, JBlockModel builder) {
		this.models.put(property + '=' + value, builder);
		return this;
	}

	@Override
	public JVariant clone() {
		try {
			return (JVariant) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JVariant> {
		@Override
		public JsonElement serialize(JVariant src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			src.models.forEach((s, m) -> object.add(s, context.serialize(m)));
			return object;
		}
	}
}
