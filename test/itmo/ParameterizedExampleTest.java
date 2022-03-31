package itmo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ParameterizedExampleTest {

    private static Stream<Arguments> isEvenTestParameters() {
        return Stream.of(
                Arguments.of(2, true),
                Arguments.of(4, true),
                Arguments.of(1, false),
                Arguments.of(Integer.MAX_VALUE, false),
                Arguments.of(Integer.MIN_VALUE, true)
        );
    }

    @ParameterizedTest
    @MethodSource("isEvenTestParameters")
    public void isEvenTest(int num, boolean expected) {
        Assertions.assertEquals(expected, isEven(num));
    }

    private boolean isEven(int num) {
        return num % 2 == 0;
    }

}
