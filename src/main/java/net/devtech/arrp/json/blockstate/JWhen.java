package net.devtech.arrp.json.blockstate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.Pair;

public class JWhen implements Cloneable {
	private final List<Pair<String, String[]>> OR = new ArrayList<>();

	/**
	 * @see JState#when()
	 */
	public JWhen() {}

	public JWhen add(String condition, String... states) {
		this.OR.add(new Pair<>(condition, states));
		return this;
	}

	@Override
	public JWhen clone() {
		try {
			return (JWhen) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JWhen> {
		@Override
		public JsonElement serialize(JWhen src, Type typeOfSrc, JsonSerializationContext context) {
			if (src.OR.size() == 1) {
				JsonObject json = new JsonObject();
				Pair<String, String[]> val = src.OR.get(0);
				json.addProperty(val.getLeft(), String.join("|", Arrays.asList(val.getRight())));
				return json;
			} else {
				JsonArray array = new JsonArray();
				for (Pair<String, String[]> val : src.OR) {
					JsonObject json = new JsonObject();
					json.addProperty(val.getLeft(), String.join("|", Arrays.asList(val.getRight())));
					array.add(json);
				}
				return array;
			}
		}
	}
}
