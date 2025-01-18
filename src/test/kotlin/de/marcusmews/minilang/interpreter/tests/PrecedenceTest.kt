package de.marcusmews.minilang.interpreter.tests

import de.marcusmews.minilang.tests.utils.run
import kotlin.test.Test


class PrecedenceTest {


    @Test
    fun testPrecedence1() {
        run(
            """
                out 1 + 2 * 3
            """
        )
            .assertNoError()
            .assertOutput("7")
            .assertNoVariables()
    }

    @Test
    fun testPrecedence2() {
        run(
            """
                out 1 + 6 / 3
            """
        )
            .assertNoError()
            .assertOutput("3")
            .assertNoVariables()
    }

    @Test
    fun testPrecedence3() {
        run(
            """
                out 1 + 2 * 3 ^ 3
            """
        )
            .assertNoError()
            .assertOutput("55")
            .assertNoVariables()
    }

    @Test
    fun testPrecedence4() {
        run(
            """
                out 1 - 2 * 3 ^ 3
            """
        )
            .assertNoError()
            .assertOutput("-53")
            .assertNoVariables()
    }

    @Test
    fun testPrecedence5() {
        run(
            """
                out (1 + 2) * 3
            """
        )
            .assertNoError()
            .assertOutput("9")
            .assertNoVariables()
    }

    @Test
    fun testPrecedence6() {
        run(
            """
                out (1 - 2) * 3
            """
        )
            .assertNoError()
            .assertOutput("-3")
            .assertNoVariables()
    }
}