package net.devtech.arrp.util;

public class ImageUtil {
	public static int recolor(int input, int val) {
		int a = input >> 24 & 0xFF, r = input >> 16 & 0xFF, g = input >> 8 & 0xFF, b = input & 0xFF;
		input = a << 24;
		if (a != 0) {
			input |= (int) (r + ((1 - r / 255D) * ((val >> 16) & 0xFF))) << 16;
			input |= (int) (g + ((1 - g / 255D) * ((val >> 8) & 0xFF))) << 8;
			input |= (int) (b + ((1 - b / 255D) * (val & 0xFF)));
		}
		return input;
	}

}
