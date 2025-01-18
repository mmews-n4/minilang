package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.*


abstract class BaseValidator(private val issueHandler: IssueHandler, val programInfo: ProgramInfo) : ASTVisitor() {

    fun reportError(node: SourceElement, message: String) {
        val range = node.location
        issueHandler.reportIssue(IssueKind.Error, range, message)
    }

}