package de.marcusmews.minilang.tests.utils

import de.marcusmews.minilang.ast.*
import org.antlr.v4.runtime.ParserRuleContext
import kotlin.random.Random

/**
 * This utility generates arbitrary ASTs of valid Minilang programs.
 */
class ASTGenerator {
    private val mockCtx = ParserRuleContext()

    fun generateASTs(seed: Long, count: Int): List<Program> {
        val random = Random(seed)
        return List(count) { generateRandomAST(random) }
    }

    private fun generateRandomAST(random: Random): Program {
        val statements = List(random.nextInt(1, 5)) { idx -> generateRandomStatement(idx, random) }
        return Program(mockCtx, statements)
    }

    private fun generateRandomStatement(position: Int, random: Random): Statement {
        return when (random.nextInt(3)) {
            0 -> generateRandomVariableDeclaration(position, random)
            1 -> generateRandomOutputStatement(position, random)
            2 -> generateRandomPrintStatement(position, random)
            else -> throw IllegalStateException("Unexpected random value")
        }
    }

    private fun generateRandomVariableDeclaration(position: Int, random: Random): VariableDeclaration {
        val identifier = Identifier(mockCtx, "var${random.nextInt(100)}")
        val expression = generateRandomExpression(random)
        return VariableDeclaration(mockCtx, position, identifier, expression)
    }

    private fun generateRandomOutputStatement(position: Int, random: Random): OutputStatement {
        val expression = generateRandomExpression(random)
        return OutputStatement(mockCtx, position, expression)
    }

    private fun generateRandomPrintStatement(position: Int, random: Random): PrintStatement {
        val string = StringLiteral(mockCtx, "RandomString${random.nextInt(100)}")
        return PrintStatement(mockCtx, position, string)
    }

    private fun generateRandomExpression(random: Random): Expression {
        return when (random.nextInt(7)) {
            0 -> NumberLiteral(mockCtx, generateNumber(random, -100, 100))
            1 -> IdentifierExpression(mockCtx, "id${random.nextInt(100)}")
            2 -> BinaryOperation(mockCtx,
                generateRandomExpression(random),
                generateRandomOperator(random),
                generateRandomExpression(random)
            )
            3 -> ParenthesizedExpression(mockCtx, generateRandomExpression(random))
            4 -> SequenceExpression(mockCtx,
                NumberLiteral(mockCtx, random.nextInt(0, 10).toDouble()),
                NumberLiteral(mockCtx, random.nextInt(11, 20).toDouble())
            )
            5 -> MapExpression(mockCtx,
                SequenceExpression(mockCtx,
                    NumberLiteral(mockCtx, random.nextInt(0, 10).toDouble()),
                    NumberLiteral(mockCtx, random.nextInt(11, 20).toDouble())
                ),
                Identifier(mockCtx, "param${random.nextInt(10)}"),
                generateRandomExpression(random)
            )
            6 -> ReduceExpression(mockCtx,
                SequenceExpression(mockCtx,
                    NumberLiteral(mockCtx, random.nextInt(0, 10).toDouble()),
                    NumberLiteral(mockCtx, random.nextInt(11, 20).toDouble())
                ),
                NumberLiteral(mockCtx, generateNumber(random, 0, 10)),
                Identifier(mockCtx, "acc${random.nextInt(10)}"),
                Identifier(mockCtx, "val${random.nextInt(10)}"),
                generateRandomExpression(random)
            )
            else -> throw IllegalStateException("Unexpected random value")
        }
    }

    private fun generateNumber(random: Random, origin: Int, bound: Int): Double {
        return Math.round(random.nextDouble(origin * 100.0, bound * 100.0)) / 100.0
    }

    private fun generateRandomOperator(random: Random): Operator {
        return when (random.nextInt(5)) {
            0 -> Operator.PLUS
            1 -> Operator.MINUS
            2 -> Operator.MULTIPLY
            3 -> Operator.DIVIDE
            4 -> Operator.POWER
            else -> throw IllegalStateException("Unexpected random value")
        }
    }
}

