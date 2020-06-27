package net.devtech.arrp.util;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Consumer;

public class IrremovableList<T> extends AbstractList<T> {
	private final List<T> list;
	private final Consumer<T> action;

	public IrremovableList(List<T> list, Consumer<T> action) {
		this.list = list;
		this.action = action;
	}

	@Override
	public T get(int index) {
		return this.list.get(index);
	}

	@Override
	public void add(int index, T element) {
		this.action.accept(element);
		this.list.add(index, element);
	}

	@Override
	public int size() {
		return this.list.size();
	}
}
