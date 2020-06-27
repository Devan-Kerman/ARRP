package net.devtech.arrp.json.models;

public class JDisplay implements Cloneable {
	private JPosition thirdperson_righthand;
	private JPosition thirdperson_lefthand;
	private JPosition firstperson_righthand;
	private JPosition firstperson_lefthand;
	private JPosition gui;
	private JPosition head;
	private JPosition ground;
	private JPosition fixed;

	/**
	 * @see JModel#display()
	 */
	public JDisplay() {}

	public JDisplay setThirdperson_righthand(JPosition thirdperson_righthand) {
		this.thirdperson_righthand = thirdperson_righthand;
		return this;
	}

	public JDisplay setThirdperson_lefthand(JPosition thirdperson_lefthand) {
		this.thirdperson_lefthand = thirdperson_lefthand;
		return this;
	}

	public JDisplay setFirstperson_righthand(JPosition firstperson_righthand) {
		this.firstperson_righthand = firstperson_righthand;
		return this;
	}

	public JDisplay setFirstperson_lefthand(JPosition firstperson_lefthand) {
		this.firstperson_lefthand = firstperson_lefthand;
		return this;
	}

	public JDisplay setGui(JPosition gui) {
		this.gui = gui;
		return this;
	}

	public JDisplay setHead(JPosition head) {
		this.head = head;
		return this;
	}

	public JDisplay setGround(JPosition ground) {
		this.ground = ground;
		return this;
	}

	public JDisplay setFixed(JPosition fixed) {
		this.fixed = fixed;
		return this;
	}

	@Override
	public JDisplay clone() {
		try {
			return (JDisplay) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
