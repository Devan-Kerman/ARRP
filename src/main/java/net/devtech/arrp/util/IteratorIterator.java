package net.devtech.arrp.util;

import java.util.Iterator;

public class IteratorIterator<T> implements Iterator<T> {
	private static final Iterator EMPTY = new Iterator() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			throw new UnsupportedOperationException();
		}
	};

	private final Iterator<T>[] iterators;
	private int index;

	@SafeVarargs
	public IteratorIterator(Iterable<T>... iterables) {
		this(new Iterator[iterables.length]);
		if (iterables.length > 0) {
			for (int i = 0; i < iterables.length; i++) {
				this.iterators[i] = iterables[i].iterator();
			}
		}
	}

	@SafeVarargs
	public IteratorIterator(Iterator<T>... iterators) {
		this.iterators = iterators;
	}

	private Iterator<T> get(boolean update) {
		int index = this.index;
		if (index < this.iterators.length) {
			Iterator<T> iterator = this.iterators[index];
			if (iterator.hasNext()) {
				return iterator;
			} else {
				this.index = index + 1;
				Iterator<T> ret = this.get(update);
				if (!update) {
					this.index = index;
				}
				return ret;
			}
		}

		return EMPTY;
	}

	@Override
	public boolean hasNext() {
		return this.get(false)
		           .hasNext();
	}

	@Override
	public T next() {
		return this.get(true)
		           .next();
	}

	@Override
	public void remove() {
		this.get(false)
		    .remove();
	}
}
