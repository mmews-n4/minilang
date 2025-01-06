package de.marcusmews.minilang.ast.tests

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.ast.*
import de.marcusmews.minilang.tests.utils.ASTGenerator
import de.marcusmews.minilang.tests.utils.MinilangSourceModifier
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.fail

class ASTParentsTest {

    /** Checks that all AST source elements (but the program root) have a parent source element */
    @Test
    fun testASTSourceElementsHaveParents() {
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
                val core = Core(modifiedSource).parse()
                val parents = core.getProgramInfo()!!.parents

                traverse(core.getProgramInfo()!!, listOf(object : ASTVisitor(){
                    override fun onSourceElement(node: SourceElement, parent: SourceElement?, parentProperty: String?) {
                        when (node) {
                            is Program  -> { /* has no parent */ }
                            else        -> assertNotNull(parents[node], "Missing parent of ${node::class}")
                        }
                    }
                }))

            } catch (e: Exception) {
                fail("Test failed for AST $index. Exception: ${e.message}", e)
            }
        }
    }
}