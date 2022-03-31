package itmo.minimizers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static itmo.minimizers.FibonacciMinimizer.fibonacci;

public class FibonacciMinimizerTest {

    private static Stream<Arguments> fibonacciNumberTestParameters() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 3),
                Arguments.of(4, 5),
                Arguments.of(5, 8),
                Arguments.of(6, 13)
        );
    }

    @ParameterizedTest
    @MethodSource("fibonacciNumberTestParameters")
    void fibonacciNumberTest(int index, int expectedValue) {
        Assertions.assertEquals(expectedValue, fibonacci(index));;
    }

}
