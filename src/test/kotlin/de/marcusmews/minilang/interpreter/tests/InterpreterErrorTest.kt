package de.marcusmews.minilang.interpreter.tests

import de.marcusmews.minilang.tests.utils.run
import kotlin.test.Test

class InterpreterErrorTest {

    @Test
    fun testSequenceRuntimeError1() {
        run("""
            out map({0, 1} + 0.1, x -> {x, 10})
        """.trimIndent())
            .assertError("Runtime Error (0, 28): Start of sequence must be Int but was 0.1")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testSequenceRuntimeError2() {
        run("""
            out map({0, 1} + 0.1, x -> {0, x})
        """.trimIndent())
            .assertError("Runtime Error (0, 31): End of sequence must be Int but was 0.1")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testSequenceRuntimeError3() {
        run("""
            out map({5, 3}, x -> x)
        """.trimIndent())
            .assertError("Runtime Error (0, 8): Start of sequence must be less/equal than end but was 5 > 3")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testSequenceRuntimeError4() {
        run("""
            out map({0, 3}, x -> {x, 1})
        """.trimIndent())
            .assertError("Runtime Error (0, 21): Start of sequence must be less/equal than end but was 2 > 1")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testSequenceRuntimeError5() {
        run("""
            out map(map({0, 3}, x -> {x, x + 1}), z -> {z, 5})
        """.trimIndent())
            .assertError("Runtime Error (0, 44): Invalid start of sequence. Expected Number but value was [...")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testSequenceRuntimeError6() {
        run("""
            out map(map({0, 3}, x -> {x, x + 1}), z -> {1, z})
        """.trimIndent())
            .assertError("Runtime Error (0, 47): Invalid end of sequence. Expected Number but value was [...")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testDivisionByZero1() {
        run("""
            out 1 / 0
        """.trimIndent())
            .assertError("Runtime Error (0, 8): Division by zero")
            .assertNoOutput()
            .assertNoVariables()
    }

    @Test
    fun testDivisionByZero2() {
        run("""
            out map({-2, 2}, x -> 5 / x)
        """.trimIndent())
            .assertError("Runtime Error (0, 26): Division by zero")
            .assertNoOutput()
            .assertNoVariables()
    }


}