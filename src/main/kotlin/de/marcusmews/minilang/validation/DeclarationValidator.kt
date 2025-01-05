package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.*

class DeclarationValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onIdentifierExpression(node: IdentifierExpression, parent: SourceElement?, parentProperty: String?) {
        checkDeclarationMissing(node)
    }

    override fun onProgram(node: Program, parent: SourceElement?, parentProperty: String?) {
        checkDeclarationCollision(node)
        checkDeclarationHiding(node)
    }

    override fun onMapExpression(node: MapExpression, parent: SourceElement?, parentProperty: String?) {
        checkDeclarationCollision(node)
        checkDeclarationHiding(node)
    }

    override fun onReduceExpression(node: ReduceExpression, parent: SourceElement?, parentProperty: String?) {
        checkDeclarationCollision(node)
        checkDeclarationHiding(node)
    }


    private fun checkDeclarationCollision(node: SourceElement) {
        val scopes = getScopes(programInfo, node)
        val scope = scopes.firstOrNull() ?: return

        for (identifierName in scope.declarations.keySet()) {
            if (scope.declarations.get(identifierName).size > 1) {
                val declarations = scope.declarations.get(identifierName)
                for ((idx, declPos) in declarations.withIndex()) {
                    if (idx > 0) {
                        val srcElem: SourceElement? = getScopeRelatedElement(scope, declPos)
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
                        val srcElem: SourceElement? = getScopeRelatedElement(scope, 0)
                        if (srcElem != null) {
                            reportError(srcElem, "Identifier $identifierName hides a variable already declared in a parent scope")
                        }
                    }
                }
            }
        }
    }

    private fun getScopeRelatedElement(scope: Scope, declPos: Int): SourceElement? {
        var srcElem: SourceElement? = null
        when (scope.relatedElem) {
            is Program          -> srcElem = scope.relatedElem.statements.getOrNull(declPos) // improve: mark identifier instead of hole statement
            is MapExpression    -> srcElem = scope.relatedElem // this might never happen
            is ReduceExpression -> srcElem = scope.relatedElem // improve: mark specific parameter instead of hole expression
        }
        return srcElem
    }

    private fun checkDeclarationMissing(idExpr: IdentifierExpression) {
        val scopes = getScopes(programInfo, idExpr)
        var scopeIdExpr : Scope? = null
        for (scope in scopes) {
            if (scope.declarations.containsKey(idExpr.name)) {
                scopeIdExpr = scope
            }
        }
        if (scopeIdExpr == null) {
            reportError(idExpr, "Identifier ${idExpr.name} undeclared")
            return
        }
        // scope founds means identifier is declared
        if (scopeIdExpr.relatedElem is Program) {
            // check whether the identifier access happens before/after its declaration
            var stmtIdExpr : SourceElement = idExpr
            while (stmtIdExpr !is Statement && programInfo.parents.containsKey(stmtIdExpr)) {
                stmtIdExpr = programInfo.parents[stmtIdExpr]!!
            }
            if (stmtIdExpr is Statement) {
                val declPos = scopeIdExpr.declarations[idExpr.name]!!.first()
                val stmtPos = getPosition(programInfo, stmtIdExpr)
                if (declPos >= stmtPos) {
                    reportError(idExpr, "Identifier ${idExpr.name} used before declaration")
                    return
                }
            }
        }
    }

}