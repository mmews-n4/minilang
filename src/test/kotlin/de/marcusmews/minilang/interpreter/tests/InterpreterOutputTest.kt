package de.marcusmews.minilang.interpreter.tests

import de.marcusmews.minilang.tests.utils.run
import kotlin.test.Test

class InterpreterOutputTest {

    @Test
    fun testPrintStatement1() { run ("""
            print "Hello World"
        """)
        .assertNoError()
        .assertOutput("Hello World")
        .assertNoVariables()
    }

    @Test
    fun testOutInt() {
        run("""
            out 2
        """)
            .assertNoError()
            .assertOutput("2")
            .assertNoVariables()
    }

    @Test
    fun testOutReal() {
        run("""
            out 3.14
        """)
            .assertNoError()
            .assertOutput("3.14")
            .assertNoVariables()
    }

    @Test
    fun testOutSequence1() {
        run("""
            out {0, 2}
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2]")
            .assertNoVariables()
    }

    @Test
    fun testOutSequence2() {
        run("""
            out {0, 1 + 1}
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2]")
            .assertNoVariables()
    }

    @Test
    fun testOutSequenceNested1() {
        run("""
            out map({0, 1}, x -> {0, x})
        """)
            .assertNoError()
            .assertOutput("[[0], [0, 1]]")
            .assertNoVariables()
    }

    @Test
    fun testOutMap() {
        run("""
            out map({0,4}, x -> x + 0.1)
        """)
            .assertNoError()
            .assertOutput("[0.1, 1.1, 2.1, 3.1, 4.1]")
            .assertNoVariables()
    }

    @Test
    fun testOutReduce1() {
        run("""
            out reduce({10, 14}, 0.1, x y -> x + y)
        """)
            .assertNoError()
            .assertOutput("60.1")
            .assertNoVariables()
    }

    @Test
    fun testOutReduce2() {
        run("""
            out {reduce({0,4}, 0, x y -> x + y), 15}
        """)
            .assertNoError()
            .assertOutput("[10, 11, 12, 13, 14, 15]")
            .assertNoVariables()
    }

    @Test
    fun testOutReduceNested1() {
        run("""
            out reduce(map({0, 1}, a -> {0, a}), {0, 0}, x y -> x + y)
        """)
            .assertNoError()
            .assertOutput("[0, 0, 0, 1]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression1() {
        run("""
            out 1 + 2
        """)
            .assertNoError()
            .assertOutput("3")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression2() {
        run("""
            out 1.1 + 2
        """)
            .assertNoError()
            .assertOutput("3.1")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression3() {
        run("""
            out {0, 5} + 2
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2, 3, 4, 5, 2]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression4() {
        run("""
            out 2 + {0, 5}
        """)
            .assertNoError()
            .assertOutput("[2, 0, 1, 2, 3, 4, 5]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression5() {
        run("""
            out {0, 5} + 2.2
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2, 3, 4, 5, 2.2]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression6() {
        run("""
            out 2.2 + {0, 5}
        """)
            .assertNoError()
            .assertOutput("[2.2, 0, 1, 2, 3, 4, 5]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression7() {
        run("""
            out {0, 5} + {3, 15}
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2, 3, 4, 5, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression8() {
        run("""
            out {0, 5} - 3
        """)
            .assertNoError()
            .assertOutput("[0, 1, 2, 4, 5]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression9() {
        run("""
            out {0, 5} * 3
        """)
            .assertNoError()
            .assertOutput("[0, 3, 6, 9, 12, 15]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression10() {
        run("""
            out 3 * {0, 5}
        """)
            .assertNoError()
            .assertOutput("[0, 3, 6, 9, 12, 15]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression11() {
        run("""
            out {0, 5} / 3
        """)
            .assertNoError()
            .assertOutput("[0, 0.3333333333333333, 0.6666666666666666, 1, 1.3333333333333333, 1.6666666666666667]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression12() {
        run("""
            out {0, 5} ^ 3
        """)
            .assertNoError()
            .assertOutput("[0, 1, 8, 27, 64, 125]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpression13() {
        run("""
            out {0, 5} - {0, 5}
        """)
            .assertNoError()
            .assertOutput("[]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpressionReduce1() {
        run("""
            out map({0, 5} + {3, 10}, x -> x + 2)
        """)
            .assertNoError()
            .assertOutput("[2, 3, 4, 5, 6, 7, 5, 6, 7, 8, 9, 10, 11, 12]")
            .assertNoVariables()
    }

    @Test
    fun testOutBinaryExpressionReduce2() {
        run("""
            out map({0, 5} + 47.3, x -> x + 2)
        """)
            .assertNoError()
            .assertOutput("[2, 3, 4, 5, 6, 7, 49.3]")
            .assertNoVariables()
    }

}
