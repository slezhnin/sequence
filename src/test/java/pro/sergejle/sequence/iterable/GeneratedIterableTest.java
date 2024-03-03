package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import pro.sergejle.sequence.RandomUtils;

class GeneratedIterableTest extends BaseIterableTest {

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Should return all values from generated source list")
    void iterator() {
        final var source = nextRandomList(5, RandomUtils::nextInt);
        final var sourceIterator = source.iterator();

        final var actual = iterableToList(
            new GeneratedIterable<>(
                sourceIterator::next,
                sourceIterator::hasNext
            ));

        assertThat(actual).containsExactlyElementsOf(source);
    }
}
