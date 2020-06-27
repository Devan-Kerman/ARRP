package net.devtech.arrp.json.blockstate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class JState {
	private final List<JVariant> variants = new ArrayList<>();
	private final List<JMultipart> multiparts = new ArrayList<>();

	/**
	 * @see #state()
	 * @see #state(JMultipart...)
	 * @see #state(JVariant...)
	 */
	public JState() {
	}

	public static JState state() {
		return new JState();
	}

	public static JState state(JVariant... variants) {
		JState state = new JState();
		for (JVariant variant : variants) {
			state.add(variant);
		}
		return state;
	}

	public JState add(JVariant variant) {
		if (!this.multiparts.isEmpty()) {
			throw new IllegalStateException("BlockStates can only have variants *or* multiparts, not both");
		}
		this.variants.add(variant);
		return this;
	}

	public static JState state(JMultipart... parts) {
		JState state = new JState();
		for (JMultipart part : parts) {
			state.add(part);
		}
		return state;
	}

	public JState add(JMultipart multiparts) {
		if (!this.variants.isEmpty()) {
			throw new IllegalStateException("BlockStates can only have variants *or* multiparts, not both");
		}
		this.multiparts.add(multiparts);
		return this;
	}

	public static JVariant variant() {
		return new JVariant();
	}

	public static JVariant variant(JBlockModel model) {
		JVariant variant = new JVariant();
		variant.put("", model);
		return variant;
	}

	public static JBlockModel model(String id) {
		return new JBlockModel(id);
	}

	public static JMultipart multipart(JBlockModel... models) {
		JMultipart multipart = new JMultipart();
		for (JBlockModel model : models) {
			multipart.addModel(model);
		}
		return multipart;
	}

	public static JWhen when() {
		return new JWhen();
	}

	@Override
	public JState clone() {
		try {
			return (JState) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JState> {
		@Override
		public JsonElement serialize(JState src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			if (!src.variants.isEmpty()) {
				if (src.variants.size() == 1) {
					json.add("variants", context.serialize(src.variants.get(0)));
				} else {
					json.add("variants", context.serialize(src.variants));
				}
			}
			if (!src.multiparts.isEmpty()) {
				json.add("multipart", context.serialize(src.multiparts));
			}
			return json;
		}
	}
}
