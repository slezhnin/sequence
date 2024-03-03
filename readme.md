# Sequence is a stream like representation of iterables

Java Stream is too complex. Sometimes I need more simple solution for the single threaded code.
So I came up with `Iterator` based structure more or less reminding the `Stream` API and compatible with the most Java collection classes.

## Meet the Sequence library.

```java
class TestSeq {

    public static void main() {
        var seq = Sequence
            .of(1, 2, 3, 4)
            .peek(System.out::println)
            .filter(i -> i == 1)
            .map(i -> i * 2);

        System.out.println("as list: " + seq.toList());
        System.out.println("count: " + seq.count());
    }
}
```
