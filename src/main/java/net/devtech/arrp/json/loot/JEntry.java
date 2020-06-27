package net.devtech.arrp.json.loot;

import java.util.ArrayList;
import java.util.List;

public class JEntry implements Cloneable {
	private String type;
	private String name;
	private List<String> children;
	private Boolean expand;
	private List<JFunction> functions;
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

	public JEntry child(String child) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
		return this;
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
