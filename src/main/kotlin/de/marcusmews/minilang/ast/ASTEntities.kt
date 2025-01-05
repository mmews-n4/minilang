package de.marcusmews.minilang.ast
/*
 * Representation of the Abstract Syntax Tree (AST) for Minilang
 * The goal is to use data classes. As a drawback, some information
 * such as parent nodes, is stored in {@link #SourceCodeInfo}
 */


/** Marker interface for source related AST elements */
interface SourceElement

/** Program Root */
data class Program(val statements: List<Statement>) : SourceElement

/** Abstract Syntax Tree (AST) nodes */
sealed class Statement : SourceElement

/** Variable declaration: var identifier = expression */
data class VariableDeclaration(val identifier: String?, val expression: Expression?) : Statement()

/** Output statement: out expression */
data class OutputStatement(val expression: Expression?) : Statement()

/** Print statement: print "string" */
data class PrintStatement(val string: String?) : Statement()

/** Expressions */
sealed class Expression : SourceElement

data class BinaryOperation(val left: Expression?, val operator: Operator?, val right: Expression?) : Expression()
data class ParenthesizedExpression(val expression: Expression?) : Expression()
data class IdentifierExpression(val name: String) : Expression()
data class NumberLiteral(val value: Double) : Expression()
data class SequenceExpression(val start: Expression?, val end: Expression?) : Expression()
data class MapExpression(val sequence: Expression?, val parameter: String?, val body: Expression?) : Expression()
data class ReduceExpression(val sequence: Expression?, val accumulator: Expression?, val param1: String?, val param2: String?, val body: Expression?) : Expression()

/** Operators */
enum class Operator {
    PLUS, MINUS, MULTIPLY, DIVIDE, POWER
}