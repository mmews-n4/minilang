package de.marcusmews.minilang.ast

import org.antlr.v4.runtime.ParserRuleContext

/*
 * Representation of the Abstract Syntax Tree (AST) for Minilang.
 * Parent nodes are stored in {@link #SourceCodeInfo}
 */


/** Marker interface for source related AST elements */
sealed class SourceElement(ctx: ParserRuleContext) {
    val location: Range = getRange(ctx)
}

/** Program Root */
class Program(ctx: ParserRuleContext, val statements: List<Statement>) : SourceElement(ctx)

/** Abstract Syntax Tree (AST) nodes */
sealed class Statement(ctx: ParserRuleContext, val position: Int) : SourceElement(ctx)

/** Variable declaration: var identifier = expression */
class VariableDeclaration(ctx: ParserRuleContext, position: Int, val identifier: Identifier?, val expression: Expression?) : Statement(ctx, position)

/** Output statement: out expression */
class OutputStatement(ctx: ParserRuleContext, position: Int, val expression: Expression?) : Statement(ctx, position)

/** Print statement: print "string" */
class PrintStatement(ctx: ParserRuleContext, position: Int, val string: StringLiteral?) : Statement(ctx, position)

class StringLiteral(ctx: ParserRuleContext, val value: String) : SourceElement(ctx)

/** Expressions */
sealed class Expression(ctx: ParserRuleContext) : SourceElement(ctx)

class BinaryOperation(ctx: ParserRuleContext, val left: Expression?, val operator: Operator?, val right: Expression?) : Expression(ctx)
class ParenthesizedExpression(ctx: ParserRuleContext, val expression: Expression?) : Expression(ctx)
class IdentifierExpression(ctx: ParserRuleContext, val name: String) : Expression(ctx)
class NumberLiteral(ctx: ParserRuleContext, val value: Double) : Expression(ctx)
class SequenceExpression(ctx: ParserRuleContext, val start: Expression?, val end: Expression?) : Expression(ctx)
class MapExpression(ctx: ParserRuleContext, val sequence: Expression?, val parameter: Identifier?, val body: Expression?) : Expression(ctx)
class ReduceExpression(ctx: ParserRuleContext, val sequence: Expression?,
                       /**
                        * **Important note:**
                        *
                        * The assignment calls this value 'accumulator'. This term is used in Kotlin and Java collections differently.
                        * - Inside the Kotlin collections fold function, it refers to a variable that accumulates intermediate results.
                        *   it is initialized using a variable called 'initial'.
                        * - In the Java Stream reduce method, it refers to a lambda that combines two intermediate values and returns
                        *   one resulting value.
                        *
                        * Since neither makes sense here, I call it 'initial' since that matches most to what the assignment might intend.
                        */
                       val initial: Expression?, val param1: Identifier?, val param2: Identifier?, val body: Expression?) : Expression(ctx)

class Identifier(ctx: ParserRuleContext, val name: String) : SourceElement(ctx)

/** Operators */
enum class Operator {
    PLUS, MINUS, MULTIPLY, DIVIDE, POWER
}

/** Parts of source elements */
enum class SourceElementPart {
    Statements, // used in Program
    Identifier, // used in VariableDeclaration
    Expression, // used in VariableDeclaration, OutputStatement, ParenthesizedExpression
    String,     // used in PrintStatement
    Left,       // used in BinaryOperation
    Right,      // used in BinaryOperation
    Name,       // used in Identifier, IdentifierExpression
    Value,      // used in NumberLiteral
    Start,      // used in SequenceExpression
    End,        // used in SequenceExpression
    Sequence,   // used in SequenceExpression
    Parameter,  // used in MapExpression
    Body,       // used in MapExpression, ReduceExpression
    Initial,    // used in ReduceExpression
    Param1,     // used in ReduceExpression
    Param2,     // used in ReduceExpression
}

fun getSourceElementParts(elem: SourceElement) : List<Pair<SourceElementPart, Any?>> {
    return when (elem) {
        is Program              -> listOf((SourceElementPart.Statements to elem.statements))
        is VariableDeclaration  -> listOf((SourceElementPart.Identifier to elem.identifier), (SourceElementPart.Expression to elem.expression))
        is OutputStatement      -> listOf((SourceElementPart.Expression to elem.expression))
        is PrintStatement       -> listOf((SourceElementPart.String to elem.string))
        is BinaryOperation      -> listOf((SourceElementPart.Left to elem.left), (SourceElementPart.Right to elem.right))
        is ParenthesizedExpression  -> listOf((SourceElementPart.Expression to elem.expression))
        is IdentifierExpression -> listOf((SourceElementPart.Name to elem.name))
        is NumberLiteral        -> listOf((SourceElementPart.Value to elem.value))
        is SequenceExpression   -> listOf((SourceElementPart.Start to elem.start), (SourceElementPart.End to elem.end))
        is MapExpression        -> listOf((SourceElementPart.Sequence to elem.sequence), (SourceElementPart.Parameter to elem.parameter), (SourceElementPart.Body to elem.body))
        is ReduceExpression     -> listOf((SourceElementPart.Sequence to elem.sequence), (SourceElementPart.Initial to elem.initial), (SourceElementPart.Param1 to elem.param1), (SourceElementPart.Param2 to elem.param2), (SourceElementPart.Body to elem.body))
        is StringLiteral        -> listOf((SourceElementPart.Value to elem.value))
        is Identifier           -> listOf((SourceElementPart.Name to elem.name))
    }
}
