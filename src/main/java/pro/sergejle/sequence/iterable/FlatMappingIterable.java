package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.function.Function;

public class FlatMappingIterable<T, R> implements Iterable<R> {

    private final Iterable<T> sourceIterable;
    private final Function<? super T, ? extends Iterable<? extends R>> mapper;

    public FlatMappingIterable(
        final Iterable<T> sourceIterable,
        final Function<? super T, ? extends Iterable<? extends R>> mapper
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
        private Iterator<? extends R> current = EmptyIterable
            .<R>emptyIterable()
            .iterator();

        private Iter(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            if (!current.hasNext()) {
                while (iterator.hasNext()) {
                    current = mapper
                        .apply(iterator.next())
                        .iterator();
                    if (current.hasNext()) { break; }
                }
            }

            return current.hasNext();
        }

        @Override
        public R next() {
            return current.next();
        }
    }
}
