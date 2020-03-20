package net.devtech.rrp.util;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class BlockModelJsonBuilder {
	private static final Gson GSON = new Gson();
	private final Map<String, String> textures = new HashMap<>();
	private final String parent;

	public BlockModelJsonBuilder(String parent) {this.parent = parent;}

	public void putTexture(String key, String texture) {
		this.textures.put(key, "block/" + texture);
	}

	public String build() {
		return String.format("{\"parent\":\"%s\",\"textures\":%s}", this.parent, GSON.toJson(this.textures));
	}
}
