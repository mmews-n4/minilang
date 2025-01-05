package de.marcusmews.minilang.ast

import de.marcusmews.minilang.antlr.MinilangBaseVisitor
import de.marcusmews.minilang.antlr.MinilangParser
import org.antlr.v4.runtime.ParserRuleContext
import java.util.Collections

/**
 * This class builds an AST from the parse tree by traversing the parse tree using an ANTLR tree visitor
 *
 * Implementation note: It is a conscious decision to set the parent here in this rather tedious manner
 * instead of e.g. doing it a separate tree traversal using reflection to support cleaner separation of
 * AST construction and its use.
 */
class ASTBuilder : MinilangBaseVisitor<Any>() {
    private val sourceLocations = HashMap<SourceElement, Range>()
    private val parents = HashMap<SourceElement, SourceElement>()

    fun build(ctx: MinilangParser.ProgramContext) : ASTInfo {
        sourceLocations.clear()
        val program = visitProgram(ctx)
        return ASTInfo(program, Collections.unmodifiableMap(parents), Collections.unmodifiableMap(sourceLocations))
    }

    override fun visitProgram(ctx: MinilangParser.ProgramContext): Program {
        val statements = ctx.stmt().map { visitStmt(it) }
        val program = addLocation(ctx, Program(statements))
        for (stmt in statements) {
            parents[stmt] = program
        }
        return program
    }

    override fun visitStmt(ctx: MinilangParser.StmtContext): Statement {
        return when {
            ctx.varDecl() != null       -> visitVarDecl(ctx.varDecl())
            ctx.outputStmt() != null    -> visitOutputStmt(ctx.outputStmt())
            ctx.printStmt() != null     -> visitPrintStmt(ctx.printStmt())
            else                        -> throw IllegalArgumentException("Unknown statement type: ${ctx.text}")
        }
    }

    override fun visitVarDecl(ctx: MinilangParser.VarDeclContext): VariableDeclaration {
        val identifier = ctx.IDENTIFIER()?.text
        val expression = visitExpr(ctx.expr())
        val varDecl = addLocation(ctx, VariableDeclaration(identifier, expression))
        if (expression != null) {
            parents[expression] = varDecl
        }
        return varDecl
    }

    override fun visitOutputStmt(ctx: MinilangParser.OutputStmtContext): OutputStatement {
        val expression = visitExpr(ctx.expr())
        val outStmt = addLocation(ctx, OutputStatement(expression))
        if (expression != null) {
            parents[expression] = outStmt
        }
        return outStmt
    }

    override fun visitPrintStmt(ctx: MinilangParser.PrintStmtContext): PrintStatement {
        val string = ctx.STRING()?.text?.trim('"') // Remove quotes
        return addLocation(ctx, PrintStatement(string))
    }

    override fun visitExpr(ctx: MinilangParser.ExprContext?): Expression? {
        return when {
            ctx == null                 -> null
            ctx.NUMBER() != null        -> addLocation(ctx, NumberLiteral(ctx.NUMBER().text.toDouble()))
            ctx.IDENTIFIER() != null    -> addLocation(ctx, IdentifierExpression(ctx.IDENTIFIER().text))
            ctx.op != null              -> visitBinaryOperation(ctx)
            ctx.sequence() != null      -> visitSequence(ctx.sequence())
            ctx.mapExpr() != null       -> visitMapExpr(ctx.mapExpr())
            ctx.reduceExpr() != null    -> visitReduceExpr(ctx.reduceExpr())
            ctx.LPAREN() != null        -> visitParenthesizedExpression(ctx)
            else                        -> {
                if (ctx.text != null && ctx.text.isNotBlank()) {
                    throw IllegalArgumentException("Unknown expression: ${ctx.text}")
                } else {
                    null // ignore error state
                }
            }
        }
    }

    private fun visitBinaryOperation(ctx: MinilangParser.ExprContext): BinaryOperation {
        val left = visitExpr(ctx.expr(0))
        val right = visitExpr(ctx.expr(1))
        val op = when (ctx.op.text) {
            "+" -> Operator.PLUS
            "-" -> Operator.MINUS
            "*" -> Operator.MULTIPLY
            "/" -> Operator.DIVIDE
            "^" -> Operator.POWER
            else -> throw IllegalArgumentException("Unknown operator: ${ctx.op.text}")
        }
        val binOp = addLocation(ctx, BinaryOperation(left, op, right))
        left?.let { parents[it] = binOp }
        right?.let { parents[it] = binOp }
        return binOp
    }


    private fun visitParenthesizedExpression(ctx: MinilangParser.ExprContext): ParenthesizedExpression {
        val expr = visitExpr(ctx.expr(0))
        val parenExpr = addLocation(ctx, ParenthesizedExpression(expr))
        if (expr != null) {
            parents[expr] = parenExpr
        }
        return parenExpr
    }

    override fun visitSequence(ctx: MinilangParser.SequenceContext): SequenceExpression {
        val start = visitExpr(ctx.expr(0))
        val end = visitExpr(ctx.expr(1))
        val seqExpr = addLocation(ctx, SequenceExpression(start, end))
        if (start != null) {
            parents[start] = seqExpr
        }
        if (end != null) {
            parents[end] = seqExpr
        }
        return seqExpr
    }

    override fun visitMapExpr(ctx: MinilangParser.MapExprContext): MapExpression {
        val sequence = visitExpr(ctx.expr(0))
        val parameter = ctx.IDENTIFIER()?.text
        val body = visitExpr(ctx.expr(1))
        val mapExpr = addLocation(ctx, MapExpression(sequence, parameter, body))
        if (sequence != null) {
            parents[sequence] = mapExpr
        }
        if (body != null) {
            parents[body] = mapExpr
        }
        return mapExpr
    }

    override fun visitReduceExpr(ctx: MinilangParser.ReduceExprContext): ReduceExpression {
        val sequence = visitExpr(ctx.expr(0))
        val accumulator = visitExpr(ctx.expr(1))
        val param1 = if (ctx.IDENTIFIER().size > 0) ctx.IDENTIFIER(0)?.text else null
        val param2 = if (ctx.IDENTIFIER().size > 1) ctx.IDENTIFIER(1)?.text else null
        val body = visitExpr(ctx.expr(2))
        val redExpr = addLocation(ctx, ReduceExpression(sequence, accumulator, param1, param2, body))
        if (sequence != null) {
            parents[sequence] = redExpr
        }
        if (accumulator != null) {
            parents[accumulator] = redExpr
        }
        if (body != null) {
            parents[body] = redExpr
        }
        return redExpr
    }

    private fun <T : SourceElement> addLocation(ctx: ParserRuleContext, t: T) : T {
        sourceLocations[t] = getRange(ctx)
        return t
    }
}
