package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pro.sergejle.sequence.RandomUtils;

class FlatMappingIterableTest extends BaseIterableTest {

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Should return all iterables as one united iterable")
    void iterator() {
        final var source = nextRandomList(
            5,
            () -> nextRandomList(3, RandomUtils::nextInt)
        );
        final var index = new ArrayList<Integer>(5);
        final var expected = new ArrayList<Integer>(15);

        for (int i = 0; i < source.size(); i++) {
            index.add(i);
        }
        source.forEach(expected::addAll);

        final var actual = iterableToList(new FlatMappingIterable<>(index, source::get));

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Should skip empty iterable when flat mapping iterable mapping results")
    void iteratorOfEmptyIterable() {
        final var source = List.of(
            List.of(1),
            List.<Integer>of(),
            List.of(2)
        );
        final var index = List.of(0, 1, 2);
        final var expected = List.of(1, 2);

        final var actual = iterableToList(new FlatMappingIterable<>(index, source::get));

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
