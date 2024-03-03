package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.sergejle.sequence.RandomUtils;

class LimitingIterableTest extends BaseIterableTest {

    @Test
    @DisplayName("Should return empty result when limit to negative number")
    void negative() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new LimitingIterable<>(source, -5));

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return empty result when limit to zero")
    void zero() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new LimitingIterable<>(source, 0));

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return subset when limit > 0 < size")
    void subList() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new LimitingIterable<>(source, 1));

        assertThat(actual).containsExactlyElementsOf(source.subList(0, 1));
    }

    @Test
    @DisplayName("Should return entire source when limit >= size")
    void all() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new LimitingIterable<>(source, source.size() + 5));

        assertThat(actual).containsExactlyElementsOf(source);
    }

    @Test
    @DisplayName("Should throw exception when exceeding limit in the next()")
    void exceed() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        Assertions
            .assertThatThrownBy(() -> {
                new LimitingIterable<>(source, 0)
                    .iterator()
                    .next();
            })
            .isInstanceOf(IllegalStateException.class);
    }
}
