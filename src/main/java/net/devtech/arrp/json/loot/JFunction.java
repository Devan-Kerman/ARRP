package net.devtech.arrp.json.loot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JFunction implements Cloneable {
	private final List<JCondition> conditions = new ArrayList<>();
	private Map<String, Object> properties = new HashMap<>();

	/**
	 * @see JLootTable#function(String)
	 */
	public JFunction(String function) {
		this.properties.put("function", function);
	}

	public JFunction add(JCondition condition) {
		this.conditions.add(condition);
		return this;
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
			JsonObject element = context.serialize(src.properties)
			                            .getAsJsonObject();
			if (!src.conditions.isEmpty()) {
				element.add("conditions", context.serialize(src.conditions));
			}
			return element;
		}
	}
}
