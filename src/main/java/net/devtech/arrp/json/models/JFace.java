package net.devtech.arrp.json.models;

import net.minecraft.util.math.Direction;

public class JFace implements Cloneable {
	private final float[] uv = new float[4];
	private final String texture;
	private String cullface;
	private Integer rotation;
	private Integer tintIndex;

	/**
	 * @see JModel#face(String)
	 */
	public JFace(String texture) {
		this.texture = '#' + texture;
	}

	public JFace uv(float x1, float y1, float x2, float y2) {
		this.uv[0] = x1;
		this.uv[1] = y1;
		this.uv[2] = x2;
		this.uv[3] = y2;
		return this;
	}

	public JFace cullface(Direction direction) {
		this.cullface = direction.asString();
		return this;
	}

	public JFace rot90() {
		this.rotation = 90;
		return this;
	}

	public JFace rot180() {
		this.rotation = 180;
		return this;
	}

	public JFace rot270() {
		this.rotation = 270;
		return this;
	}

	public JFace tintIndex(int index) {
		this.tintIndex = index;
		return this;
	}

	@Override
	public JFace clone() {
		try {
			return (JFace) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
