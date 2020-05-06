package net.devtech.arrp.json.tags;

import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class JTag {
	private Boolean replace;
	private List<String> values = new ArrayList<>();

	JTag() {}

	public static JTag replacingTag() {
		return tag().replace();
	}

	/**
	 * whether or not this tag should override all super tags
	 */
	JTag replace() {
		this.replace = true;
		return this;
	}

	public static JTag tag() {
		return new JTag();
	}

	/**
	 * add a normal item to the tag
	 */
	JTag add(Identifier identifier) {
		this.values.add(identifier.toString());
		return this;
	}

	/**
	 * add a tag to the tag
	 */
	JTag tag(Identifier tag) {
		this.values.add('#' + tag.getNamespace() + ':' + tag.getPath());
		return this;
	}
}
