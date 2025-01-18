package de.marcusmews.minilang.interpreter.tests

import de.marcusmews.minilang.tests.utils.run
import kotlin.test.Test

class InterpreterVariableTest {

    @Test
    fun testVariables1() {
        run("""
            var v1 = 1
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("v1" to "1"))
    }

    @Test
    fun testVariables2() {
        run("""
            var v1 = 1
            var v2 = 2.2
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("v1" to "1", "v2" to "2.2"))
    }

    @Test
    fun testSequences1() {
        run("""
            var s1 = {0, 0}
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("s1" to "[0]"))
    }

    @Test
    fun testSequences2() {
        run("""
            var s1 = {0, 0}
            var s2 = {1, 2}
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("s1" to "[0]", "s2" to "[1, 2]"))
    }

    @Test
    fun testNestedSequence1() {
        run("""
            var nestedSequence = map({0, 1}, x -> {0, x})
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("nestedSequence" to "[[0], [0, 1]]"))
    }

    @Test
    fun testMixedSequence1() {
        run("""
            var mixedSequence = map({0, 1}, x -> {0, x}) + 3.14
        """)
            .assertNoError()
            .assertOutput("")
            .assertVariables(mapOf("mixedSequence" to "[[0], [0, 1], 3.14]"))
    }

}