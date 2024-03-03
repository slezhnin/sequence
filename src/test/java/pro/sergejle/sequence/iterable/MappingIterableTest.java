package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.sergejle.sequence.RandomUtils;

class MappingIterableTest extends BaseIterableTest {

    @Test
    @DisplayName("Should return empty result on empty source")
    void empty() {
        final var source = List.<Integer>of();

        final var actual = iterableToList(new MappingIterable<>(source, Float::valueOf));

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return mapped result on provided source")
    void notEmpty() {
        final var source = nextRandomList(5, RandomUtils::nextInt);
        final var expected = new ArrayList<Float>(5);

        source.forEach(i -> expected.add(Float.valueOf(i)));

        final var actual = iterableToList(new MappingIterable<>(source, Float::valueOf));

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
