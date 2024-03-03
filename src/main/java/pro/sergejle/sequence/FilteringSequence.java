package pro.sergejle.sequence;

import java.util.function.Consumer;
import java.util.function.Predicate;
import pro.sergejle.sequence.iterable.FilteringIterable;
import pro.sergejle.sequence.iterable.LimitingIterable;
import pro.sergejle.sequence.iterable.PeakingIterable;
import pro.sergejle.sequence.iterable.SkippingIterable;

interface FilteringSequence<T> extends Iterable<T> {

    default Sequence<T> filter(final Predicate<? super T> predicate) {
        return Sequence.of(new FilteringIterable<T>(this, predicate));
    }

    default Sequence<T> peek(final Consumer<? super T> action) {
        return Sequence.of(new PeakingIterable<>(this, action));
    }

    default Sequence<T> limit(final long maxSize) {
        return Sequence.of(new LimitingIterable<>(this, maxSize));
    }

    default Sequence<T> skip(final long n) {
        return Sequence.of(new SkippingIterable<>(this, n));
    }
}
