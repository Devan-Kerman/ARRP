package net.devtech.rrp.util.recipies;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * this class is for the recipe choices for a crafting recipe
 *
 * @implNote do <b>not</b> implement this class
 */
public interface RecipeChoice {
	final class Item implements RecipeChoice {
		public final String item;

		public Item(@NotNull Identifier item) {this.item = item.toString();}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Item)) return false;

			Item item1 = (Item) o;

			return this.item.equals(item1.item);
		}

		@Override
		public int hashCode() {
			return this.item.hashCode();
		}
	}

	final class Tag implements RecipeChoice {
		public final String tag;

		public Tag(@NotNull Identifier tag) {this.tag = tag.toString();}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Tag)) return false;

			Tag tag1 = (Tag) o;

			return this.tag.equals(tag1.tag);
		}

		@Override
		public int hashCode() {
			return this.tag.hashCode();
		}
	}
}
