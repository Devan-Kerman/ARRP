package net.devtech.rrp.data;

import net.devtech.rrp.AbstractRuntimePack;
import net.devtech.rrp.RRP;
import net.minecraft.util.Identifier;
import sun.misc.IOUtils;
import java.io.IOException;
import java.io.InputStream;

public class RuntimeDatapackImpl extends AbstractRuntimePack implements RuntimeDatapack {
	@Override
	public String getName() {
		return "RRP_server";
	}

	@Override
	public void registerDefaultBlockLootTable(Identifier block, Identifier item) {
		this.registerTemplatedResource(fix(block, "loot_tables/blocks", "json"), "{\"type\":\"minecraft:block\",\"pools\":[{\"rolls\":1,\"entries\":[{\"type\":\"minecraft:item\",\"name\":\"{0}\"}],\"conditions\":[{\"condition\":\"minecraft" +
		                                                                          ":survives_explosion\"}]}]}", item);
	}

	@Override
	public void registerAggregateBlockLootTable(Identifier block, Identifier piece, int max, int min) {
		this.registerTemplatedResource(fix(block, "loot_tables/blocks", "json"), getTemplate("aggregate"), block, piece, min, max);
	}

	@Override
	public void registerCobbleBlockLootTable(Identifier block, Identifier blockItem, Identifier cobble) {
		this.registerTemplatedResource(fix(block, "loot_tables/blocks", "json"), getTemplate("cobble"), blockItem, cobble);
	}

	@Override
	public void registerLootTable(Identifier identifier, String table) {
		this.registerRawStringResource(fix(identifier, "loot_tables", "json"), table);
	}


	private static String getTemplate(String temp) {
		InputStream stream = RRP.class.getResourceAsStream("/templates/"+temp+".temp");
		try {
			return new String(IOUtils.readFully(stream, -1, true));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
