package net.devtech.arrp.json.models;

public class JElement implements Cloneable {
	private final float[] from = new float[3];
	private final float[] to = new float[3];
	private JRotation rotation;
	private Boolean shade;
	private JFaces faces;

	/**
	 * @see JModel#element()
	 */
	public JElement() {}

	public JElement from(float x, float y, float z) {
		this.from[0] = x;
		this.from[1] = y;
		this.from[2] = z;
		return this;
	}

	public JElement to(float x, float y, float z) {
		this.to[0] = x;
		this.to[1] = y;
		this.to[2] = z;
		return this;
	}

	public JElement rotation(JRotation rotation) {
		this.rotation = rotation;
		return this;
	}

	public JElement shade() {
		this.shade = false;
		return this;
	}

	public JElement faces(JFaces faces) {
		this.faces = faces;
		return this;
	}

	@Override
	public JElement clone() {
		try {
			return (JElement) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
