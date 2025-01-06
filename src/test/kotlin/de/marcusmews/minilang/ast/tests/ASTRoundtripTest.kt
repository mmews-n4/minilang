package de.marcusmews.minilang.ast.tests

import de.marcusmews.minilang.ast.ASTPrinter
import de.marcusmews.minilang.Core
import de.marcusmews.minilang.tests.utils.ASTGenerator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

/**
 * The round-trip test generates a specific number of arbitrary Minilang ASTs.
 * These ASTs are then serialized into Minilang source code. That source code
 * is then parsed again by the Minilang parser and then transformed into an
 * AST again.
 */
class ASTRoundtripTest {

    /** Checks that programs can be serialized back again to their original source code */
    @Test
    fun testASTGeneratorFormatterParser() {
        val seed = 42L
        val count = 1000

        // Generate ASTs
        val generatedASTs = ASTGenerator().generateASTs(seed, count)
        val codeGenerator = ASTPrinter()

        generatedASTs.forEachIndexed { index, astGen ->
            try {
                // Step 1: Generate Minilang source code from AST
                val generatedSource = codeGenerator.generate(astGen)
                println("Source code of generated AST $index:\n$generatedSource\n")

                // Step 2: Parse the generated source code back into an AST
                val core = Core(generatedSource)
                    .parse()
                    .validate()

                // Step 3: Check if parsing completed without exceptions
                assertNotNull(core.getAst(), "Parser failed to generate a parse tree for AST $index")

                val parsedSource = core.printAst()
                // Print the parsed source
                println("Source code of parsed AST $index:\n${parsedSource}\n\n")

                assertEquals(generatedSource, parsedSource)
            } catch (e: Exception) {
                fail("Test failed for AST $index. Exception: ${e.message}", e)
            }
        }
    }
}