package de.marcusmews.minilang.ast.tests

import de.marcusmews.minilang.ast.ASTPrinter
import de.marcusmews.minilang.Core
import de.marcusmews.minilang.tests.utils.ASTGenerator
import de.marcusmews.minilang.tests.utils.MinilangSourceModifier
import kotlin.test.Test
import kotlin.test.fail

class ASTBrokenTest {

    @Test
    fun testASTGeneratorFormatterParser() {
        val seed = 42L
        val count = 1000

        // Generate ASTs
        val generatedASTs = ASTGenerator().generateASTs(seed, count)
        val codeGenerator = ASTPrinter()
        val sourceModifier = MinilangSourceModifier()

        generatedASTs.forEachIndexed { index, astGen ->
            try {
                // Step 1: Generate Minilang source code from AST
                val generatedSource = codeGenerator.generate(astGen)
                val modifiedSource = sourceModifier.modifySourceCode(seed + index, generatedSource)
                println("Source code of generated AST $index:\n$modifiedSource\n")

                // Step 2: Parse the generated source code back into an AST
                val core = Core(modifiedSource)
                core.parse()     // this should not throw exceptions

            } catch (e: Exception) {
                fail("Test failed for AST $index. Exception: ${e.message}", e)
            }
        }
    }
}