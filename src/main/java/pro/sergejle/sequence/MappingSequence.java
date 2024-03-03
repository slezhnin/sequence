package pro.sergejle.sequence;

import static java.util.function.Function.identity;
import java.util.Map;
import java.util.function.Function;
import pro.sergejle.sequence.iterable.FlatMappingIterable;
import pro.sergejle.sequence.iterable.MappingIterable;
import pro.sergejle.sequence.iterable.WrappingIterable;

interface MappingSequence<T> extends Iterable<T> {

    default <R> Sequence<R> map(final Function<? super T, ? extends R> mapper) {
        return Sequence.of(new MappingIterable<T, R>(this, mapper));
    }

    default <R> Sequence<R> flatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return Sequence.of(new FlatMappingIterable<>(this, mapper));
    }

    default <K, V> MapEntrySequence<K, V> mapEntry(
        final Function<? super T, ? extends Map.Entry<K, V>> mapper
    ) {
        return new MapEntrySequence<K, V>(new MappingIterable<>(this, mapper));
    }

    default <K, V> MapEntrySequence<K, V> mapEntry(
        final Function<? super T, ? extends K> keyMapper,
        final Function<? super T, ? extends V> valueMapper
    ) {
        return mapEntry(t -> Map.entry(keyMapper.apply(t), valueMapper.apply(t)));
    }

    default <R> FlatMapSequence<R> mapIterable(
        final Function<? super T, ? extends Iterable<R>> mapper
    ) {
        return new FlatMapSequence<>(new MappingIterable<>(this, mapper));
    }

    default <K, V> MapEntrySequence<K, V> flatMapEntry(
        final Function<? super T, ? extends Sequence<? extends Map.Entry<K, V>>> mapper
    ) {
        return new MapEntrySequence<K, V>(new FlatMappingIterable<>(this, mapper));
    }

    class FlatMapSequence<T> extends WrappingIterable<Iterable<T>>
        implements Sequence<Iterable<T>> {

        public FlatMapSequence(final Iterable<Iterable<T>> sourceIterable) {
            super(sourceIterable);
        }

        public <R> Sequence<R> mapAll(final Function<? super T, ? extends R> mapper) {
            return Sequence.of(new MappingIterable<T, R>(flatMap(), mapper));
        }

        public Sequence<T> flatMap() {
            return flatMap(identity());
        }
    }

    class MapEntrySequence<K, V> extends WrappingIterable<Map.Entry<K, V>>
        implements Sequence<Map.Entry<K, V>> {

        public MapEntrySequence(final Iterable<Map.Entry<K, V>> sourceIterable) {
            super(sourceIterable);
        }

        public <R, U> MapEntrySequence<R, U> map(
            final Function<? super K, ? extends R> keyMapper,
            final Function<? super V, ? extends U> valueMapper
        ) {
            return new MapEntrySequence<R, U>(map(e -> Map.<R, U>entry(
                keyMapper.apply(e.getKey()),
                valueMapper.apply(e.getValue())
            )));
        }

        public <R> MapEntrySequence<R, V> mapKey(
            final Function<? super K, ? extends R> keyMapper
        ) {
            return map(keyMapper, identity());
        }

        public <U> MapEntrySequence<K, U> mapValue(
            final Function<? super V, ? extends U> valueMapper
        ) {
            return map(identity(), valueMapper);
        }

        public Map<K, V> toMap() {
            return toMap(Map.Entry::getKey, Map.Entry::getValue);
        }
    }
}
