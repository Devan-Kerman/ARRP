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

import net.minecraft.state.property.Property;
import net.minecraft.util.Pair;

public class JWhen implements Cloneable {
	private final List<List<Pair<String, String[]>>> state = new ArrayList<>();

	/**
	 * @see JState#when()
	 */
	public JWhen() {}
	
	@SafeVarargs
	public final <T extends Comparable<T>> JWhen add(Property<T> property, T... values) {
		String[] states = new String[values.length];
		for(int i = 0; i < values.length; i++) {
			states[i] = property.name(values[i]);
		}
		
		return this.add(property.getName(), states);
	}
	
	public JWhen add(String condition, String... states) {
		this.state.add(List.of(new Pair<>(condition, states)));
		return this;
	}
	
	public JWhen add(StateBuilder builder) {
		this.state.add(List.copyOf(builder.state));
		return this;
	}
	
	/**
	 * @see JState#whenStateBuilder()
	 */
	public static class StateBuilder implements Cloneable {
		final List<Pair<String, String[]>> state = new ArrayList<>();
		
		@SafeVarargs
		public final <T extends Comparable<T>> StateBuilder add(Property<T> property, T... values) {
			String[] states = new String[values.length];
			for(int i = 0; i < values.length; i++) {
				states[i] = property.name(values[i]);
			}
			
			return this.add(property.getName(), states);
		}
		
		public StateBuilder add(String condition, String... states) {
			this.state.add(new Pair<>(condition, states));
			return this;
		}
		
		@Override
		protected StateBuilder clone() {
			try {
				return (StateBuilder) super.clone();
			} catch(CloneNotSupportedException e) {
				throw new InternalError(e);
			}
		}
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
			if (src.state.size() == 1) {
				JsonObject json = new JsonObject();
				for(Pair<String, String[]> pair : src.state.get(0)) {
					json.addProperty(pair.getLeft(), String.join("|", Arrays.asList(pair.getRight())));
				}
				return json;
			} else {
				JsonObject or = new JsonObject();
				JsonArray array = new JsonArray();
				for(List<Pair<String, String[]>> pairs : src.state) {
					JsonObject json = new JsonObject();
					for(Pair<String, String[]> pair : pairs) {
						json.addProperty(pair.getLeft(), String.join("|", Arrays.asList(pair.getRight())));
					}
					array.add(json);
				}
				or.add("OR", array);
				return or;
			}
		}
	}
}
