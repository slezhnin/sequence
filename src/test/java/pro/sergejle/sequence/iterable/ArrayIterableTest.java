package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import pro.sergejle.sequence.RandomUtils;

class ArrayIterableTest extends BaseUnitTestingIterable {

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Iterator should return all array elements")
    void iterator() {
        final var source = nextRandomList(RandomUtils::nextInt).toArray(new Integer[0]);

        final var actual = iterableToList(new ArrayIterable<>(source));

        assertThat(actual).containsExactly(source);
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Spliterator should return all array elements")
    void spliterator() {
        final var source = nextRandomList(RandomUtils::nextInt).toArray(new Integer[0]);

        final var actual = iterableToList(new ArrayIterable<>(source));

        assertThat(new ArrayIterable<>(source)
            .spliterator()
            .getExactSizeIfKnown()).isEqualTo(source.length);
        assertThat(actual).containsExactly(source);
    }
}
