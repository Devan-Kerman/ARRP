package net.devtech.arrp.json.blockstate;

public class JBlockModel {
	private final String model;
	private Integer x;
	private Integer y;
	private Boolean uvlock;

	/**
	 * @see JState#model(String)
	 */
	JBlockModel(String model) {
		this.model = model;
	}

	public JBlockModel x(int x) {
		this.x = x;
		return this;
	}

	public JBlockModel y(int y) {
		this.y = y;
		return this;
	}

	public JBlockModel uvlock() {
		this.uvlock = true;
		return this;
	}

	@Override
	public JBlockModel clone() {
		try {
			return (JBlockModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

}
