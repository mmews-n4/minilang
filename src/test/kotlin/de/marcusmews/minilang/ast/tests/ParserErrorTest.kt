package de.marcusmews.minilang.ast.tests

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.tests.utils.assertErrors
import kotlin.test.Test

class ParserErrorTest {

    @Test
    fun testPrintStatement1() {
        val source = """
            print 2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 6): mismatched input '2' expecting STRING")
    }

    @Test
    fun testPrintStatement2() {
        val source = """
            var x = 1
            p rint "Edit me"
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (1, 0): extraneous input 'p' expecting {<EOF>, 'var', 'out', 'print'}")
    }

    @Test
    fun testOutStatement() {
        val source = """
            out "string literal"
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 4): Parser expected expression but found: \"string literal\"")
    }

    @Test
    fun testBinaryOperation() {
        val source = """
            out 1 # 2
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 6): token recognition error at: '#'", "Error (0, 8): extraneous input '2' expecting {<EOF>, 'var', 'out', 'print'}")
    }

    @Test
    fun testOtherStatement() {
        val source = """
            exit -1
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 0): mismatched input 'exit' expecting {'var', 'out', 'print'}")
    }

}