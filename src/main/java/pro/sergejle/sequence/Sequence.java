package pro.sergejle.sequence;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static pro.sergejle.sequence.iterable.EmptyIterable.emptyIterable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import pro.sergejle.sequence.iterable.ArrayIterable;
import pro.sergejle.sequence.iterable.FlatMappingIterable;
import pro.sergejle.sequence.iterable.GeneratedIterable;
import pro.sergejle.sequence.iterable.StreamIterable;
import pro.sergejle.sequence.iterable.WrappingIterable;

public interface Sequence<T>
    extends FilteringSequence<T>, MappingSequence<T>, TerminatingSequence<T> {

    static <T> Sequence<T> of() {
        return new IterableSequence<>(emptyIterable());
    }

    static <T> Sequence<T> of(
        final Stream<T> sourceStream
    ) {
        requireNonNull(sourceStream);
        
        return new IterableSequence<>(new StreamIterable<>(sourceStream));

    }

    static <T> Sequence<T> of(
        final Iterable<T> sourceIterable
    ) {
        requireNonNull(sourceIterable);
        
        return new IterableSequence<>(sourceIterable);
    }

    @SafeVarargs
    static <T> Sequence<T> of(final T... values) {
        return new IterableSequence<>(new ArrayIterable<>(values));
    }

    static <T> Sequence<T> of(final T value) {
        requireNonNull(value);
        
        return new IterableSequence<>(List.of(value));
    }

    static <K, V> MapEntrySequence<K, V> ofEntries(
        final Stream<Map.Entry<K, V>> sourceStream
    ) {
        requireNonNull(sourceStream);

        return new MapEntrySequence<>(new StreamIterable<>(sourceStream));

    }

    static <K, V> MapEntrySequence<K, V> ofEntries(
        final Iterable<Map.Entry<K, V>> sourceIterable
    ) {
        requireNonNull(sourceIterable);

        return new MapEntrySequence<>(sourceIterable);
    }

    @SafeVarargs
    public static <K, V> MapEntrySequence<K, V> ofEntries(final Map.Entry<K, V>... values) {
        return new MapEntrySequence<>(new ArrayIterable<>(values));
    }

    static <T> FlatMapSequence<T> ofIterables(
        final Stream<Iterable<T>> sourceStream
    ) {
        requireNonNull(sourceStream);

        return new FlatMapSequence<>(new StreamIterable<>(sourceStream));

    }

    static <T> FlatMapSequence<T> ofIterables(
        final Iterable<Iterable<T>> sourceIterable
    ) {
        requireNonNull(sourceIterable);

        return new FlatMapSequence<>(sourceIterable);
    }

    @SafeVarargs
    static <T> FlatMapSequence<T> ofIterables(final Iterable<T>... values) {
        return new FlatMapSequence<>(new ArrayIterable<>(values));
    }

    static <T> Sequence<T> ofNullable(final T value) {
        if (value == null) {
            return of();
        }

        return of(value);
    }

    static <T> Sequence<T> generate(
        final Supplier<? extends T> next,
        final Supplier<Boolean> hasNext
    ) {
        requireNonNull(next);
        requireNonNull(hasNext);

        return new IterableSequence<>(new GeneratedIterable<>(next, hasNext));
    }

    static <T> Sequence<T> generate(
        final Supplier<? extends T> next
    ) {
        requireNonNull(next);

        return generate(next, () -> true);
    }

    static <T> Sequence<T> empty() {
        return of();
    }

    default Sequence<T> append(final Iterable<T> extraIterable) {
        requireNonNull(extraIterable);

        return new IterableSequence<>(new FlatMappingIterable<>(
            of(this, extraIterable),
            identity()
        ));
    }

    default Sequence<T> prepend(final Iterable<T> extraIterable) {
        requireNonNull(extraIterable);

        return new IterableSequence<>(new FlatMappingIterable<>(
            of(extraIterable, this),
            identity()
        ));
    }

    class IterableSequence<T> extends WrappingIterable<T> implements Sequence<T> {

        public IterableSequence(final Iterable<T> sourceIterable) {
            super(sourceIterable);
        }
    }
}
