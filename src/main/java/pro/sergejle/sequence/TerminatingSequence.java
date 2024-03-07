package pro.sergejle.sequence;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

interface TerminatingSequence<T> extends Iterable<T> {

    default Sequence<T> distinct() {
        return Sequence.of(toMutableSet());
    }

    default Sequence<T> sorted() {
        return sorted(null);
    }

    default Sequence<T> sorted(final Comparator<? super T> comparator) {
        final var mutableList = toMutableList();

        mutableList.sort(comparator);

        return Sequence.of(mutableList);
    }

    default T reduce(
        final T identity,
        final BinaryOperator<T> accumulator
    ) {
        requireNonNull(accumulator);
        
        var result = identity;
        var foundIdentity = identity != null;

        for (final T element : this) {
            if (foundIdentity) {
                result = accumulator.apply(result, element);
            } else {
                result = element;
                foundIdentity = true;
            }
        }

        return result;
    }

    default Optional<T> reduce(final BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);

        return Optional.ofNullable(reduce(null, accumulator));
    }

    default <U> U reduce(
        final U identity,
        final BiFunction<U, ? super T, U> accumulator
    ) {
        requireNonNull(accumulator);

        var result = identity;

        for (T element : this) {
            result = accumulator.apply(result, element);
        }

        return result;
    }

    default <U> U reduce(final BiFunction<U, ? super T, U> accumulator) {
        requireNonNull(accumulator);

        return reduce(null, accumulator);
    }

    default Optional<T> min(final Comparator<? super T> comparator) {
        requireNonNull(comparator);

        return reduce((candidate, next) -> comparator.compare(next, candidate) < 0
            ? next
            : candidate);
    }

    default Optional<T> max(final Comparator<? super T> comparator) {
        requireNonNull(comparator);

        return reduce((candidate, next) -> comparator.compare(next, candidate) > 0
            ? next
            : candidate);
    }

    default long count() {
        return reduce(0L, (count, e) -> count + 1);
    }

    default boolean anyMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);

        for (final var element : this) {
            if (predicate.test(element)) {
                return true;
            }
        }

        return false;
    }

    default boolean allMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);

        for (final var element : this) {
            if (!predicate.test(element)) {
                return false;
            }
        }

        return true;
    }

    default boolean noneMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);

        for (final var element : this) {
            if (predicate.test(element)) {
                return false;
            }
        }

        return true;
    }

    default Optional<T> findFirst() {
        final var i = iterator();

        return i.hasNext() ? Optional.of(i.next()) : Optional.empty();
    }

    default Optional<T> findLast() {
        return Optional.ofNullable(reduce(null, (id, el) -> el));
    }

    default Object[] toArray() {
        return toMutableList().toArray();
    }

    default T[] toArray(final IntFunction<T[]> generator) {
        return toMutableList().toArray(generator);
    }

    default List<T> toMutableList() {
        final var arrayList = new ArrayList<T>();

        forEach(arrayList::add);

        return arrayList;
    }

    default void forEachIndex(BiConsumer<? super T, Integer> action) {
        requireNonNull(action);

        var index = 0;

        for (T t : this) {
            action.accept(t, index++);
        }
    }

    default List<T> toList() {
        return unmodifiableList(toMutableList());
    }

    default Set<T> toMutableSet() {
        final var hashSet = new HashSet<T>();

        forEach(hashSet::add);

        return hashSet;
    }

    default Set<T> toSet() {
        return unmodifiableSet(toMutableSet());
    }

    default <K, U> Map<K, U> toMutableMap(
        final Function<? super T, ? extends K> keyMapper,
        final Function<? super T, ? extends U> valueMapper
    ) {
        final var hashMap = new HashMap<K, U>();

        forEach(e -> hashMap.put(keyMapper.apply(e), valueMapper.apply(e)));

        return hashMap;
    }

    default <K, U> Map<K, U> toMap(
        final Function<? super T, ? extends K> keyMapper,
        final Function<? super T, ? extends U> valueMapper
    ) {
        return unmodifiableMap(toMutableMap(keyMapper, valueMapper));
    }

    default Stream<T> toStream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
