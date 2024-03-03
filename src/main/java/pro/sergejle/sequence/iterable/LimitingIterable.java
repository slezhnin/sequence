package pro.sergejle.sequence.iterable;

import java.util.Iterator;

public class LimitingIterable<T> extends WrappingIterable<T> {

    private final long limit;

    public LimitingIterable(
        final Iterable<T> sourceIterable,
        final long limit
    ) {
        super(sourceIterable);
        this.limit = limit;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(super.iterator(), limit);
    }

    private class Iter implements Iterator<T> {

        private final Iterator<T> iterator;
        private long counter = limit;

        private Iter(
            final Iterator<T> iterator,
            final long limit
        ) {
            this.iterator = iterator;
            this.counter = limit;
        }

        @Override
        public boolean hasNext() {
            if (counter <= 0) {
                return false;
            }

            return iterator.hasNext();
        }

        @Override
        public T next() {
            if (counter <= 0) {
                throw new IllegalStateException("Iterator has reached the limit of " + limit);
            }

            --counter;

            return iterator.next();
        }
    }
}
