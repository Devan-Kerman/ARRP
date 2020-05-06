package net.devtech.arrp.json.models;

public class JFaces {
	private JFace up;
	private JFace down;
	private JFace north;
	private JFace south;
	private JFace east;
	private JFace west;

	/**
	 * @see JModel#faces()
	 */
	JFaces() {}

	public JFaces up(JFace face) {
		this.up = face;
		return this;
	}

	public JFaces down(JFace face) {
		this.down = face;
		return this;
	}

	public JFaces north(JFace face) {
		this.north = face;
		return this;
	}

	public JFaces south(JFace face) {
		this.south = face;
		return this;
	}

	public JFaces east(JFace face) {
		this.east = face;
		return this;
	}

	public JFaces west(JFace face) {
		this.west = face;
		return this;
	}
}
