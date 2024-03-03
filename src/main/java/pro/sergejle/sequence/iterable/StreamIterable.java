package pro.sergejle.sequence.iterable;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamIterable<T> implements Iterable<T> {

    private final Stream<T> sourceStream;

    public StreamIterable(final Stream<T> sourceStream) {
        this.sourceStream = requireNonNull(sourceStream);
    }

    @Override
    public Iterator<T> iterator() {
        return sourceStream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return sourceStream.spliterator();
    }
}
