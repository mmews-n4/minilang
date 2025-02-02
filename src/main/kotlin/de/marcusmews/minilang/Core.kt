package de.marcusmews.minilang

import de.marcusmews.minilang.analysis.ProgramBuilder
import de.marcusmews.minilang.antlr.createParser
import de.marcusmews.minilang.ast.*
import de.marcusmews.minilang.validation.*

class Core(private var source: String) {
    private val issueHandler = StdOutIssueHandler()
    private var programInfo: ProgramInfo? = null

    fun parse() : Core {
        if (programInfo == null) {
            try {
                val astInfo = ASTBuilder().build(createParser(source, issueHandler).program())
                programInfo = ProgramBuilder().build(astInfo)
            } catch (e: ASTBuilderException) {
                val range = getRange(e.ctx)
                issueHandler.replaceIssue(IssueKind.Error, range, e.message ?: "")
            }
        }
        return this
    }

    fun validate() : Core {
        if (programInfo == null) {
            // errors during parsing or AST construction
        } else if (issueHandler.issues.isEmpty()) {
            val validators = listOf(
                StructureValidator(issueHandler, programInfo!!),
                DeclarationValidator(issueHandler, programInfo!!),
                TypeValidator(issueHandler, programInfo!!)
                // add more validators here
            )
            traverse(programInfo!!, validators)
        } else {
            // the ANTLR parser already found issues hence do not perform higher level validations
        }
        return this
    }

    fun printAst() : String? {
        return if (programInfo == null) {
                null
            } else {
                ASTPrinter().generate(programInfo!!.program)
            }
    }

    fun getAst() : Program? {
        return programInfo?.program
    }

    fun getProgramInfo(): ProgramInfo? {
        return programInfo
    }

    fun getIssues(): List<Issue> {
        return issueHandler.issues
    }

}