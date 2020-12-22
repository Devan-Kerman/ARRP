package net.devtech.arrp.json.loot;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.devtech.arrp.impl.RuntimeResourcePackImpl;

public class JCondition implements Cloneable {
	private JsonObject parameters = new JsonObject();

	/**
	 * @see JLootTable#condition(String)
	 * @see JLootTable#predicate(String)
	 */
	public JCondition(String condition) {
		this.parameters.addProperty("condition", condition);
	}

	public JCondition set(JsonObject parameters) {
		parameters.addProperty("condition",this.parameters.get("condition").getAsString());
		this.parameters = parameters;
		return this;
	}

	/**
	 * "or"'s the conditions together
	 */
	public JCondition alternative(JCondition...conditions) {
		JsonArray array = new JsonArray();
		for (JCondition condition : conditions) {
			array.add(RuntimeResourcePackImpl.GSON.toJsonTree(condition));
		}
		this.parameters.add("terms", array);
		return this;
	}

	@Override
	public JCondition clone() {
		try {
			return (JCondition) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JCondition> {
		@Override
		public JsonElement serialize(JCondition src, Type typeOfSrc, JsonSerializationContext context) {
			return src.parameters;
		}
	}
}
