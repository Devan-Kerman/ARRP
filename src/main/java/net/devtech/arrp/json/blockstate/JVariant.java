package net.devtech.arrp.json.blockstate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public final class JVariant implements Cloneable {
	private final Map<String, List<JBlockModel>> models = new HashMap<>();

	/**
	 * @see JState#variant()
	 */
	public JVariant() {}

	public JVariant put(String key, JBlockModel model) {
		List<JBlockModel> models = this.models.getOrDefault(key, new ArrayList<>());

		models.add(model);

		this.models.put(key, models);

		return this;
	}

	/**
	 * boolean block properties
	 */
	public JVariant put(String property, boolean value, JBlockModel model) {
		return this.put(property + '=' + value, model);
	}

	/**
	 * int block properties
	 */
	public JVariant put(String property, int value, JBlockModel model) {
		return this.put(property + '=' + value, model);
	}

	/**
	 * other block properties
	 *
	 * @see Direction
	 */
	public JVariant put(String property, StringIdentifiable value, JBlockModel model) {
		return this.put(property + '=' + value.asString(), model);
	}

	/**
	 * everything else
	 */
	public JVariant put(String property, String value, JBlockModel model) {
		return this.put(property + '=' + value, model);
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
			src.models.forEach((s, m) -> object.add(s, context.serialize(m.size() == 1 ? m.get(0) : m)));
			return object;
		}
	}
}
