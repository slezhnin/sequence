package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.Spliterator;

public class WrappingIterable<T> implements Iterable<T> {

    private final Iterable<T> sourceIterable;

    public WrappingIterable(
        final Iterable<T> sourceIterable
    ) {
        this.sourceIterable = requireNonNull(sourceIterable);
    }

    @Override
    public Iterator<T> iterator() {
        return sourceIterable.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return sourceIterable.spliterator();
    }
}
