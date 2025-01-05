package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.IssueKind
import de.marcusmews.minilang.ast.Range



data class Issue(val kind: IssueKind, val range: Range?, val message: String) {
    override fun toString(): String {
        val position = if (range == null) "" else " (${range.start.line}, ${range.start.character})"
        return "$kind$position: $message"
    }
}


interface IssueHandler {
    /** Note that Minilang does not support multiple files and hence no file is given */
    fun reportIssue(kind: IssueKind, range: Range?, message: String)
}

class StdOutIssueHandler : IssueHandler {
    val issues = ArrayList<Issue>()

    override fun reportIssue(kind: IssueKind, range: Range?, message: String) {
        issues.add(Issue(kind, range, message))

        if (range == null) {
            println("$kind [unknown location]: $message")
        } else {
            println("$kind [${range.start.line}/${range.start.character}]: $message")
        }
    }
}
