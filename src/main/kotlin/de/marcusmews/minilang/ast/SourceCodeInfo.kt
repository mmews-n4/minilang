package de.marcusmews.minilang.ast

import org.antlr.v4.runtime.ParserRuleContext
import org.apache.commons.collections4.MultiValuedMap
import java.util.ArrayList


data class ProgramInfo(val program: Program, val parents: Map<SourceElement, SourceElement>, val sourceLocations: Map<SourceElement, Range>, val scopes: Map<SourceElement, Scope>)

data class ASTInfo(val program: Program, val parents: Map<SourceElement, SourceElement>, val sourceLocations: Map<SourceElement, Range>)

data class Range(val start: Position, val end: Position?)

/**
 * @param line position in a document (zero-based)
 * @param character character offset on a line in a document (zero-based)
 */
data class Position(val line: Int, val character: Int)
enum class IssueKind {
    Error,
    @Suppress("unused")
    Warning,
    @Suppress("unused")
    Info
}

/** Returns the range of a given parser context of ANTLR */
fun getRange(ctx: ParserRuleContext) : Range {
    val start = Position(ctx.start.line - 1, ctx.start.charPositionInLine)
    val end = Position(ctx.stop.line - 1, ctx.stop.charPositionInLine)
    return Range(start, end)
}

data class Scope(val relatedElem: SourceElement, val declarations: MultiValuedMap<String, Int>)

/** Returns the position index of the given Statement. 0-based. */
fun getPosition(programInfo: ProgramInfo, stmt: Statement) : Int {
    if (programInfo.parents.containsKey(stmt)) {
        val program = programInfo.parents[stmt]
        if (program is Program) {
            for ((idx, pStmt) in program.statements.withIndex()) {
                if (pStmt == stmt) {
                    return idx
                }
            }
        }
    }
    return 0
}

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