package de.marcusmews.minilang.validation.tests

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.tests.utils.assertErrors
import de.marcusmews.minilang.tests.utils.assertNoError
import kotlin.test.*

class DeclarationTest {

    /** Example given in assignment */
    @Test
    fun testExample() {
        val source = """
            var n = 500
            var sequence = map({0, n}, i -> (-1)^i / (2.0 * i + 1))
            var pi = 4 * reduce(sequence, 0, x y -> x + y)
            print "pi = "
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testDeclarationExistsVarDecl() {
        val source = """
            var pi = 4
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testDeclarationMissingVarDecl() {
        val source = """
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 4): Identifier pi undeclared")
    }

    @Test
    fun testDeclarationAfterUseVarDecl() {
        val source = """
            out pi
            var pi = 4
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 4): Variable pi used before declaration")
    }

    @Test
    fun testDeclarationExistsReduce() {
        val source = """
            var pi = reduce({0, 9}, 0, x y -> x + y)
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testDeclarationMissingReduce() {
        val source = """
            var pi = reduce({0, 9}, 0, x y -> xF + y)
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 34): Identifier xF undeclared")
    }

    @Test
    fun testDeclarationExistsMap() {
        val source = """
            var sequence = map({0, 9}, i -> i)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testDeclarationMissingMap() {
        val source = """
            var sequence = map({0, 9}, i -> iF)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 32): Identifier iF undeclared")
    }

    @Test
    fun testDeclarationExistsNested() {
        val source = """
            var n = 500
            var pi = 4 * reduce(map({0, n}, i -> (-1)^i / (2.0 * i + 1)), 0, x y -> x + y)
            print "pi = "
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertNoError(core)
    }

    @Test
    fun testDeclarationMissingNested() {
        val source = """
            var n = 500
            var pi = 4 * reduce(map({0, n}, i -> (-1)^iF / (2.0 * i + 1)), 0, x y -> x + yF)
            print "pi = "
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (1, 42): Identifier iF undeclared", "Error (1, 77): Identifier yF undeclared")
    }

    @Test
    fun testCollisionVarDecl() {
        val source = """
            var pi = 3
            var pi = 4
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (1, 4): Identifier pi already declared")
    }

    @Test
    fun testCollisionVarDeclAndReduce() {
        val source = """
            out reduce({0, 9}, 0, pi pi -> pi + pi)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 25): Identifier pi already declared")
    }

    @Test
    fun testHidingMap() {
        val source = """
            var pi = map({0, 9}, pi -> pi)
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 21): Identifier pi hides a variable already declared in a parent scope")
    }

    @Test
    fun testHidingReduce() {
        val source = """
            var pi = reduce({0, 9}, 0, x pi -> x + pi)
            out pi
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (0, 29): Identifier pi hides a variable already declared in a parent scope")
    }

    @Test
    fun testAccessTopLevel() {
        val source = """
            var pi = 3.14
            out reduce({0, 9}, 0, x y -> x + y + pi)
        """.trimIndent()
        val core = Core(source).parse().validate()

        assertErrors(core, "Error (1, 37): Identifier pi declared on top level may not be accessed from lambda. (See note in validation)")
    }
}