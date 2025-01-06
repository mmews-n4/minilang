package de.marcusmews.minilang.analysis

import de.marcusmews.minilang.ast.*
import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap


class ProgramBuilder {
    private val scopes = HashMap<SourceElement, Scope>()

    /** Creates a ProgramInfo from a given ASTInfo */
    fun build(astInfo: ASTInfo) : ProgramInfo {
        // compute scopes
        scopes.clear()
        traverse(astInfo.program)

        return ProgramInfo(astInfo.program, astInfo.parents, astInfo.sourceLocations, scopes)
    }

    private fun traverse(program: Program) {
        val declarations = ArrayListValuedHashMap<String, Int>()
        for ((idx, stmt) in program.statements.withIndex()) {
            when (stmt) {
                is VariableDeclaration  -> traverse(stmt, idx, declarations)
                is OutputStatement      -> traverse(stmt.expression)
                else                    -> {/* ignore */}
            }
        }
        scopes[program] = Scope(program, declarations)
    }

    private fun traverse(varDecl: VariableDeclaration, idx: Int, declarations: MultiValuedMap<String, Int>) {
        if (varDecl.identifier != null) {
            declarations.put(varDecl.identifier, idx)
        }
        traverse(varDecl.expression)
    }

    private fun traverse(expr: Expression?) {
        when (expr) {
            null                        -> return
            is MapExpression            -> traverse(expr)
            is ReduceExpression         -> traverse(expr)
            is ParenthesizedExpression  -> traverse(expr.expression)
            is BinaryOperation          -> {
                traverse(expr.left)
                traverse(expr.right)
            }
            is SequenceExpression       -> {
                traverse(expr.start)
                traverse(expr.end)
            }
            else                        -> {/* ignore */}
        }
    }

    private fun traverse(mapExpr: MapExpression) {
        if (mapExpr.parameter != null) {
            val declarations = ArrayListValuedHashMap<String, Int>()
            declarations.put(mapExpr.parameter, 0)
            scopes[mapExpr] = Scope(mapExpr, declarations)
        }
        traverse(mapExpr.sequence)
        traverse(mapExpr.body)
    }

    private fun traverse(redExpr: ReduceExpression) {
        if (redExpr.param1 != null || redExpr.param2 != null) {
            val declarations = ArrayListValuedHashMap<String, Int>()
            redExpr.param1?.let { declarations.put(it, 0) }
            redExpr.param2?.let { declarations.put(it, 1) }
            scopes[redExpr] = Scope(redExpr, declarations)
        }
        traverse(redExpr.sequence)
        traverse(redExpr.accumulator)
        traverse(redExpr.body)
    }
}
