package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.function.Function;

public class MappingIterable<T, R> implements Iterable<R> {

    private final Iterable<T> sourceIterable;
    private final Function<? super T, ? extends R> mapper;

    public MappingIterable(
        final Iterable<T> sourceIterable,
        final Function<? super T, ? extends R> mapper
    ) {
        this.sourceIterable = requireNonNull(sourceIterable);
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public Iterator<R> iterator() {
        return new Iter(sourceIterable.iterator());
    }

    private class Iter implements Iterator<R> {

        private final Iterator<T> iterator;

        private Iter(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public R next() {
            return mapper.apply(iterator.next());
        }
    }
}
