package net.devtech.arrp.json.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.math.Direction;

/**
 * a block/item model, static import this class
 */
public class JModel implements Cloneable {
	private String parent;
	// true is default
	private Boolean ambientocclusion;
	// some thingy idk
	private JDisplay display;
	// texture variables
	private JTextures textures;
	// make serializer
	private List<JElement> elements = new ArrayList<>();

	/**
	 * @see #model(String)
	 * @see #model()
	 */
	public JModel() {}

	public static JModel model() {
		return new JModel();
	}

	public static JModel model(String parent) {
		JModel model = new JModel();
		model.parent = parent;
		return model;
	}

	public static JDisplay display() {
		return new JDisplay();
	}

	public static JElement element() {
		return new JElement();
	}

	public static JFace face(String texture) {
		return new JFace(texture);
	}

	public static JFaces faces() {
		return new JFaces();
	}

	public static JPosition position() {
		return new JPosition();
	}

	public static JRotation rotation(Direction.Axis axis) {
		return new JRotation(axis);
	}

	public static JTextures textures() {
		return new JTextures();
	}

	public JModel parent(String parent) {
		this.parent = parent;
		return this;
	}

	public JModel noAmbientOcclusion() {
		this.ambientocclusion = false;
		return this;
	}

	public JModel display(JDisplay display) {
		this.display = display;
		return this;
	}

	public JModel textures(JTextures textures) {
		this.textures = textures;
		return this;
	}

	public JModel element(JElement... elements) {
		this.elements.addAll(Arrays.asList(elements));
		return this;
	}

	@Override
	public JModel clone() {
		try {
			return (JModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
