package de.marcusmews.minilang.analysis.tests

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.ast.*
import de.marcusmews.minilang.tests.utils.ASTGenerator
import kotlin.test.Test
import kotlin.test.assertEquals

class ScopeCreationTest {

    /** Checks that the correct number of scopes is created */
    @Test
    fun testCreateScopes() {
        val seed = 42L
        val count = 1000

        // Generate ASTs
        val generatedASTs = ASTGenerator().generateASTs(seed, count)
        val codeGenerator = ASTPrinter()

        generatedASTs.forEachIndexed { index, astGen ->
            // Step 1: Generate Minilang source code from AST
            val generatedSource = codeGenerator.generate(astGen)
            println("Source code of generated AST $index:\n$generatedSource\n")

            // Step 2: Parse the generated source code back into an AST
            val core = Core(generatedSource).parse()
            val programInfo = core.getProgramInfo()!!
            var expectedScopeCount = 0

            traverse(programInfo, listOf(object : ASTVisitor(){
                override fun onSourceElement(node: SourceElement, parent: SourceElement?, parentProperty: String?) {
                    when (node) {
                        is Program          -> { expectedScopeCount++ }
                        is MapExpression    -> { expectedScopeCount++ }
                        is ReduceExpression -> { expectedScopeCount++ }
                        else                -> {/* no scopes for other elements */}
                    }
                }
            }))

            assertEquals(expectedScopeCount, programInfo.scopes.size)
        }
    }
}