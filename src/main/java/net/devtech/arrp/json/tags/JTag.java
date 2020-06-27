package net.devtech.arrp.json.tags;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Identifier;

public class JTag {
	private Boolean replace;
	private List<String> values = new ArrayList<>();

	/**
	 * @see #tag()
	 * @see #tag(Identifier)
	 */
	public JTag() {}

	public static JTag replacingTag() {
		return tag().replace();
	}

	/**
	 * whether or not this tag should override all super tags
	 */
	public JTag replace() {
		this.replace = true;
		return this;
	}

	public static JTag tag() {
		return new JTag();
	}

	/**
	 * add a normal item to the tag
	 */
	public JTag add(Identifier identifier) {
		this.values.add(identifier.toString());
		return this;
	}

	/**
	 * add a tag to the tag
	 */
	public JTag tag(Identifier tag) {
		this.values.add('#' + tag.getNamespace() + ':' + tag.getPath());
		return this;
	}

	@Override
	public JTag clone() {
		try {
			return (JTag) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
