package net.devtech.arrp.json.loot;

public class JRoll implements Cloneable {
	private final int min;
	private final int max;

	/**
	 * @see JLootTable#roll(int, int)
	 */
	public JRoll(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public JRoll clone() {
		try {
			return (JRoll) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
