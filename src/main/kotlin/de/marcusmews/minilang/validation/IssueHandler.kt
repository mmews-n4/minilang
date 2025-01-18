package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.IssueKind
import de.marcusmews.minilang.ast.RANGE_MISSING
import de.marcusmews.minilang.ast.Range



data class Issue(val kind: IssueKind, val range: Range?, val message: String) {
    override fun toString(): String {
        return toString(0, 0)
    }

    /** Change base of range numbers from zero based to zero plus given offset based */
    fun toString(lineBase: Int, characterBase: Int): String {
        val position = if (range == null) "" else " (${range.start.line + lineBase}, ${range.start.character + characterBase})"
        return "$kind$position: $message"
    }
}


interface IssueHandler {
    /** Note that Minilang does not support multiple files and hence no file is given */
    fun reportIssue(kind: IssueKind, range: Range?, message: String)

    fun printIssues()
}

class StdOutIssueHandler : IssueHandler {
    val issues = ArrayList<Issue>()

    override fun reportIssue(kind: IssueKind, range: Range?, message: String) {
        issues.add(Issue(kind, range, message))
    }

    override fun printIssues() {
        for (issue in issues) {
            val range = issue.range ?: RANGE_MISSING
            val kind = issue.kind
            val message = issue.message
            if (range == RANGE_MISSING) {
                println("$kind [unknown location]: $message")
            } else {
                println("$kind [${range.start.line}/${range.start.character}]: $message")
            }
        }
    }

    fun replaceIssue(kind: IssueKind, range: Range?, message: String) {
        if (range != null) {
            issues.removeAll { it.kind == kind && it.range?.start == range.start }
        }
        issues.add(Issue(kind, range, message))
    }
}
