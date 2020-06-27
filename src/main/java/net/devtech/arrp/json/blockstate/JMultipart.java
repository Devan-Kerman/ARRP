package net.devtech.arrp.json.blockstate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JMultipart implements Cloneable {
	// one or list
	private final List<JBlockModel> apply = new ArrayList<>();
	private JWhen when;

	/**
	 * @see JState#multipart(JBlockModel...)
	 */
	public JMultipart() {}

	@Override
	public JMultipart clone() {
		try {
			return (JMultipart) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public JMultipart when(JWhen when) {
		this.when = when;
		return this;
	}

	public JMultipart addModel(JBlockModel model) {
		this.apply.add(model);
		return this;
	}

	public static class Serializer implements JsonSerializer<JMultipart> {
		@Override
		public JsonElement serialize(JMultipart src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject obj = new JsonObject();
			if (src.apply.size() == 1) {
				obj.add("apply", context.serialize(src.apply.get(0)));
			} else {
				obj.add("apply", context.serialize(src.apply));
			}
			obj.add("when", context.serialize(src.when));
			return obj;
		}
	}
}
