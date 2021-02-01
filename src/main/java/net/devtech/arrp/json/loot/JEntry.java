package net.devtech.arrp.json.loot;

import net.devtech.arrp.impl.RuntimeResourcePackImpl;

import java.util.ArrayList;
import java.util.List;

public class JEntry implements Cloneable {
	private String type;
	private String name;
	private List<JEntry> children;
	private Boolean expand;
	private List<JFunction> functions;
	private List<JCondition> conditions;
	private Integer weight;
	private Integer quality;

	/**
	 * @see JLootTable#entry()
	 */
	public JEntry() {}

	public JEntry type(String type) {
		this.type = type;
		return this;
	}

	public JEntry name(String name) {
		this.name = name;
		return this;
	}

	public JEntry child(JEntry child) {
		if (this == child) {
			throw new IllegalArgumentException("Can't add entry as its own child!");
		}
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
		return this;
	}

	/**
	 * @deprecated unintuitive to use
	 * @see JEntry#child(JEntry)
	 */
	@Deprecated
	public JEntry child(String child) {
		return child(RuntimeResourcePackImpl.GSON.fromJson(child, JEntry.class));
	}

	public JEntry expand(Boolean expand) {
		this.expand = expand;
		return this;
	}

	public JEntry function(JFunction function) {
		if (this.functions == null) {
			this.functions = new ArrayList<>();
		}
		this.functions.add(function);
		return this;
	}

	public JEntry function(String function) {
	    return function(JLootTable.function(function));
    }

	public JEntry condition(JCondition condition) {
		if(this.conditions == null) {
			this.conditions = new ArrayList<>();
		}
		this.conditions.add(condition);
		return this;
	}

	public JEntry condition(String condition) {
		return condition(JLootTable.predicate(condition));
	}

	public JEntry weight(Integer weight) {
		this.weight = weight;
		return this;
	}

	public JEntry quality(Integer quality) {
		this.quality = quality;
		return this;
	}

	@Override
	public JEntry clone() {
		try {
			return (JEntry) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
