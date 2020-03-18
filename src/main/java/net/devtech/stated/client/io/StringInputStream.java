package net.devtech.stated.client.io;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {
	private int currentByte;
	private final byte[] string;

	public StringInputStream(String string) {this.string = string.getBytes();}

	@Override
	public int read() throws IOException {
		if(this.currentByte >= this.string.length)
			return -1;
		return this.string[this.currentByte++];
	}
}
