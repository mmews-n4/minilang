package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.IssueKind
import de.marcusmews.minilang.ast.Position
import de.marcusmews.minilang.ast.Range
import org.antlr.v4.runtime.*

class AntlrErrorListener(private val issueHandler: IssueHandler) : BaseErrorListener() {

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        val errorMessage = msg ?: "Unknown error"
        val start = Position(line - 1, charPositionInLine)
        val end = getTokenEndPosition(recognizer, offendingSymbol)
        val range = Range(start, end)
        issueHandler.reportIssue(IssueKind.Error, range, errorMessage)
    }

    private fun getTokenEndPosition(recognizer: Recognizer<*, *>?, offendingSymbol: Any?): Position? {
        if (offendingSymbol is Token && recognizer is Parser) {
            val tokenStream = recognizer.inputStream
            val nextTokenIndex = offendingSymbol.tokenIndex + 1
            val nextToken = if (nextTokenIndex < tokenStream.size()) tokenStream.get(nextTokenIndex) else null

            if (nextToken != null) {
                return Position(nextToken.line - 1, nextToken.charPositionInLine)
            } else {
                // If there’s no next token, use the end of the current token
                // TODO: deal with multi-line tokens
                val length = offendingSymbol.stopIndex - offendingSymbol.startIndex + 1
                return Position(offendingSymbol.line - 1, (offendingSymbol.charPositionInLine + length))
            }
        }
        return null
    }
}