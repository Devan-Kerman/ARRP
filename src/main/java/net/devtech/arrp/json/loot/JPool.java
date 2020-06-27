package net.devtech.arrp.json.loot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JPool implements Cloneable {
	private List<JCondition> conditions;
	private List<JFunction> functions;
	private List<JEntry> entries;
	private Integer rolls;
	private JRoll roll;
	private Integer bonus_rolls;
	private JRoll bonus_roll;

	/**
	 * @see JLootTable#pool()
	 */
	public JPool() {}

	public JPool entry(JEntry entry) {
		if (this.entries == null) {
			this.entries = new ArrayList<>(1);
		}
		this.entries.add(entry);
		return this;
	}

	public JPool condition(JCondition condition) {
		if (this.conditions == null) {
			this.conditions = new ArrayList<>(1);
		}
		this.conditions.add(condition);
		return this;
	}

	public JPool function(JFunction function) {
		if (this.functions == null) {
			this.functions = new ArrayList<>(1);
		}
		this.functions.add(function);
		return this;
	}

	public JPool rolls(Integer rolls) {
		this.rolls = rolls;
		return this;
	}

	public JPool rolls(JRoll roll) {
		this.roll = roll;
		return this;
	}

	public JPool bonus(Integer bonus_rolls) {
		this.bonus_rolls = bonus_rolls;
		return this;
	}

	public JPool bonus(JRoll bonus_roll) {
		this.bonus_roll = bonus_roll;
		return this;
	}

	@Override
	public JPool clone() {
		try {
			return (JPool) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public static class Serializer implements JsonSerializer<JPool> {
		@Override
		public JsonElement serialize(JPool src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject obj = new JsonObject();
			if (src.conditions != null) {
				obj.add("conditions", context.serialize(src.conditions));
			}
			if (src.functions != null) {
				obj.add("functions", context.serialize(src.functions));
			}
			if (src.entries != null) {
				obj.add("entries", context.serialize(src.entries));
			}
			if (src.rolls != null) {
				obj.addProperty("rolls", src.rolls);
			}
			if (src.roll != null) {
				obj.add("rolls", context.serialize(src.roll));
			}
			if (src.bonus_roll != null) {
				obj.add("bonus_rolls", context.serialize(src.bonus_roll));
			}
			if (src.bonus_rolls != null) {
				obj.addProperty("bonus_rolls", src.bonus_rolls);
			}
			return obj;
		}
	}
}
