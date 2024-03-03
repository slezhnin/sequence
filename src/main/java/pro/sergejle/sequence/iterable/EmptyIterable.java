package pro.sergejle.sequence.iterable;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

@SuppressWarnings("unchecked")
public class EmptyIterable<T> implements Iterable<T> {

    private final static Iterable<?> EMPTY_ITERABLE = new EmptyIterable<>();
    private final static Iterator<?> EMPTY_ITERATOR = new Iter<>();

    public static <T> Iterable<T> emptyIterable() {
        return (Iterable<T>) EMPTY_ITERABLE;
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) EMPTY_ITERATOR;
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.emptySpliterator();
    }

    private static class Iter<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
