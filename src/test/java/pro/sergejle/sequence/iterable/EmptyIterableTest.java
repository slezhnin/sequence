package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyIterableTest extends BaseIterableTest {

    @Test
    @DisplayName("Empty Iterator next() should return false")
    void iterator() {
        final var actual = EmptyIterable
            .emptyIterable()
            .iterator();

        assertThat(actual.hasNext()).isFalse();
        assertThat(actual.next()).isNull();
    }

    @Test
    @DisplayName("Empty Spliterator tryAdvance() should return false")
    void spliterator() {
        final var actual = EmptyIterable
            .emptyIterable()
            .spliterator();

        assertThat(actual.estimateSize()).isZero();
        assertThat(actual.tryAdvance(o -> { })).isFalse();
    }
}
