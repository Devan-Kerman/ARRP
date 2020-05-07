package net.devtech.arrp.json.models;

@SuppressWarnings ("MismatchedReadAndWriteOfArray")
public class JPosition implements Cloneable {
	private float[] rotation = new float[3];
	private float[] translation = new float[3];
	private float[] scale = new float[3];

	/**
	 * @see JModel#position()
	 */
	public JPosition() {}

	public JPosition rotation(float x, float y, float z) {
		this.rotation[0] = x;
		this.rotation[1] = y;
		this.rotation[2] = z;
		return this;
	}

	public JPosition translation(float x, float y, float z) {
		this.translation[0] = x;
		this.translation[1] = y;
		this.translation[2] = z;
		return this;
	}

	public JPosition scale(float x, float y, float z) {
		this.scale[0] = x;
		this.scale[1] = y;
		this.scale[2] = z;
		return this;
	}

	@Override
	public JPosition clone() {
		try {
			return (JPosition) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
