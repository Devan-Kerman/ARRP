package net.devtech.arrp.json.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JTextures {
	private final Map<String, String> textures = new HashMap<>();

	/**
	 * @see JModel#textures()
	 */
	JTextures() {}

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

	public static class Serializer implements JsonSerializer<JTextures> {
		@Override
		public JsonElement serialize(JTextures src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			src.textures.forEach(json::addProperty);
			return json;
		}
	}
}
