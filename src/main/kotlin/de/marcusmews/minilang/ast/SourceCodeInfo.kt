package de.marcusmews.minilang.ast

import org.antlr.v4.runtime.ParserRuleContext
import org.apache.commons.collections4.MultiValuedMap
import java.util.ArrayList


data class ProgramInfo(val program: Program, val parents: Map<SourceElement, SourceElement>, val scopes: Map<SourceElement, Scope>)

data class ASTInfo(val program: Program, val parents: Map<SourceElement, SourceElement>)

data class Range(val start: Position, val end: Position?)

val RANGE_MISSING = Range(Position(0, 0), Position(0, 0))

/**
 * @param line position in a document (zero-based)
 * @param character character offset on a line in a document (zero-based)
 */
data class Position(val line: Int, val character: Int)
enum class IssueKind {
    RuntimeError,
    Error,
    @Suppress("unused")
    Warning,
    @Suppress("unused")
    Info
}

/** Returns the range of a given parser context of ANTLR */
fun getRange(ctx: ParserRuleContext) : Range {
    val start = Position(ctx.start?.line?.minus(1) ?: 0, ctx.start?.charPositionInLine ?: 0)
    var end = Position(ctx.stop?.line?.minus(1) ?: 0, ctx.stop?.charPositionInLine ?: 0)
    for (child in ctx.children.orEmpty()) {
        when (child) {
            is ParserRuleContext -> {
                if (child.stop.line > end.line || child.stop.charPositionInLine > end.character) {
                    end = Position(child.stop?.line?.minus(1) ?: 0, child.stop?.charPositionInLine ?: 0)
                }
            }
            else -> {
                end = Position(end.line,end.character + child.text.length)
            }
        }
    }
    return Range(start, end)
}

data class Scope(val relatedElem: SourceElement, val declarations: MultiValuedMap<String, Int>)

/** Returns a list of scopes ordered from innermost to outermost scope */
fun getScopes(programInfo: ProgramInfo, elem: SourceElement) : List<Scope> {
    val scopes = ArrayList<Scope>()
    var sourceElem = elem
    if (programInfo.scopes.containsKey(sourceElem)) {
        scopes.add(programInfo.scopes[sourceElem]!!)
    }
    while (programInfo.parents.containsKey(sourceElem)) {
        sourceElem = programInfo.parents[sourceElem]!!
        if (programInfo.scopes.containsKey(sourceElem)) {
            scopes.add(programInfo.scopes[sourceElem]!!)
        }
    }
    return scopes
}

/** Returns a list of scopes ordered from innermost to outermost scope */
fun getDeclarationInitializer(programInfo: ProgramInfo, idExpr: IdentifierExpression) : Expression? {
    val scopes = getScopes(programInfo, idExpr)
    for (scope in scopes) {
        if (scope.declarations.containsKey(idExpr.name)) {
            val position = scope.declarations.get(idExpr.name).first()
            when (scope.relatedElem) {
                is Program -> {
                    val variableDeclaration = scope.relatedElem.statements[position]
                    if (variableDeclaration is VariableDeclaration) {
                        return variableDeclaration.expression
                    }
                }
                is MapExpression -> {
                    val sequence = scope.relatedElem.sequence
                    if (sequence is SequenceExpression) {
                        return sequence.start
                    }
                    // no generics support here
                }
                is ReduceExpression -> {
                    return scope.relatedElem.initial
                }
                else -> {/* other elements do not declare variable initializers */}
            }
        }
    }
    return null
}