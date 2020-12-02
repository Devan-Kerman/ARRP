package net.devtech.arrp.json.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class JLang implements Cloneable {
	private final Map<String, String> lang = new HashMap<>();

	/**
	 * @see #lang()
	 */
	public JLang() {
	}

	public static JLang lang() {
		return new JLang();
	}

	public JLang translate(String in, String out) {
		this.lang.put(in, out);
		return this;
	}

	public JLang item(Item item, String name) {
		return this.object(Registry.ITEM, "item", item, name);
	}

	private <T> JLang object(Registry<T> registry, String str, T t, String name) {
		return this.object(str,
				Objects.requireNonNull(registry.getId(t), "register your item before calling this"),
				name);
	}

	private JLang object(String type, Identifier identifier, String translation) {
		this.lang.put(type + '.' + identifier.getNamespace() + '.' + identifier.getPath(), translation);
		return this;
	}

	public JLang block(Block block, String name) {
		return this.object(Registry.BLOCK, "block", block, name);
	}

	public JLang fluid(Fluid id, String name) {
		return this.object(Registry.FLUID, "fluid", id, name);
	}

	public JLang entity(EntityType<?> id, String name) {
		return this.object(Registry.ENTITY_TYPE, "entity_type", id, name);
	}

	public JLang enchantment(Enchantment id, String name) {
		return this.object(Registry.ENCHANTMENT, "enchantment", id, name);
	}

	public JLang item(Identifier item, String name) {
		return this.object("item", item, name);
	}

	public JLang block(Identifier block, String name) {
		return this.object("block", block, name);
	}

	public JLang fluid(Identifier id, String name) {
		return this.object("fluid", id, name);
	}

	public JLang entity(Identifier id, String name) {
		return this.object("entity_type", id, name);
	}

	public JLang enchantment(Identifier id, String name) {
		return this.object("enchantment", id, name);
	}

	public JLang itemGroup(Identifier id, String name) {
		return this.object("itemGroup", id, name);
	}

	public JLang sound(Identifier id, String name) {
		return this.object("sound_event", id, name);
	}

	public JLang status(Identifier id, String name) {
		return this.object("mob_effect", id, name);
	}

	/**
	 * Like {@link JLang#allPotion}, but it adds in the prefixes automatically.
	 */
	public JLang allPotionOf(Identifier id, String effectName) {
		this.allPotion(id,
				"Potion of " + effectName,
				"Splash Potion of " + effectName,
				"Lingering Potion of " + effectName,
				"Tipped Arrow of " + effectName);
		return this;
	}

	public JLang allPotion(Identifier id,
			String drinkablePotionName,
			String splashPotionName,
			String lingeringPotionName,
			String tippedArrowName) {
		return this.drinkablePotion(id, drinkablePotionName).splashPotion(id, splashPotionName)
		           .lingeringPotion(id, lingeringPotionName).tippedArrow(id, tippedArrowName);
	}

	public JLang tippedArrow(Identifier id, String name) {
		this.lang.put("item.minecraft.tipped_arrow.effect." + id.getPath(), name);
		return this;
	}

	public JLang lingeringPotion(Identifier id, String name) {
		this.lang.put("item.minecraft.lingering_potion.effect." + id.getPath(), name);
		return this;
	}

	public JLang splashPotion(Identifier id, String name) {
		this.lang.put("item.minecraft.splash_potion.effect." + id.getPath(), name);
		return this;
	}

	public JLang drinkablePotion(Identifier id, String name) {
		this.lang.put("item.minecraft.potion.effect." + id.getPath(), "Potion of " + name);
		return this;
	}

	/**
	 * Like {@link JLang#drinkablePotion}, but it adds in the "Potion of" automatically.
	 */
	public JLang drinkablePotionOf(Identifier id, String effectName) {
		this.lang.put("item.minecraft.potion.effect." + id.getPath(), "Potion of " + effectName);
		return this;
	}

	/**
	 * Like {@link JLang#splashPotion}, but it adds in the "Splash Potion of" automatically.
	 */
	public JLang splashPotionOf(Identifier id, String effectName) {
		this.lang.put("item.minecraft.splash_potion.effect." + id.getPath(), "Splash Potion of " + effectName);
		return this;
	}

	/**
	 * Like {@link JLang#lingeringPotion}, but it adds in the "Lingering Potion of" automatically.
	 */
	public JLang lingeringPotionOf(Identifier id, String effectName) {
		this.lang.put("item.minecraft.lingering_potion.effect." + id.getPath(), "Lingering Potion of " + effectName);
		return this;
	}

	/**
	 * Like {@link JLang#tippedArrow}, but it adds in the "Tipped Arrow of" automatically.
	 */
	public JLang tippedArrowOf(Identifier id, String effectName) {
		this.lang.put("item.minecraft.tipped_arrow.effect." + id.getPath(), "Tipped Arrow of " + effectName);
		return this;
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
