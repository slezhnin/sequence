package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.function.Consumer;

public class PeakingIterable<T> extends WrappingIterable<T> {

    private final Consumer<? super T> action;

    public PeakingIterable(
        final Iterable<T> sourceIterable,
        final Consumer<? super T> action
    ) {
        super(sourceIterable);
        this.action = requireNonNull(action);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(super.iterator());
    }

    private class Iter implements Iterator<T> {

        private final Iterator<T> iterator;

        private Iter(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            final var element = iterator.next();

            action.accept(element);

            return element;
        }
    }
}
