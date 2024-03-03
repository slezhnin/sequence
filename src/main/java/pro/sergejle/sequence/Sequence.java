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
        return new IterableSequence<>(new StreamIterable<>(requireNonNull(sourceStream)));

    }

    static <T> Sequence<T> of(
        final Iterable<T> sourceIterable
    ) {
        return new IterableSequence<>(requireNonNull(sourceIterable));
    }

    @SafeVarargs
    static <T> Sequence<T> of(final T... values) {
        return new IterableSequence<>(new ArrayIterable<>(values));
    }

    static <T> Sequence<T> of(final T value) {
        return new IterableSequence<>(List.of(requireNonNull(value)));
    }

    static <K, V> MapEntrySequence<K, V> ofEntries(
        final Stream<Map.Entry<K, V>> sourceStream
    ) {
        return new MapEntrySequence<>(new StreamIterable<>(requireNonNull(sourceStream)));

    }

    static <K, V> MapEntrySequence<K, V> ofEntries(
        final Iterable<Map.Entry<K, V>> sourceIterable
    ) {
        return new MapEntrySequence<>(requireNonNull(sourceIterable));
    }

    @SafeVarargs
    public static <K, V> MapEntrySequence<K, V> ofEntries(final Map.Entry<K, V>... values) {
        return new MapEntrySequence<>(new ArrayIterable<>(values));
    }

    static <T> FlatMapSequence<T> ofIterables(
        final Stream<Iterable<T>> sourceStream
    ) {
        return new FlatMapSequence<>(new StreamIterable<>(requireNonNull(sourceStream)));

    }

    static <T> FlatMapSequence<T> ofIterables(
        final Iterable<Iterable<T>> sourceIterable
    ) {
        return new FlatMapSequence<>(requireNonNull(sourceIterable));
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
        return new IterableSequence<>(new GeneratedIterable<>(requireNonNull(next), requireNonNull(hasNext)));
    }

    static <T> Sequence<T> generate(
        final Supplier<? extends T> next
    ) {
        return generate(requireNonNull(next), () -> true);
    }

    static <T> Sequence<T> empty() {
        return of();
    }

    default Sequence<T> append(final Iterable<T> extraIterable) {
        return new IterableSequence<>(new FlatMappingIterable<>(
            of(this, requireNonNull(extraIterable)),
            identity()
        ));
    }

    default Sequence<T> prepend(final Iterable<T> extraIterable) {
        return new IterableSequence<>(new FlatMappingIterable<>(
            of(requireNonNull(extraIterable), this),
            identity()
        ));
    }

    class IterableSequence<T> extends WrappingIterable<T> implements Sequence<T> {

        public IterableSequence(final Iterable<T> sourceIterable) {
            super(sourceIterable);
        }
    }
}
