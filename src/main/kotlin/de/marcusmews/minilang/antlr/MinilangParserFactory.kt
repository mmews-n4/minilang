package de.marcusmews.minilang.antlr

import de.marcusmews.minilang.validation.AntlrErrorListener
import de.marcusmews.minilang.validation.IssueHandler
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream



fun createParser(sourceCode: String, issueHandler: IssueHandler) : MinilangParser {
    return createParser(CharStreams.fromString(sourceCode), issueHandler)
}

fun createParser(charStream: CharStream, issueHandler: IssueHandler) : MinilangParser {
    val lexer = MinilangLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = MinilangParser(tokens)
    val errorHandler = AntlrErrorListener(issueHandler)
    lexer.removeErrorListeners()
    lexer.addErrorListener(errorHandler)
    parser.removeErrorListeners()
    parser.addErrorListener(errorHandler)
    return parser
}