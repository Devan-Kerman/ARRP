package net.devtech.arrp.util;

import java.io.OutputStream;
import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

public class UnsafeByteArrayOutputStream extends OutputStream implements AutoCloseable {
	protected byte[] buf;
	protected int index;

	public UnsafeByteArrayOutputStream() {
		this(128);
	}

	public UnsafeByteArrayOutputStream(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Negative initial size: " + size);
		}
		this.buf = new byte[size];
	}

	@Override
	public void write(int b) {
		this.ensureCapacity(this.index + 1);
		this.buf[this.index++] = (byte) b;
	}

	private void ensureCapacity(int minCapacity) {
		int len = this.buf.length;
		if (minCapacity > len) {
			int size = Math.max(minCapacity, len / 2 + len);
			this.buf = Arrays.copyOf(this.buf, size);
		}
	}

	@Override
	public void write(@NotNull byte b[], int off, int len) {
		if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) - b.length > 0)) {
			throw new IndexOutOfBoundsException();
		}
		this.ensureCapacity(this.index + len);
		System.arraycopy(b, off, this.buf, this.index, len);
		this.index += len;
	}

	public byte[] getBytes() {
		return Arrays.copyOf(this.buf, this.index);
	}
}
