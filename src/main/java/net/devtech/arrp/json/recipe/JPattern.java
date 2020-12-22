package net.devtech.arrp.json.recipe;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JPattern implements Cloneable {
	protected final String[] rows;

	JPattern(String... rows) {
		this.rows = rows;
	}

	public static JPattern pattern() {
		return new JPattern("   ", "   ", "   ");
	}

	public static JPattern pattern(String... rows) {
		return new JPattern(rows);
	}

	public JPattern row1(String keys) {
		return this.row(0, keys);
	}

	protected JPattern row(int index, String keys) {
		this.rows[index] = keys;

		return this;
	}

	public JPattern row2(String keys) {
		return this.row(1, keys);
	}

	public JPattern row3(String keys) {
		return this.row(2, keys);
	}

	@Override
	protected JPattern clone() {
		try {
			return (JPattern) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JPattern> {
		@Override
		public JsonElement serialize(final JPattern src, final Type typeOfSrc,
				final JsonSerializationContext context) {
			return context.serialize(src.rows);
		}
	}
}
