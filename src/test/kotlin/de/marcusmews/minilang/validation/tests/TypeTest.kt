package de.marcusmews.minilang.validation.tests

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.tests.utils.assertErrors
import de.marcusmews.minilang.tests.utils.assertNoError
import kotlin.test.Test

class TypeTest {

    @Test
    fun testSequenceOk1() {
        val source = """
            out {0, 1}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testSequenceOk2() {
        val source = """
            out {0, 1 + 1}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testSequenceError1() {
        val source = """
            out {0.1, 1}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 5): Types incompatible: Expected type is Int while given type was Real.")
    }

    @Test
    fun testSequenceError2() {
        val source = """
            out {0, 1.1}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 8): Types incompatible: Expected type is Int while given type was Real.")
    }

    @Test
    fun testSequenceError3() {
        val source = """
            out {0, 1 - 0.1}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 8): Types incompatible: Expected type is Int while given type was Real.")
    }

    @Test
    fun testMapOk() {
        val source = """
            out map({0,4}, x -> x + 0.1)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testReduceOk() {
        val source = """
            out reduce({0,4}, 0.1, x y -> x + y)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testSequenceReduceOk() {
        val source = """
            out {reduce({0,4}, 0, x y -> x + y), 100}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testSequenceReduceError() {
        val source = """
            out {reduce({0,4}, 0.1, x y -> x + y), 100}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 5): Types incompatible: Expected type is Int while given type was Real.")
    }

    @Test
    fun testBinaryExpressionOk1() {
        val source = """
            out 1 + 2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk2() {
        val source = """
            out 1.1 + 2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk3() {
        val source = """
            out {0, 5} + 2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk4() {
        val source = """
            out 2 + {0, 5}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk5() {
        val source = """
            out {0, 5} + 2.2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk6() {
        val source = """
            out 2.2 + {0, 5}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk7() {
        val source = """
            out {0, 5} + {3, 15}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk8() {
        val source = """
            out {0, 5} - 3
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk9() {
        val source = """
            out {0, 5} * 3
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk10() {
        val source = """
            out 3 * {0, 5}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk11() {
        val source = """
            out {0, 5} / 3
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk12() {
        val source = """
            out {0, 5} ^ 3
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionOk13() {
        val source = """
            out {0, 5} - {0, 5}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionReduceOk1() {
        val source = """
            out map({0, 5} + {3, 15}, x -> x + 2)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionReduceOk2() {
        val source = """
            out map({0, 5} + 47.3, x -> x + 2)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testBinaryExpressionError1() {
        val source = """
            out 5 - {3, 15}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 4): Types incompatible: Expected type is Real while given type was Sequence.")
    }

    @Test
    fun testBinaryExpressionError2() {
        val source = """
            out 5 / {3, 15}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 8): Types incompatible: Expected type is Real while given type was Sequence.")
    }

    @Test
    fun testBinaryExpressionError3() {
        val source = """
            out 5 ^ {3, 15}
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 8): Types incompatible: Expected type is Real while given type was Sequence.")
    }

}