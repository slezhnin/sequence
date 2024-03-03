package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class ArrayIterable<T> implements Iterable<T> {

    private final T[] array;

    public ArrayIterable(final T[] array) { this.array = requireNonNull(array); }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(array, 0);
    }

    private class Iter implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public T next() {
            return array[index++];
        }
    }
}
