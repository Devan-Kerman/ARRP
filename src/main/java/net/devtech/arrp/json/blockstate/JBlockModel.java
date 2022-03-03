package net.devtech.arrp.json.blockstate;

import net.minecraft.util.Identifier;

public class JBlockModel implements Cloneable {
	private final Identifier model;
	private Integer x;
	private Integer y;
	private Boolean uvlock;
	private Integer weight;

	@Deprecated
	public JBlockModel(String model) {
		this(new Identifier(model));
	}

	/**
	 * @see JState#model(String)
	 */
	public JBlockModel(Identifier model) {
		this.model = model;
	}

	@Override
	public JBlockModel clone() {
		try {
			return (JBlockModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
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

	public JBlockModel weight(int weight) {
		this.weight = weight;
		return this;
	}

}
