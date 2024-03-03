package pro.sergejle.sequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import static pro.sergejle.sequence.TerminatingSequenceTest.TestTerminatingSequence.emptySequence;
import static pro.sergejle.sequence.TerminatingSequenceTest.TestTerminatingSequence.sequenceOf;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pro.sergejle.sequence.iterable.WrappingIterable;

@Tag("unit")
class TerminatingSequenceTest {

    @Test
    @DisplayName("Should return only distinct elements of sequence")
    void distinct() {
        final var actual = sequenceOf(1, 1, 2, 2, 3, 3)
            .distinct()
            .toList();

        assertThat(actual).containsExactlyInAnyOrder(1, 2, 3);
    }

    @Test
    @DisplayName("Should return elements sorted by natural order")
    void sorted() {
        final var actual = sequenceOf(3, 2, 1, 4)
            .sorted()
            .toList();

        assertThat(actual).containsExactlyInAnyOrder(1, 2, 3, 4);

        var seq = Sequence.of(1, 2, 3, 4).peek(System.out::println).filter(i -> i == 1).map(i -> i * 2);

        System.out.println("as list: " + seq.toList());
        System.out.println("count: " + seq.count());
    }

    @Test
    @DisplayName("Should return sequence in reverse order with comparator")
    void sortedWithComparator() {
        final var actual = sequenceOf(4, 1, 2, 3)
            .sorted((a, b) -> -1 * a.compareTo(b))
            .toList();

        assertThat(actual).containsExactlyInAnyOrder(4, 3, 2, 1);
    }

    @Test
    @DisplayName("Should return sequence reduction to sum of elements")
    void reduce() {
        final var actual = sequenceOf(5, 4, 1, 2, 3).reduce(0, Integer::sum);

        assertThat(actual).isEqualTo(15);
    }

    @Test
    @DisplayName("Should return identity on empty sequence")
    void reduceIdentityOnEmpty() {
        final var actual = emptySequence(Integer.class).reduce(15, Integer::sum);

        assertThat(actual).isEqualTo(15);
    }

    @Test
    @DisplayName("Should return empty on empty sequence")
    void reduceToEmptyResult() {
        final var actual = emptySequence(Integer.class).reduce(Integer::sum);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return sequence reduction to sum of elements without identity")
    void reduceWithoutIdentity() {
        final var actual = sequenceOf(5, 4, 1, 2, 3).reduce(Integer::sum);

        assertThat(actual).contains(15);
    }

    @Test
    @DisplayName("Should return sequence reduction to sum of mapped elements")
    void reduceMapping() {
        final var actual = sequenceOf(5, 4, 1, 2, 3).reduce(0.0, (a, b) -> a + Float.valueOf(b));

        assertThat(actual).isEqualTo(15.0);
    }

    @Test
    @DisplayName("Should return identity on empty mapping sequence")
    void reduceMappingIdentityOnEmpty() {
        final var actual = emptySequence(Integer.class).reduce(
            15.0,
            (a, b) -> a + Float.valueOf(b)
        );

        assertThat(actual).isEqualTo(15.0);
    }

    @Test
    @DisplayName("Should return min of sequence elements")
    void min() {
        final var actual = sequenceOf(1, 3, 4, 2).min(Integer::compare);

        assertThat(actual).contains(1);
    }

    @Test
    @DisplayName("Should min return empty on empty sequence")
    void minEmpty() {
        final var actual = emptySequence(Integer.class).min(Integer::compare);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return max of sequence elements")
    void max() {
        final var actual = sequenceOf(1, 3, 4, 2).max(Integer::compare);

        assertThat(actual).contains(4);
    }

    @Test
    @DisplayName("Should max return empty on empty sequence")
    void maxEmpty() {
        final var actual = emptySequence(Integer.class).max(Integer::compare);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Count on empty sequence should return zero")
    void countEmpty() {
        final var actual = emptySequence(Integer.class).count();

        assertThat(actual).isZero();
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Count should return number of sequence elements")
    void count() {
        final var randomList = nextRandomList(Object::new);

        final var actual = sequenceOf(randomList).count();

        assertThat(actual).isEqualTo(randomList.size());
    }

    @Test
    @DisplayName("anyMatch should return false on empty sequence")
    void anyMatchEmpty() {
        final var actual = emptySequence(Integer.class).anyMatch(i -> i == 0);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("anyMatch should return false on absent sequence element")
    void anyMatchNone() {
        final var actual = sequenceOf(1, 2, 3, 4).anyMatch(i -> i == 5);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("anyMatch should return true on contained sequence element")
    void anyMatch() {
        final var actual = sequenceOf(1, 2, 3, 4).anyMatch(i -> i == 3);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("allMatch should return true on empty sequence")
    void allMatchEmpty() {
        final var actual = emptySequence(Integer.class).allMatch(i -> i == 0);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("allMatch should return false on absent sequence element")
    void allMatchFalse() {
        final var actual = sequenceOf(1, 2, 1, 1).allMatch(i -> i == 1);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("anyMatch should return true on matching all sequence element")
    void allMatch() {
        final var actual = sequenceOf(1, 1, 1, 1).allMatch(i -> i == 1);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("noneMatch should return true on empty sequence")
    void noneMatchEmpty() {
        final var actual = emptySequence(Integer.class).noneMatch(i -> i == 0);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("noneMatch should return false on all present sequence elements")
    void noneMatchFalse() {
        final var actual = sequenceOf(1, 1, 1, 1).noneMatch(i -> i == 1);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("noneMatch should return true on matching none sequence element")
    void noneMatch() {
        final var actual = sequenceOf(1, 2, 3, 4).noneMatch(i -> i == 5);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("findFirst should return empty on empty sequence")
    void findFirstEmpty() {
        final var actual = emptySequence(Integer.class).findFirst();

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("findFirst should return the first element of sequence")
    void findFirst() {
        final var actual = sequenceOf(1, 2, 3, 4).findFirst();

        assertThat(actual).contains(1);
    }

    @Test
    @DisplayName("findLast should return empty on empty sequence")
    void findLastEmpty() {
        final var actual = emptySequence(Integer.class).findLast();

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("findLast should return the last element of sequence")
    void findLast() {
        final var actual = sequenceOf(1, 2, 3, 4).findLast();

        assertThat(actual).contains(4);
    }

    @Test
    @DisplayName("toArray should return empty array of objects on empty sequence")
    void toArrayObjectEmpty() {
        final var actual = emptySequence(Integer.class).toArray();

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("toArray should return array of objects on some typed sequence")
    void toArrayObject() {
        final var actual = sequenceOf(1, 2, 3, 4).toArray();

        assertThat(actual).containsExactly(1, 2, 3, 4);
    }

    @Test
    @DisplayName("toArray should return empty array on empty sequence")
    void toArrayEmpty() {
        final var actual = emptySequence(Integer.class).toArray(Integer[]::new);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("toArray should return typed array on typed sequence")
    void toArray() {
        final var actual = sequenceOf(1, 2, 3, 4).toArray(Integer[]::new);

        assertThat(actual).containsExactly(1, 2, 3, 4);
    }

    @Test
    @DisplayName("toMutableList should return empty mutable list on empty sequence")
    void toMutableListEmpty() {
        final var actual = emptySequence(Integer.class).toMutableList();

        assertThat(actual).isEmpty();

        actual.add(1);

        assertThat(actual).containsExactly(1);
    }

    @Test
    @DisplayName("toMutableList should return mutable list of sequence")
    void toMutableList() {
        final var actual = sequenceOf(1, 2, 3, 4).toMutableList();

        assertThat(actual).containsExactly(1, 2, 3, 4);

        actual.add(5);

        assertThat(actual).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    @DisplayName("toList should return empty immutable list on empty sequence")
    void toListEmpty() {
        final var actual = emptySequence(Integer.class).toList();

        assertThat(actual).isEmpty();

        assertThatThrownBy(() -> actual.add(1)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("toList should return immutable list of sequence elements")
    void toList() {
        final var actual = sequenceOf(1, 2, 3, 4).toList();

        assertThat(actual).containsExactly(1, 2, 3, 4);

        assertThatThrownBy(() -> actual.add(5)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void toMutableSet() {
        final var actual = sequenceOf(1, 2, 3, 4).toMutableSet();

        assertThat(actual).containsExactlyInAnyOrder(1, 2, 3, 4);

        actual.add(5);

        assertThat(actual).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
    }

    @Test
    void toSet() {
        final var actual = sequenceOf(1, 2, 3, 4).toSet();

        assertThat(actual).containsExactlyInAnyOrder(1, 2, 3, 4);

        assertThatThrownBy(() -> actual.add(5)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void toMutableMap() {
        final var actual = sequenceOf(
            Map.entry(1, 2),
            Map.entry(3, 4)
        ).toMutableMap(Map.Entry::getKey, Map.Entry::getValue);

        assertThat(actual.keySet()).containsExactlyInAnyOrder(1, 3);
        assertThat(actual.values()).containsExactlyInAnyOrder(2, 4);

        actual.put(5, 6);

        assertThat(actual.keySet()).containsExactlyInAnyOrder(1, 3, 5);
        assertThat(actual.values()).containsExactlyInAnyOrder(2, 4, 6);
    }

    @Test
    void toMap() {
        final var actual = sequenceOf(
            Map.entry(1, 2),
            Map.entry(3, 4)
        ).toMap(Map.Entry::getKey, Map.Entry::getValue);

        assertThat(actual.keySet()).containsExactlyInAnyOrder(1, 3);
        assertThat(actual.values()).containsExactlyInAnyOrder(2, 4);

        assertThatThrownBy(() -> actual.put(5, 6)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void toStream() {
        final var actual = sequenceOf(1, 2, 3, 4).toStream();

        assertThat(actual).containsExactly(1, 2, 3, 4);
    }

    protected static class TestTerminatingSequence<T> extends WrappingIterable<T>
        implements TerminatingSequence<T> {

        static <T> TerminatingSequence<T> emptySequence(final Class<T> source) {
            return new TestTerminatingSequence<>(List.of());
        }

        static <T> TerminatingSequence<T> sequenceOf(final List<T> source) {
            return new TestTerminatingSequence<T>(source);
        }

        @SafeVarargs
        static <T> TerminatingSequence<T> sequenceOf(final T... source) {
            return new TestTerminatingSequence<T>(List.of(source));
        }

        private TestTerminatingSequence(final Iterable<T> sourceIterable) {
            super(sourceIterable);
        }
    }

}
