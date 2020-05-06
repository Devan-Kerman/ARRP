package net.devtech.arrp.util;

import java.util.AbstractList;
import java.util.List;

public class IrremovableList<T> extends AbstractList<T> {
	private final List<T> list;

	public IrremovableList(List<T> list) {this.list = list;}

	@Override
	public T get(int index) {
		return this.list.get(index);
	}

	@Override
	public void add(int index, T element) {
		this.list.add(index, element);
	}

	@Override
	public int size() {
		return this.list.size();
	}
}
