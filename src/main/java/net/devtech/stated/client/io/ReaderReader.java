package net.devtech.stated.client.io;

import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.Arrays;

public class ReaderReader extends Reader {
	private final Reader reader;
	public ReaderReader(Reader reader) {
		this.stream = System.out;
		this.reader = reader;
	}
	private final PrintStream stream;

	@Override
	public int read(@NotNull char[] cbuf, int off, int len) throws IOException {
		int red = this.reader.read(cbuf, off, len);
		String string = new String(cbuf, off, len);
		if(string.startsWith("{{{{{{{{{{{{{{{{{{{{")) {
			this.stream.println(string);
			new Throwable().printStackTrace();
			throw new IOException("what the fuck");
		}

		return red;
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}
}
