package net.devtech.arrp.json.lang;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Identifier;

public class JLang implements Cloneable {
	private Map<String, String> lang = new HashMap<>();

	/**
	 * @see #lang()
	 */
	public JLang() {}

	public static JLang lang() {
		return new JLang();
	}

	public JLang translate(String in, String out) {
		this.lang.put(in, out);
		return this;
	}

	public JLang item(Identifier item, String name) {
		return this.object("item", item, name);
	}

	private JLang object(String type, Identifier identifier, String translation) {
		this.lang.put(type + '.' + identifier.getNamespace() + '.' + identifier.getPath(), translation);
		return this;
	}

	public JLang block(Identifier block, String name) {
		return this.object("block", block, name);
	}

	public JLang itemGroup(Identifier id, String name) {
		return this.object("itemGroup", id, name);
	}

	public JLang fluid(Identifier id, String name) {
		return this.object("fluid", id, name);
	}

	public JLang sound(Identifier id, String name) {
		return this.object("sound_event", id, name);
	}

	public JLang status(Identifier id, String name) {
		return this.object("mob_effect", id, name);
	}

	public JLang enchantment(Identifier id, String name) {
		return this.object("enchantment", id, name);
	}

	public JLang entity(Identifier id, String name) {
		return this.object("entity_type", id, name);
	}

	public JLang potion(Identifier id, String name) {
		return this.object("potion", id, name);
	}

	public JLang biome(Identifier id, String name) {
		return this.object("biome", id, name);
	}

	public Map<String, String> getLang() {
		return this.lang;
	}

	@Override
	public JLang clone() {
		try {
			return (JLang) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
