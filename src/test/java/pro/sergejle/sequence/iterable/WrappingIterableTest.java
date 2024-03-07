package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Iterator;
import java.util.Spliterator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class WrappingIterableTest extends BaseUnitTestingIterable {

    @Mock Iterable<Object> iterable;

    @Mock Iterator<Object> iterator;

    @Mock Spliterator<Object> spliterator;

    @Test
    void iterator() {
        when(iterable.iterator()).thenReturn(iterator);

        final var actual = new WrappingIterable<>(iterable).iterator();

        assertThat(actual).isSameAs(iterator);

        verify(iterable).iterator();
        verifyNoMoreInteractions(iterable);
    }

    @Test
    void spliterator() {
        when(iterable.spliterator()).thenReturn(spliterator);

        final var actual = new WrappingIterable<>(iterable).spliterator();

        assertThat(actual).isSameAs(spliterator);

        verify(iterable).spliterator();
        verifyNoMoreInteractions(iterable);
    }
}
