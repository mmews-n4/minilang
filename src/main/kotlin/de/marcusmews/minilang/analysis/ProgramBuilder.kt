package de.marcusmews.minilang.analysis

import de.marcusmews.minilang.ast.*
import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedLinkedHashMap


class ProgramBuilder {
    private val scopes = LinkedHashMap<SourceElement, Scope>()

    /** Creates a ProgramInfo from a given ASTInfo */
    fun build(astInfo: ASTInfo) : ProgramInfo {
        // compute scopes
        scopes.clear()
        traverse(astInfo.program)

        return ProgramInfo(astInfo.program, astInfo.parents, scopes)
    }

    private fun traverse(program: Program) {
        val declarations = ArrayListValuedLinkedHashMap<String, Int>()
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
            declarations.put(varDecl.identifier.name, idx)
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
            val declarations = ArrayListValuedLinkedHashMap<String, Int>()
            declarations.put(mapExpr.parameter.name, 0)
            if (mapExpr.body != null) {
                scopes[mapExpr.body] = Scope(mapExpr, declarations)
            }
        }
        traverse(mapExpr.sequence)
        traverse(mapExpr.body)
    }

    private fun traverse(redExpr: ReduceExpression) {
        if (redExpr.param1 != null || redExpr.param2 != null) {
            val declarations = ArrayListValuedLinkedHashMap<String, Int>()
            redExpr.param1?.name?.let { declarations.put(it, 0) }
            redExpr.param2?.name?.let { declarations.put(it, 1) }
            if (redExpr.body != null) {
                scopes[redExpr.body] = Scope(redExpr, declarations)
            }
        }
        traverse(redExpr.sequence)
        traverse(redExpr.initial)
        traverse(redExpr.body)
    }
}

fun getDeclarationIdentifier(scope: Scope, declPos: Int): Identifier? {
    return when (scope.relatedElem) {
        is Program          ->
            if (scope.relatedElem.statements.getOrNull(declPos) is VariableDeclaration) {
                (scope.relatedElem.statements[declPos] as VariableDeclaration).identifier
            }
            else null
        is MapExpression    -> scope.relatedElem.parameter
        is ReduceExpression ->
            when (declPos) {
                0 -> scope.relatedElem.param1
                1 -> scope.relatedElem.param2
                else -> null
            }
        else -> null
    }
}
