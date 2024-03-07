package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.sergejle.sequence.RandomUtils;

class SkippingIterableTest extends BaseUnitTestingIterable {

    @Test
    @DisplayName("Should return entire result when skipping negative number")
    void negative() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new SkippingIterable<>(source, -5));

        assertThat(actual).containsExactlyElementsOf(source);
    }

    @Test
    @DisplayName("Should return entire result when skipping zero")
    void zero() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new SkippingIterable<>(source, 0));

        assertThat(actual).containsExactlyElementsOf(source);
    }

    @Test
    @DisplayName("Should return subset when skipping > 0 < size")
    void subList() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new SkippingIterable<>(source, 1));

        assertThat(actual).containsExactlyElementsOf(source.subList(1, source.size()));
    }

    @Test
    @DisplayName("Should return empty result when skipping all elements")
    void exceed() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new SkippingIterable<>(source, source.size() + 5));

        assertThat(actual).isEmpty();
    }
}
