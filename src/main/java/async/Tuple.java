package async;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class Tuple implements Iterable<Object> {
    private final Object[] values;

    public Tuple(Object... objects) {
        Objects.requireNonNull(objects);
        this.values = objects;
    }

    public int length() {
        return values.length;
    }

    public Class<?> getClassAt(int index) {
        if (valueAtExists(index)) {
            return getAt(index).getClass();
        } else {
            return Void.class;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getAt(int index) {
        if (valueAtExists(index)) {
            return (T) values[index];
        } else throw new NoSuchElementException();
    }

    public boolean valueAtExists(int index) {
        return index < values.length && values[index] != null;
    }

    public Stream<?> stream() {
        return Arrays.stream(values);
    }

    @Override
    public Iterator<Object> iterator() {
        return new TupleIterator();
    }

    private class TupleIterator implements Iterator<Object> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return valueAtExists(current);
        }

        @Override
        public Object next() {
            return getAt(current++);
        }
    }
}
