package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.function.Supplier;

public class GeneratedIterable<T> implements Iterable<T> {

    private final Supplier<? extends T> nextSupplier;
    private final Supplier<Boolean> hasNextSupplier;

    public GeneratedIterable(
        final Supplier<? extends T> nextSupplier,
        final Supplier<Boolean> hasNextSupplier
    ) {
        this.nextSupplier = requireNonNull(nextSupplier);
        this.hasNextSupplier = requireNonNull(hasNextSupplier);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return hasNextSupplier.get();
        }

        @Override
        public T next() {
            return nextSupplier.get();
        }
    }
}
