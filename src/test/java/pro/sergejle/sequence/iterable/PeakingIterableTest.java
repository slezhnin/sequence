package pro.sergejle.sequence.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static pro.sergejle.sequence.RandomUtils.nextRandomList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pro.sergejle.sequence.RandomUtils;

class PeakingIterableTest extends BaseUnitTestingIterable {

    @Mock
    Consumer<Integer> consumer;

    @Test
    @DisplayName("Should not call consumer on empty source")
    void empty() {
        final var source = List.<Integer>of();

        final var actual = iterableToList(new PeakingIterable<>(source, consumer));

        assertThat(actual).isEmpty();

        verifyNoInteractions(consumer);
    }

    @Test
    @DisplayName("Should call consumer on every element of source")
    void notEmpty() {
        final var source = nextRandomList(5, RandomUtils::nextInt);

        final var actual = iterableToList(new PeakingIterable<>(source, consumer));

        assertThat(actual).containsExactlyElementsOf(source);

        source.forEach(i -> verify(consumer).accept(i));
        verifyNoMoreInteractions(consumer);
    }
}
