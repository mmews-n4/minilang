package de.marcusmews.minilang.ast

import de.marcusmews.minilang.antlr.MinilangBaseVisitor
import de.marcusmews.minilang.antlr.MinilangParser
import org.antlr.v4.runtime.ParserRuleContext
import java.util.Collections


data class ASTBuilderException(val ctx: ParserRuleContext, val msg: String): RuntimeException(msg)

/**
 * This class builds an AST from the parse tree by traversing the parse tree using an ANTLR tree visitor
 *
 * Implementation note: It is a conscious decision to set the parent here in this rather tedious manner
 * instead of e.g. doing it a separate tree traversal to support cleaner separation of AST construction
 * and its use.
 */
class ASTBuilder : MinilangBaseVisitor<Any>() {
    private val parents = HashMap<SourceElement, SourceElement>()

    fun build(ctx: MinilangParser.ProgramContext) : ASTInfo {
        val program = visitProgram(ctx)
        return ASTInfo(program, Collections.unmodifiableMap(parents))
    }

    override fun visitProgram(ctx: MinilangParser.ProgramContext): Program {
        val statements = ctx.stmt().mapIndexed { index, stmtCtx -> visitStmt(index, stmtCtx) }
        val program = Program(ctx, statements)
        for (stmt in statements) {
            parents[stmt] = program
        }
        return program
    }

    private fun visitStmt(position: Int, ctx: MinilangParser.StmtContext): Statement {
        return when {
            ctx.varDecl() != null       -> visitVarDecl(position, ctx.varDecl())
            ctx.outputStmt() != null    -> visitOutputStmt(position, ctx.outputStmt())
            ctx.printStmt() != null     -> visitPrintStmt(position, ctx.printStmt())
            else                        -> throw ASTBuilderException(ctx, "Parser expected statement but found: ${ctx.text}")
        }
    }

    private fun visitVarDecl(position: Int, ctx: MinilangParser.VarDeclContext): VariableDeclaration {
        val identifier = ctx.identifier()?.text?.let { Identifier(ctx.identifier(), it) }
        val expression = visitExpr(ctx.expr())
        val varDecl = VariableDeclaration(ctx, position, identifier, expression)

        identifier?.let { parents[it] = varDecl }
        expression?.let { parents[it] = varDecl }
        return varDecl
    }

    private fun visitOutputStmt(position: Int, ctx: MinilangParser.OutputStmtContext): OutputStatement {
        val expression = visitExpr(ctx.expr())
        val outStmt = OutputStatement(ctx, position, expression)

        expression?.let { parents[it] = outStmt }
        return outStmt
    }

    private fun visitPrintStmt(position: Int, ctx: MinilangParser.PrintStmtContext): PrintStatement {
        val string = ctx.STRING()?.text?.let { StringLiteral(ctx, ctx.STRING().text.trim('"')) } // Remove quotes
        val printStmt = PrintStatement(ctx, position, string)
        string?.let { parents[it] = printStmt }
        return printStmt
    }

    override fun visitExpr(ctx: MinilangParser.ExprContext?): Expression? {
        return when {
            ctx == null                 -> null
            ctx.NUMBER() != null        -> NumberLiteral(ctx, ctx.NUMBER().text.toDouble())
            ctx.IDENTIFIER() != null    -> IdentifierExpression(ctx, ctx.IDENTIFIER().text)
            ctx.op != null              -> visitBinaryOperation(ctx)
            ctx.sequence() != null      -> visitSequence(ctx.sequence())
            ctx.mapExpr() != null       -> visitMapExpr(ctx.mapExpr())
            ctx.reduceExpr() != null    -> visitReduceExpr(ctx.reduceExpr())
            ctx.LPAREN() != null        -> visitParenthesizedExpression(ctx)
            else                        -> {
                if (ctx.text != null && ctx.text.isNotBlank()) {
                    throw ASTBuilderException(ctx, "Parser expected expression but found: ${ctx.text}")
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
            else -> throw ASTBuilderException(ctx, "Unknown operator: ${ctx.op.text}")
        }
        val binOp = BinaryOperation(ctx, left, op, right)
        left?.let { parents[it] = binOp }
        right?.let { parents[it] = binOp }
        return binOp
    }


    private fun visitParenthesizedExpression(ctx: MinilangParser.ExprContext): ParenthesizedExpression {
        val expr = visitExpr(ctx.expr(0))
        val parenExpr = ParenthesizedExpression(ctx, expr)

        expr?.let { parents[it] = parenExpr }
        return parenExpr
    }

    override fun visitSequence(ctx: MinilangParser.SequenceContext): SequenceExpression {
        val start = visitExpr(ctx.expr(0))
        val end = visitExpr(ctx.expr(1))
        val seqExpr = SequenceExpression(ctx, start, end)

        start?.let { parents[it] = seqExpr }
        end?.let { parents[it] = seqExpr }
        return seqExpr
    }

    override fun visitMapExpr(ctx: MinilangParser.MapExprContext): MapExpression {
        val sequence = visitExpr(ctx.expr(0))
        val parameter = ctx.identifier()?.text?.let { Identifier(ctx.identifier(), it) }
        val body = visitExpr(ctx.expr(1))
        val mapExpr = MapExpression(ctx, sequence, parameter, body)

        parameter?.let { parents[it] = mapExpr }
        sequence?.let { parents[it] = mapExpr }
        body?.let { parents[it] = mapExpr }
        return mapExpr
    }

    override fun visitReduceExpr(ctx: MinilangParser.ReduceExprContext): ReduceExpression {
        val sequence = visitExpr(ctx.expr(0))
        val accumulator = visitExpr(ctx.expr(1))
        val param1 = ctx.identifier(0)?.text?.let { Identifier(ctx.identifier(0), it) }
        val param2 = ctx.identifier(1)?.text?.let { Identifier(ctx.identifier(1), it) }
        val body = visitExpr(ctx.expr(2))
        val redExpr = ReduceExpression(ctx, sequence, accumulator, param1, param2, body)

        param1?.let { parents[it] = redExpr }
        param2?.let { parents[it] = redExpr }
        sequence?.let { parents[it] = redExpr }
        accumulator?.let { parents[it] = redExpr }
        body?.let { parents[it] = redExpr }
        return redExpr
    }
}
