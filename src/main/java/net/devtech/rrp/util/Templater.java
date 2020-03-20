package net.devtech.rrp.util;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Templater {
	private final BufferedImage template;

	public Templater(BufferedImage image) {this.template = image;}

	public Templater(byte[] data) throws IOException {
		this(ImageIO.read(new ByteArrayInputStream(data)));
	}

	public Templater(InputStream stream) throws IOException {
		this(ImageIO.read(stream));
	}

	public BufferedImage transform(Int2IntFunction transformer) {
		BufferedImage image = new BufferedImage(this.template.getWidth(), this.template.getHeight(), this.template.getType());
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				image.setRGB(x, y, this.template.getRGB(x, y));
			}
		}
		return image;
	}

	/**
	 * a templater that simply replaces the pixels using a map
	 */
	public static class Replacer implements Int2IntFunction {
		protected final Int2IntMap colors = new Int2IntOpenHashMap();

		public void addReplacement(int rgbTemplate, int rgbNew) {
			this.colors.put(rgbTemplate, rgbNew);
		}


		@Override
		public int get(int key) {
			return this.colors.getOrDefault(key, key);
		}
	}

	/**
	 * a templating function that paints an image with the given color
	 */
	public static class Painter implements Int2IntFunction {
		private final int red;
		private final int green;
		private final int blue;
		private final int alpha;

		public Painter(Color color) {this(color.getRGB());}

		public Painter(int rgbPaint) {
			this.alpha = rgbPaint >> 24 & 0xff;
			this.red = rgbPaint >> 16 & 0xff;
			this.green = rgbPaint >> 8 & 0xff;
			this.blue = rgbPaint & 0xff;
		}

		@Override
		public int get(int key) {
			int newAlpha = (int) ((key >> 24 & 0xff) / 255f * this.alpha);
			int newRed = (int) ((key >> 16 & 0xff) / 255f * this.red);
			int newGreen = (int) ((key >> 8 & 0xff) / 255f * this.green);
			int newBlue = (int) ((key & 0xff) / 255f * this.blue);
			return newAlpha << 24 | newRed << 16 | newGreen << 8 | newBlue;
		}
	}
}
