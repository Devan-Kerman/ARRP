package net.devtech.arrp.json.animation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @see #animation()
 * @see #frame(int)
 */
public class JAnimation implements Cloneable {
	private Boolean interpolate;
	private Integer width;
	private Integer height;
	private Integer frametime;
	private List<JFrame> frames;
	private List<Integer> defaultFrames;

	public static JAnimation animation() {
		return new JAnimation();
	}

	public static JFrame frame(int index) {
		return new JFrame(index);
	}

	@Override
	public JAnimation clone() {
		try {
			return (JAnimation) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public JAnimation interpolate() {
		this.interpolate = false;
		return this;
	}

	public JAnimation width(int width) {
		this.width = width;
		return this;
	}

	public JAnimation height(int height) {
		this.height = height;
		return this;
	}

	public JAnimation frameTime(int time) {
		this.frametime = time;
		return this;
	}

	public JAnimation add(int frame) {
		if (this.defaultFrames == null) {
			this.defaultFrames = new ArrayList<>();
		}
		this.defaultFrames.add(frame);
		return this;
	}

	public JAnimation add(JFrame frame) {
		if (this.frames == null) {
			this.frames = new ArrayList<>();
		}
		this.frames.add(frame);
		return this;
	}

	public static class Serializer implements JsonSerializer<JAnimation> {
		@Override
		public JsonElement serialize(JAnimation src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			{
				JsonObject animation = new JsonObject();
				if (src.interpolate != null) {
					animation.addProperty("interpolate", src.interpolate);
				}
				if (src.width != null) {
					animation.addProperty("width", src.width);
				}
				if (src.height != null) {
					animation.addProperty("height", src.height);
				}
				if (src.frametime != null) {
					animation.addProperty("frametime", src.frametime);
				}
				JsonArray frames = new JsonArray();
				if (src.frames != null) {
					for (JFrame frame : src.frames) {
						frames.add(context.serialize(frame));
					}
				}
				if (src.defaultFrames != null) {
					for (Integer frame : src.defaultFrames) {
						frames.add(frame);
					}
				}
				if (frames.size() > 0) {
					animation.add("frames", frames);
				}

				object.add("animation", animation);
			}
			return object;
		}
	}
}
