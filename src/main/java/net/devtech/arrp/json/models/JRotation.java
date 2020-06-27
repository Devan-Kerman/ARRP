package net.devtech.arrp.json.models;

import net.minecraft.util.math.Direction;

public class JRotation {
	private final float[] origin = new float[3];
	private final char axis;
	private Float angle;
	private Boolean rescale;

	/**
	 * @see JModel#rotation(Direction.Axis)
	 */
	public JRotation(Direction.Axis axis) {
		this.axis = axis.asString()
		                .charAt(0);
	}

	public JRotation origin(float x, float y, float z) {
		this.origin[0] = x;
		this.origin[1] = y;
		this.origin[2] = z;
		return this;
	}

	public JRotation angle(Float angle) {
		this.angle = angle;
		return this;
	}

	public JRotation rescale() {
		this.rescale = true;
		return this;
	}

	@Override
	public JRotation clone() {
		try {
			return (JRotation) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
