package net.devtech.arrp.json.models;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JTextures {
	private final Map<String, String> textures = new HashMap<>();

	/**
	 * @see JModel#textures()
	 */
	public JTextures() {}

	public JTextures var(String name, String val) {
		this.textures.put(name, val);
		return this;
	}

	public JTextures particle(String val) {
		this.textures.put("particle", val);
		return this;
	}

	public JTextures layer0(String val) {
		this.textures.put("layer0", val);
		return this;
	}

	public JTextures layer1(String val) {
		this.textures.put("layer1", val);
		return this;
	}

	public JTextures layer2(String val) {
		this.textures.put("layer2", val);
		return this;
	}

	public JTextures layer3(String val) {
		this.textures.put("layer3", val);
		return this;
	}

	public JTextures layer4(String val) {
		this.textures.put("layer4", val);
		return this;
	}

	@Override
	public JTextures clone() {
		try {
			return (JTextures) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JTextures> {
		@Override
		public JsonElement serialize(JTextures src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			src.textures.forEach(json::addProperty);
			return json;
		}
	}
}
