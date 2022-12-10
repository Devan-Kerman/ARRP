package net.devtech.arrp.json.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.devtech.arrp.json.loot.JCondition;

import net.minecraft.util.Identifier;
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
	private List<JElement> elements;
	private List<JOverride> overrides;

	/**
	 * @see #model(String)
	 * @see #model()
	 */
	public JModel() {}

	/**
	 * @return a new jmodel that does not override it's parent's elements
	 */
	public static JModel modelKeepElements() {
		JModel model = new JModel();
		model.elements = null;
		return model;
	}

	/**
	 * @return a new jmodel that does not override it's parent's elements
	 */
	public static JModel modelKeepElements(String parent) {
		JModel model = new JModel();
		model.parent = parent;
		model.elements = null;
		return model;
	}

	public static JModel modelKeepElements(Identifier identifier) {
		return modelKeepElements(identifier.toString());
	}

	public static JModel model() {
		return new JModel();
	}

	public static JModel model(String parent) {
		JModel model = new JModel();
		model.parent = parent;
		return model;
	}

	public static JOverride override(JCondition predicate, Identifier model) {
		return new JOverride(predicate, model.toString());
	}

	public static JCondition condition() {
		return new JCondition(null);
	}

	public static JModel model(Identifier identifier) {
		return model(identifier.toString());
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

	public JModel addOverride(JOverride override) {
		if(this.overrides == null) this.overrides = new ArrayList<>();
		this.overrides.add(override);
		return this;
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
		if(this.elements == null) {
			this.elements = new ArrayList<>();
		}
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
