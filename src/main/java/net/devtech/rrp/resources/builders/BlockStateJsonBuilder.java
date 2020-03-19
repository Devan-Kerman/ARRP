package net.devtech.rrp.resources.builders;

import com.google.gson.Gson;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;

/**
 * a builder class for blockstate jsons
 */
public class BlockStateJsonBuilder {
	private static final Gson GSON = new Gson();
	private Map<String, String> states = new HashMap<>();

	/**
	 * create a new normal blockstate json
	 */
	public String normal(Identifier identifier) {
		this.states.put("", String.format("{\"model\": %s}", identifier));
		return this.build();
	}

	/**
	 * add a blockstate key to the json
	 * assumes any model added will be located in model/block
	 */
	public BlockStateJsonBuilder add(String key, Identifier model) {
		this.states.put(key, String.format("{\"model\": %s:block/%s}", model.getNamespace(), model.getPath()));
		return this;
	}

	public String build() {
		return String.format("{\"variants\": %s}", GSON.toJson(this.states));
	}
}
