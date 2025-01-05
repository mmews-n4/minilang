package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.*


abstract class BaseValidator(private val issueHandler: IssueHandler, val programInfo: ProgramInfo) : ASTVisitor() {

    fun reportError(node: SourceElement, message: String) {
        val range = programInfo.sourceLocations[node]
        issueHandler.reportIssue(IssueKind.Error, range, message)
    }

}