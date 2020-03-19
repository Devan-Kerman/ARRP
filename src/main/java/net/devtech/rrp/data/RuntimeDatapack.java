package net.devtech.rrp.data;

import net.minecraft.util.Identifier;
import java.util.function.Supplier;

public interface RuntimeDatapack {
	/**
	 * create a default block loot table, like the one shown in the fabric
	 * tutorial
	 * @param block the block loot table
	 * @param item the block item
	 */
	void registerDefaultBlockLootTable(Identifier block, Identifier item);

	/**
	 * create a redstone ore / glowstone block like loot table
	 * for the block, can be picked up with silk touch and is influenced by
	 * fortune
	 * @param block the block id
	 * @param piece the "piece" id eg. redstone dust
	 * @param max the maximum number of pieces eg. 4
	 * @param min the minimum number of pieces eg. 3
	 */
	void registerAggregateBlockLootTable(Identifier block, Identifier piece, int max, int min);

	/**
	 * register a raw resource, this is like an artificial file
	 * @param identifier the id of the file
	 * @param data the byte data
	 */
	void registerRawResource(Identifier identifier, Supplier<byte[]> data);

	/**
	 * @see #registerRawResource(Identifier, Supplier)
	 */
	void registerRawStringResource(Identifier identifier, String data);

	/**
	 * register a block loot table resembling that of cobblestone
	 * @param block the id of the block
	 * @param blockItem the dropped id
	 * @param cobble the "cobblestone" of this block
	 */
	void registerCobbleBlockLootTable(Identifier block, Identifier blockItem, Identifier cobble);

	/**
	 * register a raw loot table
	 * @param identifier the id of the table
	 * @param table the string json
	 */
	void registerLootTable(Identifier identifier, String table);


	/**
	 * register a resource with a pre-made format using the java message format
	 * @see java.text.MessageFormat
	 * @param path the place to store the newly generated resource
	 * @param template the template
	 * @param args the args to pass to the message format
	 */
	void registerTemplatedResource(Identifier path, String template, Object... args);
}
