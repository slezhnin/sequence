package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class StreamIterableTest extends BaseIterableTest {

    @Mock Stream<Object> stream;

    @Mock Iterator<Object> iterator;

    @Mock Spliterator<Object> spliterator;

    @Test
    void iterator() {
        when(stream.iterator()).thenReturn(iterator);

        final var actual = new StreamIterable<>(stream).iterator();

        assertThat(actual).isSameAs(iterator);

        verify(stream).iterator();
        verifyNoMoreInteractions(stream);
    }

    @Test
    void spliterator() {
        when(stream.spliterator()).thenReturn(spliterator);

        final var actual = new StreamIterable<>(stream).spliterator();

        assertThat(actual).isSameAs(spliterator);

        verify(stream).spliterator();
        verifyNoMoreInteractions(stream);
    }
}
