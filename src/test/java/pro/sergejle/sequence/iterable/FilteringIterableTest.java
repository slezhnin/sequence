package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FilteringIterableTest extends BaseIterableTest {

    @Test
    @DisplayName("Should filter according to predicate")
    void iterator() {
        final var source = List.of(2, 5, 3, 7);

        final var actual = iterableToList(new FilteringIterable<>(source, i -> i > 4));

        assertThat(actual).containsExactly(5, 7);
    }
}
