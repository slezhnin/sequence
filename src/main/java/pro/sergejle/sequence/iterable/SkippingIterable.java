package pro.sergejle.sequence.iterable;

import java.util.Iterator;

public class SkippingIterable<T> extends WrappingIterable<T> {

    private final long nSkip;

    public SkippingIterable(
        final Iterable<T> sourceIterable,
        final long nSkip
    ) {
        super(sourceIterable);
        this.nSkip = nSkip;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(super.iterator());
    }

    private class Iter implements Iterator<T> {

        private final Iterator<T> iterator;

        private Iter(final Iterator<T> iterator) {
            this.iterator = iterator;

            skip();
        }

        private void skip() {
            for (long counter = nSkip; counter > 0; --counter) {
                if (!iterator.hasNext()) {
                    break;
                }

                iterator.next();
            }
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }
    }
}
