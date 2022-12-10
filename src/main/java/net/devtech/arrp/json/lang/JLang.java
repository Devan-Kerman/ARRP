package net.devtech.arrp.json.lang;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Adds a custom entry to the lang file. (deprecated: renamed to a more intuitive name)
     *
     * @param in  the translation string
     * @param out the in-game name of the object
     * @return the file with the new entry.
     * @deprecated use {@link #entry(String, String)} instead.
     */
    @Deprecated
    public JLang translate(String in, String out) {
        this.lang.put(in, out);
        return this;
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

    public JLang entry(String entry, String name) {
        this.lang.put(entry, name);
        return this;
    }


    /**
     * adds a translation key for an item, respects {@link Item#getTranslationKey()}
     */
    public JLang itemRespect(Item item, String name) {
        this.lang.put(item.getTranslationKey(), name);
        return this;
    }

    public JLang item(ItemStack stack, String name) {
        this.lang.put(stack.getTranslationKey(), name);
        return this;
    }

    /**
     * @see JLang#itemRespect(Item, String) uses the {@link Item#getTranslationKey()}}
     */
    @Deprecated
    public JLang item(Item item, String name) {
        return this.object(Registries.ITEM, "item", item, name);
    }

    /**
     * adds a translation key for an block, respects {@link Block#getTranslationKey()}
     */
    public JLang blockRespect(Block block, String name) {
        this.lang.put(block.getTranslationKey(), name);
        return this;
    }

    /**
     * @see JLang#blockRespect(Block, String) uses the {@link Block#getTranslationKey()}}
     */
    @Deprecated
    public JLang block(Block block, String name) {
        return this.object(Registries.BLOCK, "block", block, name);
    }

    public JLang fluid(Fluid fluid, String name) {
        return this.object(Registries.FLUID, "fluid", fluid, name);
    }

    /**
     * adds a translation key for an entity, respects {@link EntityType#getTranslationKey()}
     */
    public JLang entityRespect(EntityType<?> type, String name) {
        this.lang.put(type.getTranslationKey(), name);
        return this;
    }

    /**
     * @see JLang#entityRespect(EntityType, String) uses the {@link EntityType#getTranslationKey()}}
     */
    @Deprecated
    public JLang entity(EntityType<?> type, String name) {
        return this.object(Registries.ENTITY_TYPE, "entity_type", type, name);
    }

    /**
     * adds a translation key for an entity, respects {@link Enchantment#getTranslationKey()}
     */
    public JLang enchantmentRespect(Enchantment enchantment, String name) {
        this.lang.put(enchantment.getTranslationKey(), name);
        return this;
    }

    /**
     * @see JLang#enchantmentRespect(Enchantment, String) uses the {@link Enchantment#getTranslationKey()}}
     */
    @Deprecated
    public JLang enchantment(Enchantment enchantment, String name) {
        return this.object(Registries.ENCHANTMENT, "enchantment", enchantment, name);
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
