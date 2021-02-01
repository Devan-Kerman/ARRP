package net.devtech.arrp.json.loot;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JFunction implements Cloneable {
	private final List<JCondition> conditions = new ArrayList<>();
	private JsonObject properties = new JsonObject();

	/**
	 * @see JLootTable#function(String)
	 */
	public JFunction(String function) {
		function(function);
	}

	public JFunction function(String function) {
		this.properties.addProperty("function", function);
		return this;
	}

	public JFunction set(JsonObject properties) {
		properties.addProperty("function",this.properties.get("function").getAsString());
		this.properties = properties;
		return this;
	}

	public JFunction parameter(String key, JsonElement value) {
		this.properties.add(key, value);
		return this;
	}

	public JFunction parameter(String key, String value) {
		return parameter(key, new JsonPrimitive(value));
	}

	public JFunction parameter(String key, Number value) {
		return parameter(key, new JsonPrimitive(value));
	}

	public JFunction parameter(String key, Boolean value) {
		return parameter(key, new JsonPrimitive(value));
	}

	public JFunction parameter(String key, Identifier value) {
		return parameter(key, value.toString());
	}

	public JFunction parameter(String key, Character value) {
		return parameter(key, new JsonPrimitive(value));
	}

	public JFunction condition(JCondition condition) {
		this.conditions.add(condition);
		return this;
	}

	/**
	 * @deprecated unintuitive name
	 * @see JFunction#condition(JCondition)
	 */
	@Deprecated
	public JFunction add(JCondition condition) {
		return condition(condition);
	}

	@Override
	public JFunction clone() {
		try {
			return (JFunction) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JFunction> {
		@Override
		public JsonElement serialize(JFunction src, Type typeOfSrc, JsonSerializationContext context) {
			if (!src.conditions.isEmpty()) {
				src.properties.add("conditions", context.serialize(src.conditions));
			}
			return src.properties;
		}
	}
}
