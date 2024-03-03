package pro.sergejle.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RandomUtils {

    private static final Random random = new Random();

    public static void check(
        final boolean valid,
        final String message
    ) {
        if (!valid) {
            throw new IllegalStateException(message);
        }
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(final int bound) {
        return random.nextInt(bound);
    }

    public static <T> List<T> nextRandomList(
        final int minSize,
        final int maxSize,
        final Supplier<T> itemSupplier
    ) {
        check(minSize >= 0, "minSize must be equals or greater then zero");
        check(minSize <= maxSize, "minSize must be equals or lesser then maxSize");

        final var randomSize = nextInt(maxSize - minSize) + minSize;
        final var randomList = new ArrayList<T>(randomSize);

        for (int i = 0; i < randomSize; i++) {
            randomList.add(itemSupplier.get());
        }

        return randomList;
    }

    public static <T> List<T> nextRandomList(
        final int maxSize,
        final Supplier<T> itemSupplier
    ) {
        return nextRandomList(0, maxSize, itemSupplier);
    }

    public static <T> List<T> nextRandomList(
        final Supplier<T> itemSupplier
    ) {
        return nextRandomList(10, itemSupplier);
    }
}
