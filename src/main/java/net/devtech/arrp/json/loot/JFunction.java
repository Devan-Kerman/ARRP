package net.devtech.arrp.json.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JFunction implements Cloneable {
	private final List<JCondition> conditions = new ArrayList<>();
	private Map<String, Object> properties = new HashMap<>();

	JFunction(String function) {
		this.properties.put("function", function);
	}

	public JFunction add(JCondition condition) {
		this.conditions.add(condition);
		return this;
	}


	public static class Serializer implements JsonSerializer<JFunction> {
		@Override
		public JsonElement serialize(JFunction src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = context.serialize(src.properties).getAsJsonObject();
			if (!src.conditions.isEmpty()) element.add("conditions", context.serialize(src.conditions));
			return element;
		}
	}

	@Override
	public JFunction clone() {
		try {
			return (JFunction) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
