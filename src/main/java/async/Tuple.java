package async;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class that lets a user store an indefinite amount of unrelated items. The stored values are effectively final, as this class
 * does not provide a method to change the array that internally stores them.
 */
public class Tuple implements Iterable<Object> {
    private final Object[] values;

    public Tuple(Object... objects) {
        Objects.requireNonNull(objects);
        this.values = objects;
    }

    public int length() {
        return values.length;
    }

    /**
     * @param index the position in the internal array where the class-value should be retrieved
     * @return the class of the value stored at this index or {@link Void} when the position is out of the array or
     * occupied by a null value
     */
    public Class<?> getClassAt(int index) {
        if (valueAtExists(index)) {
            return getAt(index).getClass();
        } else {
            return Void.class;
        }
    }

    /**
     * @param index the position in the internal array where the class-value should be retrieved
     * @param <T>   The class to which the value should be cast
     * @return the value at the stored position
     * @throws NoSuchElementException when the specified index is out of bounds or stores a null-value
     * @throws ClassCastException     when attempting to cast the stored value to an incompatible class
     */
    @SuppressWarnings("unchecked")
    public <T> T getAt(int index) {
        if (valueAtExists(index)) {
            return (T) values[index];
        } else throw new NoSuchElementException();
    }

    /**
     * @param index the position in the internal array where the class-value should be retrieved
     * @return true when any non-null-value is stored at that index or
     * false when the index is out of bounds or a null-value is stored at that index
     */
    public boolean valueAtExists(int index) {
        return index < values.length && values[index] != null;
    }

    /**
     * @return an untyped Stream of all stored values
     * Note that the existing stream may contain null-values
     */
    public Stream<?> stream() {
        return Arrays.stream(values);
    }

    /**
     * @return an untyped Stream of all stored values
     * The stream won't contain null-values
     */
    public Stream<?> streamNoNullValues() {
        return stream().filter(Objects::nonNull);
    }

    /**
     * @return an iterator over the internal array
     * If cast to {@link TupleIterator} it will provide a generic {@link TupleIterator#nextValue()} operation that
     * casts the next Value to the specified generic type. Also {@link TupleIterator#hasNextNoNull()} allows the iterator
     * to skip null values
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleIterator();
    }

    private class TupleIterator implements Iterator<Object> {
        private int current = 0;


        @Override
        public boolean hasNext() {
            return current < values.length;
        }

        /**
         * This method will advance the iterator over null values and effectively ignore them
         *
         * @return true when there is a non-null left or false if not
         */
        public boolean hasNextNoNull() {
            if (current < values.length) {
                if (valueAtExists(current)) {
                    return true;
                } else {
                    current++;
                    return hasNext();
                }
            } else {
                return false;
            }
        }

        @Override
        public Object next() {
            return getAt(current++);
        }

        /**
         * @param <T> the type the next() object should be cast to
         * @return the next value cast as the specified type
         * @throws ClassCastException if the next value cannot be cast to T
         */
        @SuppressWarnings("unchecked")
        public <T> T nextValue() {
            return (T) next();
        }
    }
}
