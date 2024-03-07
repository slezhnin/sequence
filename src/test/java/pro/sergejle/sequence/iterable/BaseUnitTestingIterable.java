package pro.sergejle.sequence.iterable;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static org.mockito.quality.Strictness.LENIENT;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class BaseUnitTestingIterable {

    public <T> List<T> iterableToList(final Iterable<T> source) {
        final var actual = new ArrayList<T>();

        requireNonNull(source).forEach(actual::add);

        return unmodifiableList(actual);
    }
}
