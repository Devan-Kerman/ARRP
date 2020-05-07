package net.devtech.arrp.json.loot;

public class JCondition implements Cloneable {
	private final String condition;

	/**
	 * @see JLootTable#condition(String)
	 */
	public JCondition(String condition) {this.condition = condition;}

	@Override
	public JCondition clone() {
		try {
			return (JCondition) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
