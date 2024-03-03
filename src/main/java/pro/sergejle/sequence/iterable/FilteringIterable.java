package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.function.Predicate;

public class FilteringIterable<T> extends WrappingIterable<T> {

    private final Predicate<? super T> predicate;

    public FilteringIterable(
        final Iterable<T> sourceIterable,
        final Predicate<? super T> predicate
    ) {
        super(sourceIterable);
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(super.iterator());
    }

    private class Iter implements Iterator<T> {

        private final Iterator<T> sourceIterator;
        private T next;

        private Iter(final Iterator<T> sourceIterator) {
            this.sourceIterator = sourceIterator;
        }

        @Override
        public boolean hasNext() {
            return (next = findNext()) != null;
        }

        @Override
        public T next() {
            return next;
        }

        private T findNext() {
            while (sourceIterator.hasNext()) {
                final var probableNext = sourceIterator.next();

                if (predicate.test(probableNext)) {
                    return probableNext;
                }
            }

            return null;
        }
    }
}
