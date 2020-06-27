package net.devtech.arrp.json.loot;

import java.util.ArrayList;
import java.util.List;

public class JLootTable implements Cloneable {
	private final String type;
	private List<JPool> pools;

	/**
	 * @see JLootTable#loot(String)
	 */
	public JLootTable(String type) {this.type = type;}

	public static JLootTable loot(String type) {
		return new JLootTable(type);
	}

	public static JEntry entry() {
		return new JEntry();
	}

	public static JCondition condition(String condition) {
		return new JCondition(condition);
	}

	public static JFunction function(String function) {
		return new JFunction(function);
	}

	public static JPool pool() {
		return new JPool();
	}

	public static JRoll roll(int min, int max) {
		return new JRoll(min, max);
	}

	public JLootTable pool(JPool pool) {
		if (this.pools == null) {
			this.pools = new ArrayList<>(1);
		}
		this.pools.add(pool);
		return this;
	}

	@Override
	public JLootTable clone() {
		try {
			return (JLootTable) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
