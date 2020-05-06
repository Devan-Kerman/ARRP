package net.devtech.arrp.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {

	public static BufferedImage colorImage(BufferedImage base, int tr, int tg, int tb) throws IOException {
		BufferedImage image = new BufferedImage(base.getWidth(), base.getHeight(), base.getType());
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = base.getRGB(x, y);
				int a = rgb >> 24 & 0xFF, r = rgb >> 16 & 0xFF, g = rgb >> 8 & 0xFF, b = rgb & 0xFF;
				rgb = a << 24;
				if (a != 0) {
					rgb |= sigmoid((int) (r + ((1 - r / 255D) * tr))) << 16;
					rgb |= sigmoid((int) (g + ((1 - g / 255D) * tg))) << 8;
					rgb |= sigmoid((int) (b + ((1 - b / 255D) * tb)));
				}
				image.setRGB(x, y, rgb);
			}
		}

		return image;
	}

	private static int sigmoid(int x) {
		return (int) (256 * (((x - 128D) / 128D) / (1 + Math.abs((x - 128D) / 128D))) + 128);
	}
}
