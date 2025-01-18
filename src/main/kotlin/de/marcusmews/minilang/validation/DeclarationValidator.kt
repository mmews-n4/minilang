package de.marcusmews.minilang.validation

import de.marcusmews.minilang.analysis.getDeclarationIdentifier
import de.marcusmews.minilang.ast.*

/**
 * Contains validations regarding declarations and its misuses
 */
class DeclarationValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onIdentifierExpression(node: IdentifierExpression, parent: SourceElement?, partType: SourceElementPart?) {
        checkDeclarationMissing(node)
    }

    override fun onProgram(node: Program, parent: SourceElement?, partType: SourceElementPart?) {
        checkDeclarationCollision(node)
        checkDeclarationHiding(node)
    }

    override fun onMapExpression(node: MapExpression, parent: SourceElement?, partType: SourceElementPart?) {
        if (node.body != null) {
            checkDeclarationCollision(node.body)
            checkDeclarationHiding(node.body)
        }
    }

    override fun onReduceExpression(node: ReduceExpression, parent: SourceElement?, partType: SourceElementPart?) {
        if (node.body != null) {
            checkDeclarationCollision(node.body)
            checkDeclarationHiding(node.body)
        }
    }


    private fun checkDeclarationCollision(node: SourceElement) {
        val scopes = getScopes(programInfo, node)
        val scope = scopes.firstOrNull() ?: return

        for (identifierName in scope.declarations.keySet()) {
            if (scope.declarations.get(identifierName).size > 1) {
                val declarations = scope.declarations.get(identifierName)
                for ((idx, declPos) in declarations.withIndex()) {
                    if (idx > 0) {
                        val srcElem = getDeclarationIdentifier(scope, declPos)
                        if (srcElem != null) {
                            reportError(srcElem, "Identifier $identifierName already declared")
                        }
                    }
                }
            }
        }
    }

    private fun checkDeclarationHiding(node: SourceElement) {
        val scopes = getScopes(programInfo, node)
        val scope = scopes.firstOrNull() ?: return

        for (identifierName in scope.declarations.keySet()) {
            for (parentScope in scopes) {
                if (parentScope != scope) {
                    if (parentScope.declarations.containsKey(identifierName)) {
                        val declPos = scope.declarations[identifierName].first()
                        val srcElem = getDeclarationIdentifier(scope, declPos)
                        if (srcElem != null) {
                            reportError(srcElem, "Identifier $identifierName hides a variable already declared in a parent scope")
                        }
                    }
                }
            }
        }
    }

    private fun checkDeclarationMissing(idExpr: IdentifierExpression) {
        val scopes = getScopes(programInfo, idExpr)
        var scopeIdExpr : Scope? = null
        var scopeIdExprIdx : Int = -1
        for ((index, scope) in scopes.withIndex()) {
            if (scope.declarations.containsKey(idExpr.name)) {
                scopeIdExpr = scope
                scopeIdExprIdx = index
                break
            }
        }
        if (scopeIdExpr == null) {
            reportError(idExpr, "Identifier ${idExpr.name} undeclared")
            return
        }
        // scope found means identifier is declared

        checkTopLevelAccess(idExpr, scopes, scopeIdExpr) ?: return

        checkParentLambdaAccess(idExpr, scopeIdExprIdx) ?: return

        if (scopeIdExpr.relatedElem is Program) {
            // check whether the identifier access happens before/after its declaration
            var stmtIdExpr : SourceElement = idExpr
            while (stmtIdExpr !is Statement && programInfo.parents.containsKey(stmtIdExpr)) {
                stmtIdExpr = programInfo.parents[stmtIdExpr]!!
            }
            if (stmtIdExpr is Statement) {
                val declPos = scopeIdExpr.declarations[idExpr.name]!!.first()
                val stmtPos = stmtIdExpr.position
                if (declPos >= stmtPos) {
                    reportError(idExpr, "Variable ${idExpr.name} used before declaration")
                    return
                }
            }
        }
    }

    /**
     * **Important note:**
     *
     * This checks whether the current IdentifierExpression is located inside a lambda
     * but accesses a variable declared on top level. With respect to the
     * assignment this is forbidden. However, this restriction can be removed
     * at any time since its violation would not harm the program execution.
     */
    private fun checkTopLevelAccess(idExpr: IdentifierExpression, scopes: List<Scope>, scopeIdExpr: Scope) : Any? {
        if (scopeIdExpr.relatedElem is Program) {
            // the identifier was declared on program level / top level
            if (scopes.size > 1) {
                reportError(
                    idExpr,
                    "Identifier ${idExpr.name} declared on top level may not be accessed from lambda. (See note in validation)"
                )
                return null
            }
        }
        return this
    }

    /**
     * **Important note**
     *
     * This checks whether a variable declared in a parent lambda is accessed.
     * With respect to the assignment this is forbidden. However, this restriction
     * can be removed at any time since its violation would not harm the program
     * execution.
     */
    private fun checkParentLambdaAccess(idExpr: IdentifierExpression, scopeIdExprIdx: Int) : Any? {
        if (scopeIdExprIdx > 0) {
            reportError(idExpr, "Variable ${idExpr.name} access to parent scope not allowed. (See note in validation)")
            return null
        }
        return this
    }
}